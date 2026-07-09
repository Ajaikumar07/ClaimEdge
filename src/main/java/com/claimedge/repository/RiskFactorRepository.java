package com.claimedge.repository;

import com.claimedge.entity.RiskFactor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RiskFactorRepository extends JpaRepository<RiskFactor, Long> {
    List<RiskFactor> findByApplicationId(Long applicationId);
}
