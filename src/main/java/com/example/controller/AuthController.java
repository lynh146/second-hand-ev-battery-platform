package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";   
    }
    
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";   
    }

    @GetMapping("/forgot_password")
    public String showForgotPasswordPage() {
        return "forgot_password";
    }

    @GetMapping("/reset-password")  
    public String showResetPasswordPage(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        return "reset_password";
    }

    
}
