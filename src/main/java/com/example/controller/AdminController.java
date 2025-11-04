package com.example.controller;

import com.example.model.Admin;
import com.example.service.impl.AdminServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminServiceImpl adminService;

    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "admin_login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {
        Admin admin = adminService.login(email, password);
        if (admin != null) {
            model.addAttribute("admin", admin);
            return "admin_dashboard";
        } else {
            model.addAttribute("error", "Sai email hoặc mật khẩu");
            return "admin_login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "admin_dashboard";
    }
}
