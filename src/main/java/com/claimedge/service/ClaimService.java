package com.claimedge.service;

import com.claimedge.entity.Claim;
import com.claimedge.repository.ClaimRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClaimService {

    private final ClaimRepository repo;
    private final NotificationService notificationService;

    public ClaimService(ClaimRepository repo, NotificationService notificationService) {
        this.repo = repo;
        this.notificationService = notificationService;
    }

    public Claim submitClaim(Claim claim) {
        claim.setStatus(Claim.ClaimStatus.SUBMITTED);
        claim.setSubmissionDate(LocalDate.now());
        notificationService.createNotification(
                claim.getClaimId(),
                "Your claim has been submitted successfully.",
                "CLAIM_SUBMITTED"
        );
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
        notificationService.createNotification(
                claim.getClaimId(),
                "Your claim has been approved successfully.",
                "CLAIM_APPROVED"
        );
        return repo.save(claim);
    }

    public Claim rejectClaim(Long id, String reason) {
        Claim claim = getClaimById(id);
        if (claim.getStatus() != Claim.ClaimStatus.UNDER_REVIEW) {
            throw new RuntimeException("Only UNDER_REVIEW claims can be rejected");
        }
        claim.setStatus(Claim.ClaimStatus.REJECTED);
        claim.setReason(reason);
        notificationService.createNotification(
                claim.getClaimId(),
                "Your claim has been rejected.",
                "CLAIM_REJECTED"
        );
        return repo.save(claim);
    }

    public Claim settleClaim(Long id) {
        Claim claim = getClaimById(id);
        if (claim.getStatus() != Claim.ClaimStatus.APPROVED) {
            throw new RuntimeException("Only APPROVED claims can be settled");
        }
        claim.setStatus(Claim.ClaimStatus.SETTLED);
        notificationService.createNotification(
                claim.getClaimId(),
                "Your claim has been settled successfully.",
                "CLAIM_SETTLED"
        );
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
