package com.example.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.PasswordResetToken;
import com.example.model.User;
import com.example.repository.PasswordResetTokenRepository;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepo;
    private final PasswordResetTokenRepository tokenRepo;
    private final EmailService emailService;

    public void createResetToken(String email) {

        User user = userRepo.findByEmail(email);
        if (user == null) throw new RuntimeException("Email không tồn tại!");

        PasswordResetToken token = tokenRepo.findByUser(user);
        if (token == null) token = new PasswordResetToken();

        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        token.setUsed(false);

        tokenRepo.save(token);

        String link = "http://localhost:8080/reset-password?token=" + token.getToken();

        emailService.sendEmail(
                email,
                "Khôi phục mật khẩu",
                "Nhấn vào link để đổi mật khẩu:\n" + link
        );
    }

    public PasswordResetToken validate(String token) {

        PasswordResetToken prt = tokenRepo.findByToken(token);

        if (prt == null) throw new RuntimeException("Token không hợp lệ!");
        if (prt.isUsed()) throw new RuntimeException("Token đã sử dụng!");
        if (prt.getExpiryDate().isBefore(LocalDateTime.now()))
            throw new RuntimeException("Token đã hết hạn!");

        return prt;
    }

    public void resetPassword(String token, String newPass, PasswordEncoder encoder) {

        PasswordResetToken valid = validate(token);

        User user = valid.getUser();
        user.setPassword(encoder.encode(newPass));
        userRepo.save(user);

        valid.setUsed(true);
        tokenRepo.save(valid);
    }
}
