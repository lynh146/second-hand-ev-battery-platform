package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.model.Complaint;
import com.example.service.ComplaintService;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin("*") // Cho phép frontend React truy cập
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    // 1. Lấy tất cả complaint
    @GetMapping
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.findAll());
    }

    // 2. Lấy complaint đang pending
    @GetMapping("/pending")
    public ResponseEntity<List<Complaint>> getPendingComplaints() {
        return ResponseEntity.ok(complaintService.findPending());
    }

    // 3. Tìm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Complaint> getComplaintById(@PathVariable Long id) {
        Complaint complaint = complaintService.findById(id);
        if (complaint == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(complaint);
    }

    // 4. Update complaint (thường để update status)
    @PutMapping("/{id}")
    public ResponseEntity<Complaint> updateComplaint(
            @PathVariable Long id,
            @RequestBody Complaint updateData) {

        Complaint existing = complaintService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        // Chỉ cập nhật các thuộc tính cần thiết
        existing.setStatus(updateData.getStatus());
        existing.setProcessedAt(updateData.getProcessedAt());

        return ResponseEntity.ok(complaintService.update(existing));
    }
}
