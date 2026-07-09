package com.claimedge.controller;

import com.claimedge.entity.PremiumPayment;
import com.claimedge.service.PremiumPaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PremiumPaymentController {

    private final PremiumPaymentService service;

    public PremiumPaymentController(PremiumPaymentService service) {
        this.service = service;
    }

    @PostMapping("/collect")
    public PremiumPayment collect(@RequestBody PremiumPayment payment) {
        return service.collectPayment(payment);
    }

    @GetMapping
    public List<PremiumPayment> getAll() {
        return service.getAllPayments();
    }

    @GetMapping("/{id}")
    public PremiumPayment get(@PathVariable Long id) {
        return service.getPaymentById(id);
    }

    @GetMapping("/policy/{policyId}")
    public List<PremiumPayment> getByPolicy(@PathVariable Long policyId) {
        return service.getPaymentsByPolicy(policyId);
    }

    @GetMapping("/outstanding")
    public List<PremiumPayment> getOutstanding() {
        return service.getOutstandingPayments();
    }

    @PatchMapping("/{id}/mark-overdue")
    public PremiumPayment markOverdue(@PathVariable Long id) {
        return service.markOverdue(id);
    }

    @PostMapping("/{id}/refund")
    public String refund(@PathVariable Long id) {
        return service.refundPayment(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deletePayment(id);
        return "Payment deleted successfully";
    }
}
