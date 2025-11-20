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

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final PasswordEncoder passwordEncoder; 
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    public User registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email đã tồn tại!");
        }
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("MEMBER");
        }
        if (user.getStatus() == null || user.getStatus().isBlank()) {
            user.setStatus("ACTIVE");
        }
        if (user.getRegisterDate() == null) {
            user.setRegisterDate(LocalDateTime.now());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        Wallet wallet = Wallet.builder()
                .user(savedUser)
                .balance(BigDecimal.ZERO)     
                .createdAt(LocalDateTime.now())
                .status("ACTIVE")
                .build();

        walletRepository.save(wallet);

        return savedUser;
    }

    public User loginUser(String email, String rawPassword) {
        User existing = userRepository.findByEmail(email);
        if (existing == null) {
            throw new RuntimeException("Sai email hoặc mật khẩu!");
        }

        if (!passwordEncoder.matches(rawPassword, existing.getPassword())) {
            throw new RuntimeException("Sai email hoặc mật khẩu!");
        }

        return existing;
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {

                    user.setFullName(updatedUser.getFullName());
                    user.setPhoneNumber(updatedUser.getPhoneNumber());
                    user.setAddress(updatedUser.getAddress());

                    if (updatedUser.getStatus() != null && !updatedUser.getStatus().isBlank()) {
                        user.setStatus(updatedUser.getStatus());
                    }
                    if (updatedUser.getRole() != null && !updatedUser.getRole().isBlank()) {
                        user.setRole(updatedUser.getRole());
                    }

                    if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
                        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                    }

                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User không tồn tại!"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
