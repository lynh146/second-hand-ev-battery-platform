package com.example.service;

import com.example.model.Payment;
import com.example.model.Transaction;
import com.example.repository.PaymentRepository;
import com.example.repository.TransactionRepository;
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
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepository paymentRepository;
    private final TransactionRepository transactionRepository; 
    private static final BigDecimal PLATFORM_FEE_RATE = BigDecimal.valueOf(0.05);

    private BigDecimal calculateCommission(BigDecimal amount) {
        return amount.multiply(PLATFORM_FEE_RATE);
    }

    // --- HÀM MOMO VÀ TẠO RECORD (Code đã có, giữ nguyên) ---
    @Override
    @Transactional
    public Long createPaymentRecord(Long transactionId, BigDecimal totalAmount, String paymentMethod) {
        Transaction transaction = transactionRepository.findById(transactionId)
            .orElseThrow(() -> new RuntimeException("Transaction not found."));

        BigDecimal commissionFee = calculateCommission(totalAmount);
        
        Payment newPayment = Payment.builder()
                .transaction(transaction)
                .amount(totalAmount)
                .paymentMethod(paymentMethod)
                .commissionFee(commissionFee)
                .status("INITIATED")
                .build();
        
        return paymentRepository.save(newPayment).getPaymentID();
    }
    
    @Override
    @Transactional
    public void processMomoSuccess(String orderId, Map<String, Object> momoResponse) {
        // ... (Logic cập nhật thành công đã có, giữ nguyên)
        // [Code processMomoSuccess]
        Long paymentId = Long.parseLong(orderId);
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found."));

        // Cập nhật chi tiết MoMo
        payment.setStatus("PAID");
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentGatewayRef(orderId); 
        payment.setGatewayTransactionID(String.valueOf(momoResponse.get("transId")));
        payment.setGatewayResponseCode(String.valueOf(momoResponse.get("resultCode")));
        payment.setSecureHash((String) momoResponse.get("signature")); 

        paymentRepository.save(payment);
        
        // Cập nhật trạng thái Transaction
        Transaction transaction = payment.getTransaction();
        transaction.setStatus("SUCCESS");
        transactionRepository.save(transaction);
    }
    
    @Override
    @Transactional
    public void processMomoFailure(String orderId, Map<String, Object> momoResponse) {
        // ... (Logic cập nhật thất bại đã có, giữ nguyên)
        // [Code processMomoFailure]
        Long paymentId = Long.parseLong(orderId);
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found."));
        
        payment.setStatus("FAILED");
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentGatewayRef(orderId);
        payment.setGatewayResponseCode(String.valueOf(momoResponse.get("resultCode")));
        
        paymentRepository.save(payment);
        
        Transaction transaction = payment.getTransaction();
        transaction.setStatus("FAILED");
        transactionRepository.save(transaction);
    }

    // --- TRIỂN KHAI CÁC PHƯƠNG THỨC MỚI ---
    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
    
    @Override
    public List<Payment> getPaymentsByTransactionId(Long transactionId) {
        return paymentRepository.findByTransaction_TransactionID(transactionId);
    }
}