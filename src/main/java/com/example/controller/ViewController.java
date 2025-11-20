package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.User;
import com.example.model.Wallet;
import com.example.service.UserService;
import com.example.service.WalletService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ViewController {

    private final UserService userService;
    private final WalletService walletService;   // 🔥 THÊM DÒNG NÀY

    public ViewController(UserService userService,
                          WalletService walletService) {    // 🔥 THÊM DÒNG NÀY
        this.userService = userService;
        this.walletService = walletService;      // 🔥 THÊM DÒNG NÀY
    }


    @GetMapping("/login")
    public String showLogin() {
        return "login";  
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String email,
                              @RequestParam String password,
                              Model model,
                              HttpSession session) {
        try {
            User user = userService.loginUser(email, password);

            session.setAttribute("loggedInUser", user);

            return "redirect:/profile";

        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "login";
        }
    }


    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "register";  
    }

    @PostMapping("/register")
    public String handleRegister(
            @ModelAttribute("user") User user,
            @RequestParam String confirmPassword,
            Model model) {

        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Mật khẩu không khớp");
            return "register";
        }

        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("USER");
        }
        if (user.getStatus() == null || user.getStatus().isBlank()) {
            user.setStatus("ACTIVE");
        }

        try {
            userService.registerUser(user);
            model.addAttribute("message", "Đăng ký thành công, hãy đăng nhập!");
            return "login";

        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
    }


    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

       
        Wallet wallet = walletService.getWalletByUser(loggedInUser.getUserID());

        model.addAttribute("user", loggedInUser);
        model.addAttribute("wallet", wallet);

        return "profile";
    }
}
