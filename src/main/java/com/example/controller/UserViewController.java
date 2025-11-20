package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.User;
import com.example.service.UserService;

@Controller
@RequestMapping("/users")
public class UserViewController {

    private final UserService userService;

    public UserViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";  // Diễn: form đăng ký cho admin tạo user
    }

    @PostMapping("/new")
    public String handleCreate(
            @ModelAttribute("user") User user,
            RedirectAttributes redirectAttributes
    ) {
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("MEMBER");   // đặt role mặc định
        }
        if (user.getStatus() == null || user.getStatus().isBlank()) {
            user.setStatus("ACTIVE");
        }

        userService.registerUser(user);

        redirectAttributes.addFlashAttribute("message", "Tạo tài khoản thành công!");
        return "redirect:/users/new";
    }
}

