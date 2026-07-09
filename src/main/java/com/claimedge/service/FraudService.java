package com.claimedge.service;

import com.claimedge.entity.FraudFlag;
import com.claimedge.repository.FraudFlagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FraudService {

    private final FraudFlagRepository repo;

    public FraudService(FraudFlagRepository repo) {
        this.repo = repo;
    }

    public FraudFlag addFlag(FraudFlag flag) {
        if (flag.getClaimId() == null) throw new RuntimeException("claimId is required");
        if (flag.getFraudType() == null) flag.setFraudType("NORMAL");

        switch (flag.getFraudType().toUpperCase()) {
            case "DUPLICATE_CLAIM", "IDENTITY_FRAUD", "GHOST_CLAIM" -> flag.setSeverity("HIGH");
            case "INFLATED_CLAIM", "SUSPICIOUS_PATTERN"             -> flag.setSeverity("MEDIUM");
            default                                                  -> flag.setSeverity("LOW");
        }

        // Auto-escalate if claim already flagged
        if (!repo.findByClaimId(flag.getClaimId()).isEmpty()) {
            flag.setFraudType("DUPLICATE_CLAIM");
            flag.setSeverity("HIGH");
        }

        return repo.save(flag);
    }

    public List<FraudFlag> getAllFlags() { return repo.findAll(); }

    public FraudFlag getFlagById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Flag not found: " + id));
    }

    public List<FraudFlag> getFlagsByClaimId(Long claimId) { return repo.findByClaimId(claimId); }
    public List<FraudFlag> getFlagsByStatus(String status) { return repo.findByStatus(status); }
    public List<FraudFlag> getFlagsBySeverity(String severity) { return repo.findBySeverity(severity); }

    public FraudFlag investigateFlag(Long id, String reviewedBy, String notes) {
        FraudFlag flag = getFlagById(id);
        if (!"OPEN".equals(flag.getStatus())) {
            throw new RuntimeException("Only OPEN flags can be investigated");
        }
        flag.setStatus("INVESTIGATED");
        flag.setDetectedBy(reviewedBy);
        flag.setDescription(notes);
        return repo.save(flag);
    }

    public FraudFlag clearFlag(Long id, String clearedBy) {
        FraudFlag flag = getFlagById(id);
        if (!"INVESTIGATED".equals(flag.getStatus())) {
            throw new RuntimeException("Only INVESTIGATED flags can be cleared");
        }
        flag.setStatus("CLEARED");
        flag.setDetectedBy(clearedBy);
        return repo.save(flag);
    }

    public void deleteFlag(Long id) {
        if (!repo.existsById(id)) throw new RuntimeException("Flag not found: " + id);
        repo.deleteById(id);
    }
}
