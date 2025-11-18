package com.example.service;

import com.example.model.Transaction;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ITransactionService {
    
  
    Long createTransaction(Long listingId, Long buyerId, BigDecimal totalAmount);
    
     
    List<Transaction> getAllTransactions();  
    Optional<Transaction> getTransactionById(Long id);  
    Transaction createTransaction(Transaction transaction);  
    void deleteTransaction(Long id);  T
}