package com.claimedge.repository;

import com.claimedge.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByStatus(Claim.ClaimStatus status);
    List<Claim> findByPolicyId(Long policyId);
}
