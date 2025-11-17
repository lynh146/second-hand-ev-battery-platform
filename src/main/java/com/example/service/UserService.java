package com.example.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.model.Wallet;
import com.example.repository.UserRepository;
import com.example.repository.WalletRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
     @Autowired
    private WalletRepository walletRepository; 

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User registerUser(User user) {
         if (userRepository.findByEmail(user.getEmail()) != null) {
        throw new RuntimeException("Email đã tồn tại!");
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    User savedUser = userRepository.save(user);

    Wallet wallet = new Wallet();
    wallet.setUser(savedUser);
    wallet.setBalance(BigDecimal.ZERO); 
    wallet.setStatus("ACTIVE");
    walletRepository.save(wallet);

    return savedUser;
    }

    public User loginUser(String email, String password) {
        User existing = userRepository.findByEmail(email);
        if (existing == null || !passwordEncoder.matches(password, existing.getPassword())) {
            throw new RuntimeException("Sai email hoặc mật khẩu!");
        }
        return existing;
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setFullName(updatedUser.getFullName());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setAddress(updatedUser.getAddress());
            user.setStatus(updatedUser.getStatus());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User không tồn tại!"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
