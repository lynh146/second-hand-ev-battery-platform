package com.example.service.impl;

import com.example.model.Complaint;
import com.example.repository.ComplaintRepository;
import com.example.service.ComplaintService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintServiceImpl(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    @Override
    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }

    @Override
    public List<Complaint> findPending() {
        return complaintRepository.findByStatus("pending");
    }

    @Override
    public Complaint findById(Long id) {
        return complaintRepository.findById(id).orElse(null);
    }

    @Override
    public Complaint update(Complaint complaint) {
        if (complaint.getProcessedAt() == null) {
            complaint.setProcessedAt(LocalDateTime.now());
        }
        return complaintRepository.save(complaint);
    }
}
