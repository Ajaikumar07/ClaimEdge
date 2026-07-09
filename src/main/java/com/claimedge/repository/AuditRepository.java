package com.claimedge.repository;

import com.claimedge.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<AuditLog, Long> {
}
