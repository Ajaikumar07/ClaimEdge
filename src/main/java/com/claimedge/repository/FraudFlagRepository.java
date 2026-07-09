package com.claimedge.repository;

import com.claimedge.entity.FraudFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FraudFlagRepository extends JpaRepository<FraudFlag, Long> {
    List<FraudFlag> findByClaimId(Long claimId);
    List<FraudFlag> findByStatus(String status);
    List<FraudFlag> findBySeverity(String severity);
    long countByStatus(String status);
    long countBySeverity(String severity);
}
