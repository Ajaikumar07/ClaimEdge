package com.claimedge.repository;

import com.claimedge.entity.ClaimAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClaimAssessmentRepository extends JpaRepository<ClaimAssessment, Long> {
    List<ClaimAssessment> findByClaimId(Long claimId);
}
