package com.claimedge.service;

import com.claimedge.entity.Endorsement;
import com.claimedge.repository.EndorsementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EndorsementService {

    private final EndorsementRepository repo;

    public EndorsementService(EndorsementRepository repo) {
        this.repo = repo;
    }

    public Endorsement createEndorsement(Endorsement endorsement) {
        return repo.save(endorsement);
    }

    public List<Endorsement> getAllEndorsements() {
        return repo.findAll();
    }

    public Endorsement getEndorsement(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Endorsement not found: " + id));
    }

    public Endorsement updateEndorsement(Long id, Endorsement updated) {
        Endorsement e = getEndorsement(id);
        if (updated.getPolicy() != null) e.setPolicy(updated.getPolicy());
        if (updated.getChangeType() != null) e.setChangeType(updated.getChangeType());
        if (updated.getEffectiveDate() != null) e.setEffectiveDate(updated.getEffectiveDate());
        if (updated.getStatus() != null) e.setStatus(updated.getStatus());
        return repo.save(e);
    }

    public void deleteEndorsement(Long id) {
        repo.deleteById(id);
    }

    public List<Endorsement> getEndorsementsByPolicyId(Long policyId) {
        return repo.findByPolicy_PolicyId(policyId);
    }
}
