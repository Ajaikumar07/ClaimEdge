package com.claimedge.repository;

import com.claimedge.entity.UnderwritingApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnderwritingRepository extends JpaRepository<UnderwritingApplication, Long> {
}
