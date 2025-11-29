package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.model.User;
import com.example.service.UserService;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String viewUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        model.addAttribute("activePage", "users");
        model.addAttribute("pageTitle", "Quản lý người dùng");

        return "admin_users";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        return userService.getUserById(id)
                .map(user -> {
                    model.addAttribute("user", user);

                    model.addAttribute("activePage", "users");
                    model.addAttribute("pageTitle", "Chỉnh sửa người dùng");

                    return "admin_user_edit";
                })
                .orElse("redirect:/admin/users");
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User user,
                             RedirectAttributes redirectAttributes) {

        userService.updateUser(user.getUserID(), user);
        redirectAttributes.addFlashAttribute("message",
                "Đã cập nhật thông tin tài khoản ID " + user.getUserID());

        return "redirect:/admin/users";
    }
}
