 
package com.example.repository;

import com.example.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

     
    Optional<Transaction> findByPayment_PaymentID(Long paymentId);

     
    long countByStatus(String status);
}
