package com.example.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.model.Wallet;
import com.example.repository.UserRepository;
import com.example.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    // ========== DEPENDENCY INJECTION ==========
    // Dùng constructor injection + @RequiredArgsConstructor cho sạch, dễ test

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder; // dùng chung với SecurityConfig

    // ========== 1. CRUD / LẤY USER ==========

    // Thắng: dùng trong AdminUserController để load danh sách user
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Dùng chung: lấy 1 user theo ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // ========== 2. ĐĂNG KÝ USER (REGISTER) ==========

    /**
     * Đăng ký tài khoản mới:
     *  - Check trùng email
     *  - Hash password bằng BCrypt (PasswordEncoder)
     *  - Gán mặc định role = MEMBER, status = ACTIVE nếu chưa set
     *  - Set RegisterDate = now
     *  - Tạo luôn Wallet (balance = 0, status = ACTIVE)
     */
    public User registerUser(User user) {

        // kiểm tra email đã tồn tại chưa (dùng existsByEmail trong UserRepository)
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }

        // set role mặc định nếu chưa có
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("MEMBER");
        }

        // set status mặc định
        if (user.getStatus() == null || user.getStatus().isBlank()) {
            user.setStatus("ACTIVE");
        }

        // set thời gian đăng ký
        if (user.getRegisterDate() == null) {
            user.setRegisterDate(LocalDateTime.now());
        }

        // mã hoá mật khẩu trước khi lưu
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // lưu user
        User savedUser = userRepository.save(user);

        // tạo luôn ví cho user mới
        Wallet wallet = Wallet.builder()
                .user(savedUser)
                .balance(BigDecimal.ZERO)      // số dư ban đầu = 0
                .createdAt(LocalDateTime.now())
                .status("ACTIVE")
                .build();

        walletRepository.save(wallet);

        return savedUser;
    }

    // ========== 3. ĐĂNG NHẬP (CHO REST API) ==========

    /**
     * Login kiểu REST (dùng cho /api/users/login).
     * Lưu ý: Spring Security đã handle form login,
     * hàm này chủ yếu phục vụ API JSON.
     */
    public User loginUser(String email, String rawPassword) {
        User existing = userRepository.findByEmail(email);
        if (existing == null) {
            throw new RuntimeException("Sai email hoặc mật khẩu!");
        }

        // so sánh password gửi lên với password đã mã hoá trong DB
        if (!passwordEncoder.matches(rawPassword, existing.getPassword())) {
            throw new RuntimeException("Sai email hoặc mật khẩu!");
        }

        return existing;
    }

    // ========== 4. CẬP NHẬT USER ==========

    /**
     * Cập nhật thông tin user:
     *  - Cho phép đổi: fullName, phoneNumber, address, status, role
     *  - Nếu truyền password mới (không rỗng) thì hash lại & cập nhật
     *  - Dùng cho cả Admin (quản lý user) và chính user tự cập nhật profile
     */
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    // các field cơ bản
                    user.setFullName(updatedUser.getFullName());
                    user.setPhoneNumber(updatedUser.getPhoneNumber());
                    user.setAddress(updatedUser.getAddress());

                    // cho phép admin đổi trạng thái & role
                    if (updatedUser.getStatus() != null && !updatedUser.getStatus().isBlank()) {
                        user.setStatus(updatedUser.getStatus());
                    }
                    if (updatedUser.getRole() != null && !updatedUser.getRole().isBlank()) {
                        user.setRole(updatedUser.getRole());
                    }

                    // nếu có password mới thì mã hoá và set lại
                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
                        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }

                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User không tồn tại!"));
    }

    // ========== 5. XOÁ USER ==========

    /**
     * Xoá tài khoản user.
     *  Tuỳ nghiệp vụ: có thể cân nhắc không xoá cứng,
     *  mà chỉ set Status = BLOCKED / INACTIVE.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
