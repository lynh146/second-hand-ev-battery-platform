package com.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.config.CustomUserDetails;
import com.example.model.User;
import com.example.service.UserService;

@Controller
@RequestMapping("/member")
public class UserViewController {

    private final UserService userService;

    public UserViewController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/new")
    public String handleCreate(
            @ModelAttribute("user") User user,
            RedirectAttributes redirectAttributes
    ) {
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("MEMBER");
        }
        if (user.getStatus() == null || user.getStatus().isBlank()) {
            user.setStatus("ACTIVE");
        }

        userService.registerUser(user);

        redirectAttributes.addFlashAttribute("message", "Tạo tài khoản thành công!");
        return "redirect:/users/new";
    }


    @GetMapping("/profile")
    public String profilePage(Model model, Authentication authentication) {

        CustomUserDetails current =
                (CustomUserDetails) authentication.getPrincipal();

        User currentUser = current.getUser();

        model.addAttribute("user", currentUser);

        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(
            @ModelAttribute("user") User form,
            Authentication authentication,
            RedirectAttributes ra
    ) {
        CustomUserDetails current =
                (CustomUserDetails) authentication.getPrincipal();
        User user = current.getUser();

        user.setFullName(form.getFullName());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setAddress(form.getAddress());

        userService.updateUser(user.getUserID(), user);

        ra.addFlashAttribute("success", "Cập nhật hồ sơ thành công!");

        return "redirect:/member/profile";
    }


    @GetMapping("/change_password")
    public String changePasswordPage() {
        return "change_password"; 
    }
    @PostMapping("/change_password")
    public String changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Authentication authentication,
            RedirectAttributes ra
    ) {

        CustomUserDetails current =
                (CustomUserDetails) authentication.getPrincipal();
        User user = current.getUser();

        if (!newPassword.equals(confirmPassword)) {
            ra.addFlashAttribute("passwordError", "Xác nhận mật khẩu không khớp!");
            return "redirect:/member/change_password";
        }

        if (!userService.checkPassword(user, oldPassword)) {
            ra.addFlashAttribute("passwordError", "Mật khẩu hiện tại không đúng!");
            return "redirect:/member/change_password";
        }

        userService.updatePassword(user.getUserID(), newPassword);

        ra.addFlashAttribute("passwordSuccess", "Đổi mật khẩu thành công!");

        return "redirect:/member/change_password";
    }
}
