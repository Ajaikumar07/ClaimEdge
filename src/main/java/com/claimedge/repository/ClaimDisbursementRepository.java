package com.claimedge.repository;

import com.claimedge.entity.ClaimDisbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClaimDisbursementRepository extends JpaRepository<ClaimDisbursement, Long> {
    List<ClaimDisbursement> findByClaimId(Long claimId);
    List<ClaimDisbursement> findByStatus(ClaimDisbursement.DisbursementStatus status);
}
