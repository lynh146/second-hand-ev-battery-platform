package com.example.service;

import com.example.model.Complaint;
import java.util.List;

public interface ComplaintService {
    List<Complaint> findAll();
    List<Complaint> findPending();
    Complaint findById(Long id);
    Complaint update(Complaint complaint);
}
