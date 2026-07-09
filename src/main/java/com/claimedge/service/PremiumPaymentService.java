package com.claimedge.service;

import com.claimedge.entity.PremiumPayment;
import com.claimedge.repository.PremiumPaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PremiumPaymentService {

    private final PremiumPaymentRepository repo;

    public PremiumPaymentService(PremiumPaymentRepository repo) {
        this.repo = repo;
    }

    public PremiumPayment collectPayment(PremiumPayment payment) {
        payment.setPaymentDate(LocalDate.now());
        payment.setStatus(PremiumPayment.PaymentStatus.RECEIVED);
        return repo.save(payment);
    }

    public List<PremiumPayment> getAllPayments() {
        return repo.findAll();
    }

    public PremiumPayment getPaymentById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found: " + id));
    }

    public List<PremiumPayment> getPaymentsByPolicy(Long policyId) {
        return repo.findByPolicyId(policyId);
    }

    public List<PremiumPayment> getOutstandingPayments() {
        return repo.findByStatusIn(List.of(
                PremiumPayment.PaymentStatus.PENDING,
                PremiumPayment.PaymentStatus.OVERDUE));
    }

    public PremiumPayment markOverdue(Long id) {
        PremiumPayment payment = getPaymentById(id);
        payment.setStatus(PremiumPayment.PaymentStatus.OVERDUE);
        return repo.save(payment);
    }

    public String refundPayment(Long id) {
        PremiumPayment payment = getPaymentById(id);
        if (payment.getStatus() != PremiumPayment.PaymentStatus.RECEIVED) {
            throw new RuntimeException("Refund allowed only for RECEIVED payments");
        }
        payment.setStatus(PremiumPayment.PaymentStatus.PENDING);
        repo.save(payment);
        return "Refund processed successfully";
    }

    public void deletePayment(Long id) {
        repo.deleteById(id);
    }
}
