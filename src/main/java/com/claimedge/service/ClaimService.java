package com.claimedge.service;

import com.claimedge.entity.Claim;
import com.claimedge.repository.ClaimRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClaimService {

    private final ClaimRepository repo;

    public ClaimService(ClaimRepository repo) {
        this.repo = repo;
    }

    public Claim submitClaim(Claim claim) {
        claim.setStatus(Claim.ClaimStatus.SUBMITTED);
        claim.setSubmissionDate(LocalDate.now());
        return repo.save(claim);
    }

    public List<Claim> getAllClaims() {
        return repo.findAll();
    }

    public Claim getClaimById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Claim not found: " + id));
    }

    public List<Claim> getByStatus(Claim.ClaimStatus status) {
        return repo.findByStatus(status);
    }

    public Claim updateClaim(Long id, Claim updated) {
        Claim claim = getClaimById(id);
        claim.setPolicyId(updated.getPolicyId());
        claim.setClaimType(updated.getClaimType());
        claim.setIncidentDate(updated.getIncidentDate());
        claim.setClaimAmount(updated.getClaimAmount());
        claim.setAssignedAdjusterId(updated.getAssignedAdjusterId());
        claim.setStatus(updated.getStatus());
        return repo.save(claim);
    }

    public void deleteClaim(Long id) {
        repo.deleteById(id);
    }

    public Claim assignAdjuster(Long id, Long adjusterId) {
        Claim claim = getClaimById(id);
        if (claim.getStatus() != Claim.ClaimStatus.SUBMITTED) {
            throw new RuntimeException("Only SUBMITTED claims can be assigned");
        }
        claim.setAssignedAdjusterId(adjusterId);
        claim.setStatus(Claim.ClaimStatus.UNDER_REVIEW);
        return repo.save(claim);
    }

    public Claim approveClaim(Long id) {
        Claim claim = getClaimById(id);
        if (claim.getStatus() != Claim.ClaimStatus.UNDER_REVIEW) {
            throw new RuntimeException("Only UNDER_REVIEW claims can be approved");
        }
        claim.setStatus(Claim.ClaimStatus.APPROVED);
        return repo.save(claim);
    }

    public Claim rejectClaim(Long id, String reason) {
        Claim claim = getClaimById(id);
        if (claim.getStatus() != Claim.ClaimStatus.UNDER_REVIEW) {
            throw new RuntimeException("Only UNDER_REVIEW claims can be rejected");
        }
        claim.setStatus(Claim.ClaimStatus.REJECTED);
        claim.setReason(reason);
        return repo.save(claim);
    }

    public Claim settleClaim(Long id) {
        Claim claim = getClaimById(id);
        if (claim.getStatus() != Claim.ClaimStatus.APPROVED) {
            throw new RuntimeException("Only APPROVED claims can be settled");
        }
        claim.setStatus(Claim.ClaimStatus.SETTLED);
        return repo.save(claim);
    }

    public Claim reopenClaim(Long id) {
        Claim claim = getClaimById(id);
        if (claim.getStatus() != Claim.ClaimStatus.REJECTED
                && claim.getStatus() != Claim.ClaimStatus.SETTLED) {
            throw new RuntimeException("Only REJECTED or SETTLED claims can be reopened");
        }
        claim.setStatus(Claim.ClaimStatus.UNDER_REVIEW);
        return repo.save(claim);
    }
}
