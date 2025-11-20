 
package com.example.service.impl;

import com.example.model.Listing;
import com.example.model.Payment;
import com.example.model.Transaction;
import com.example.model.User;
import com.example.repository.PaymentRepository;
import com.example.repository.TransactionRepository;
import com.example.service.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final TransactionRepository transactionRepository;

    private static final BigDecimal PLATFORM_FEE_RATE = BigDecimal.valueOf(0.05);

    private BigDecimal calcFee(BigDecimal amount) {
        if (amount == null) return BigDecimal.ZERO;
        return amount.multiply(PLATFORM_FEE_RATE);
    }
<<<<<<< HEAD

    // --- HÀM MOMO VÀ TẠO RECORD (Code đã có, giữ nguyên) ---
    @Override
    @Transactional
    public Long createPaymentRecord(Long transactionId, BigDecimal totalAmount, String paymentMethod) {
        Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() -> new RuntimeException("Transaction not found."));

        BigDecimal commissionFee = calculateCommission(totalAmount);
        
        Payment newPayment = Payment.builder()
                .transaction(transaction)
=======

     
    @Override
    public Long createPaymentRecord(Long transactionId,
                                    BigDecimal totalAmount,
                                    String paymentMethod) {

        Transaction tx = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found: " + transactionId));

        User buyer   = tx.getBuyer();
        Listing listing = tx.getListing();

        Payment payment = Payment.builder()
                .user(buyer)
                .listing(listing)
>>>>>>> 3839c5e (refactor: Optimize Payment and Transaction handling)
                .amount(totalAmount)
                .paymentMethod(paymentMethod)
                .status("PENDING")          // đang chờ MoMo
                .commissionFee(calcFee(totalAmount))
                .build();

        payment = paymentRepository.save(payment);

        
<<<<<<< HEAD
        return paymentRepository.save(newPayment).getPaymentID();
=======
        tx.setPayment(payment);
        tx.setStatus("PENDING");
        transactionRepository.save(tx);

        return payment.getPaymentID();
>>>>>>> 3839c5e (refactor: Optimize Payment and Transaction handling)
    }

    
    @Override
    public void processMomoSuccess(String orderId, Map<String, Object> momoResponse) {
<<<<<<< HEAD
        // ... (Logic cập nhật thành công đã có, giữ nguyên)
        // [Code processMomoSuccess]
=======
>>>>>>> 3839c5e (refactor: Optimize Payment and Transaction handling)
        Long paymentId = Long.parseLong(orderId);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + paymentId));

<<<<<<< HEAD
        // Cập nhật chi tiết MoMo
        payment.setStatus("PAID");
=======
        payment.setStatus("SUCCESS");
>>>>>>> 3839c5e (refactor: Optimize Payment and Transaction handling)
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepository.save(payment);
<<<<<<< HEAD
        
        // Cập nhật trạng thái Transaction
        Transaction transaction = payment.getTransaction();
        transaction.setStatus("SUCCESS");
        transactionRepository.save(transaction);
=======

        Transaction tx = transactionRepository
                .findByPayment_PaymentID(paymentId)
                .orElse(null);

        if (tx != null) {
            tx.setStatus("SUCCESS");
            transactionRepository.save(tx);
        }
>>>>>>> 3839c5e (refactor: Optimize Payment and Transaction handling)
    }

    
    @Override
    public void processMomoFailure(String orderId, Map<String, Object> momoResponse) {
<<<<<<< HEAD
        // ... (Logic cập nhật thất bại đã có, giữ nguyên)
        // [Code processMomoFailure]
=======
>>>>>>> 3839c5e (refactor: Optimize Payment and Transaction handling)
        Long paymentId = Long.parseLong(orderId);

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + paymentId));

        payment.setStatus("FAILED");
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepository.save(payment);
<<<<<<< HEAD
        
        Transaction transaction = payment.getTransaction();
        transaction.setStatus("FAILED");
        transactionRepository.save(transaction);
    }

    // --- TRIỂN KHAI CÁC PHƯƠNG THỨC MỚI ---
=======

        Transaction tx = transactionRepository.findByPayment_PaymentID(paymentId).orElse(null);
        if (tx != null) {
            tx.setStatus("FAILED");
            transactionRepository.save(tx);
        }
    }

     
>>>>>>> 3839c5e (refactor: Optimize Payment and Transaction handling)
    @Override
    @Transactional(readOnly = true)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByUser(Long userId) {
        return paymentRepository.findByUser_UserID(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByListing(Long listingId) {
        return paymentRepository.findByListing_ListingID(listingId);
    }

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
<<<<<<< HEAD
    
    @Override
    public List<Payment> getPaymentsByTransactionId(Long transactionId) {
        return paymentRepository.findByTransaction_TransactionID(transactionId);
    }
}
=======
}
>>>>>>> 3839c5e (refactor: Optimize Payment and Transaction handling)
