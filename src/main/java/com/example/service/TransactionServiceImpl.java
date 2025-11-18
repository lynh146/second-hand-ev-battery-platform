package com.example.service;

import com.example.model.Transaction;
import com.example.model.User; 
import com.example.model.Listing;  
import com.example.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements ITransactionService {

    private final TransactionRepository transactionRepository;
     

    @Override
    public Long createTransaction(Long listingId, Long buyerId, BigDecimal totalAmount) {
        
        Transaction tx = Transaction.builder()
                .totalAmount(totalAmount)
                .createdAt(LocalDateTime.now())
                .status("INITIATED") 
              
                .build();
        
        tx = transactionRepository.save(tx);
        return tx.getTransactionID();
    }

    
    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}