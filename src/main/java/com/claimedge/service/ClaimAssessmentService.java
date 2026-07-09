package com.claimedge.service;

import com.claimedge.entity.Claim;
import com.claimedge.entity.ClaimAssessment;
import com.claimedge.repository.ClaimAssessmentRepository;
import com.claimedge.repository.ClaimRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClaimAssessmentService {

    private final ClaimAssessmentRepository repo;
    private final ClaimRepository claimRepo;

    public ClaimAssessmentService(ClaimAssessmentRepository repo, ClaimRepository claimRepo) {
        this.repo = repo;
        this.claimRepo = claimRepo;
    }

    public ClaimAssessment createAssessment(ClaimAssessment assessment) {
        assessment.setAssessmentDate(LocalDate.now());
        return repo.save(assessment);
    }

    public List<ClaimAssessment> getAllAssessments() {
        return repo.findAll();
    }

    public ClaimAssessment getAssessmentById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Assessment not found: " + id));
    }

    public ClaimAssessment updateAssessment(Long id, ClaimAssessment updated) {
        ClaimAssessment old = getAssessmentById(id);
        old.setClaimId(updated.getClaimId());
        old.setAdjusterId(updated.getAdjusterId());
        old.setApprovedAmount(updated.getApprovedAmount());
        old.setNotes(updated.getNotes());
        old.setAssessmentDate(updated.getAssessmentDate());
        return repo.save(old);
    }

    public void deleteAssessment(Long id) {
        repo.deleteById(id);
    }

    public Claim validateClaim(Long claimId) {
        Claim claim = claimRepo.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found: " + claimId));
        if (claim.getPolicyId() == null || claim.getClaimAmount() <= 0) {
            throw new RuntimeException("Invalid claim details");
        }
        if (repo.findByClaimId(claimId).isEmpty()) {
            throw new RuntimeException("Claim not yet assessed");
        }
        if (claim.getStatus() != Claim.ClaimStatus.UNDER_REVIEW) {
            throw new RuntimeException("Claim must be UNDER_REVIEW to validate");
        }
        claim.setStatus(Claim.ClaimStatus.APPROVED);
        return claimRepo.save(claim);
    }

    public String fraudCheck(Long claimId) {
        Claim claim = claimRepo.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found: " + claimId));
        if (claim.getClaimAmount() > 500000) {
            return "Fraud suspected: high claim amount";
        }
        for (ClaimAssessment a : repo.findByClaimId(claimId)) {
            if (a.getApprovedAmount() > claim.getClaimAmount()) {
                return "Fraud suspected: approved amount exceeds claim";
            }
        }
        return "No fraud detected";
    }

    public List<String> getTimeline(Long claimId) {
        Claim claim = claimRepo.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found: " + claimId));
        List<String> timeline = new ArrayList<>();
        timeline.add("Submitted on: " + claim.getSubmissionDate());
        if (claim.getAssignedAdjusterId() != null) {
            timeline.add("Assigned to Adjuster: " + claim.getAssignedAdjusterId());
        }
        for (ClaimAssessment a : repo.findByClaimId(claimId)) {
            timeline.add("Assessment by Adjuster " + a.getAdjusterId()
                    + " on " + a.getAssessmentDate());
        }
        timeline.add("Current Status: " + claim.getStatus());
        return timeline;
    }
}
