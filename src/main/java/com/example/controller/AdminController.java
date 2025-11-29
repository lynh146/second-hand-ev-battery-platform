package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.impl.AdminServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminServiceImpl adminService;

    public AdminController(AdminServiceImpl adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("activePage", "dashboard");
        model.addAttribute("pageTitle", "Admin Dashboard");

        return "admin_dashboard"; 
    }
}
