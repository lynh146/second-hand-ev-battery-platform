package com.example.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.PasswordResetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthPasswordController {

    private final PasswordResetService resetService;
    private final PasswordEncoder encoder;


@PostMapping("/forgot")
public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
    String email = body.get("email");

    try {
        resetService.createResetToken(email);
        return ResponseEntity.ok(Map.of("message", "Đã gửi email khôi phục mật khẩu!"));
    } catch (RuntimeException ex) {
        return ResponseEntity.status(400).body(Map.of("message", ex.getMessage()));
    }
}


@PostMapping("/reset")
public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {

    String token = body.get("token");
    String newPassword = body.get("password");

    try {
        resetService.resetPassword(token, newPassword, encoder);
        return ResponseEntity.ok(Map.of("message", "Đặt mật khẩu mới thành công!"));
    } catch (RuntimeException ex) {
        return ResponseEntity.status(400).body(Map.of("message", ex.getMessage()));
    }
}
}
