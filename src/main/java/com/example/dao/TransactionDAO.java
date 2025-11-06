package com.example.dao;

import com.example.model.Transaction;
import java.util.List;
import java.util.Optional;

public interface TransactionDAO {
    List<Transaction> findAll();
    Optional<Transaction> findById(Long id);
    Transaction save(Transaction transaction);
    void deleteById(Long id);
}
