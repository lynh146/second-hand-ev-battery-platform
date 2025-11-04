package com.example.service.impl;

import com.example.model.Admin;
import com.example.repository.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin login(String email, String password) {
        return adminRepository.findByEmailAndPassword(email, password);
    }
}
