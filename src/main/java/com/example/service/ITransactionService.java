package com.example.service;

import com.example.model.Transaction;
import java.util.List;
import java.util.Optional;

public interface ITransactionService {
    List<Transaction> getAllTransactions();
    Optional<Transaction> getTransactionById(Long id);
    Transaction createTransaction(Transaction transaction);
    Transaction updateTransaction(Long id, Transaction transaction);
    void deleteTransaction(Long id);
}
