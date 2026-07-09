package com.claimedge.repository;

import com.claimedge.entity.Endorsement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EndorsementRepository extends JpaRepository<Endorsement, Long> {
    List<Endorsement> findByPolicy_PolicyId(Long policyId);
}
