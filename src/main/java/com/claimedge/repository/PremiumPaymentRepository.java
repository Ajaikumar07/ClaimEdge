package com.claimedge.repository;

import com.claimedge.entity.PremiumPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PremiumPaymentRepository extends JpaRepository<PremiumPayment, Long> {
    List<PremiumPayment> findByPolicyId(Long policyId);
    List<PremiumPayment> findByStatusIn(List<PremiumPayment.PaymentStatus> statuses);
}
