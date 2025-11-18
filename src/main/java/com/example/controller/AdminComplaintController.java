package com.example.controller;

import com.example.model.Complaint;
import com.example.service.ComplaintService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/complaints")
public class AdminComplaintController {

    private final ComplaintService complaintService;

    public AdminComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    // Hiển thị danh sách khiếu nại
    @GetMapping
    public String viewComplaints(Model model) {
        List<Complaint> complaints = complaintService.findPending();
        model.addAttribute("complaints", complaints);
        return "admin_complaints";
    }

    // Xem chi tiết
    @GetMapping("/view/{id}")
    public String viewComplaint(@PathVariable Long id, Model model) {
        Complaint complaint = complaintService.findById(id);
        if (complaint == null) {
            model.addAttribute("error", "Không tìm thấy khiếu nại!");
            return "redirect:/admin/complaints";
        }
        model.addAttribute("complaint", complaint);
        return "admin_complaint_detail";
    }

    // Duyệt (giải quyết)
    @PostMapping("/resolve/{id}")
    public String resolveComplaint(@PathVariable Long id) {
        Complaint complaint = complaintService.findById(id);
        if (complaint != null) {
            complaint.setStatus("resolved"); 
            complaintService.update(complaint);
        }
        return "redirect:/admin/complaints";
    }

    // Từ chối
    @PostMapping("/reject/{id}")
    public String rejectComplaint(@PathVariable Long id) {
        Complaint complaint = complaintService.findById(id);
        if (complaint != null) {
            complaint.setStatus("rejected"); 
            complaintService.update(complaint);
        }
        return "redirect:/admin/complaints";
    }
}
