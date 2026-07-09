package com.claimedge.service;

import com.claimedge.entity.Claim;
import com.claimedge.entity.ClaimDisbursement;
import com.claimedge.repository.ClaimDisbursementRepository;
import com.claimedge.repository.ClaimRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClaimDisbursementService {

    private final ClaimDisbursementRepository repo;
    private final ClaimRepository claimRepo;

    public ClaimDisbursementService(ClaimDisbursementRepository repo, ClaimRepository claimRepo) {
        this.repo = repo;
        this.claimRepo = claimRepo;
    }

    public ClaimDisbursement initiate(ClaimDisbursement disbursement) {
        Claim claim = claimRepo.findById(disbursement.getClaimId())
                .orElseThrow(() -> new RuntimeException("Claim not found: " + disbursement.getClaimId()));
        if (claim.getStatus() != Claim.ClaimStatus.APPROVED) {
            throw new RuntimeException("Disbursement allowed only for APPROVED claims");
        }
        disbursement.setStatus(ClaimDisbursement.DisbursementStatus.PENDING);
        disbursement.setDisbursementDate(null);
        return repo.save(disbursement);
    }

    public ClaimDisbursement process(Long id) {
        ClaimDisbursement d = getDisbursementById(id);
        Claim claim = claimRepo.findById(d.getClaimId())
                .orElseThrow(() -> new RuntimeException("Claim not found"));
        d.setStatus(ClaimDisbursement.DisbursementStatus.PROCESSED);
        d.setDisbursementDate(LocalDate.now());
        claim.setStatus(Claim.ClaimStatus.SETTLED);
        claimRepo.save(claim);
        return repo.save(d);
    }

    public ClaimDisbursement fail(Long id) {
        ClaimDisbursement d = getDisbursementById(id);
        d.setStatus(ClaimDisbursement.DisbursementStatus.FAILED);
        return repo.save(d);
    }

    public ClaimDisbursement retry(Long id) {
        ClaimDisbursement d = getDisbursementById(id);
        if (d.getStatus() != ClaimDisbursement.DisbursementStatus.FAILED) {
            throw new RuntimeException("Only FAILED disbursements can be retried");
        }
        d.setStatus(ClaimDisbursement.DisbursementStatus.PENDING);
        return repo.save(d);
    }

    public List<ClaimDisbursement> getAllDisbursements() {
        return repo.findAll();
    }

    public ClaimDisbursement getDisbursementById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Disbursement not found: " + id));
    }

    public List<ClaimDisbursement> getByClaimId(Long claimId) {
        return repo.findByClaimId(claimId);
    }

    public String getSettlementSummary() {
        long settled = claimRepo.findAll().stream()
                .filter(c -> c.getStatus() == Claim.ClaimStatus.SETTLED).count();
        long pending = repo.findByStatus(ClaimDisbursement.DisbursementStatus.PENDING).size();
        return "Settled Claims: " + settled + " | Pending Disbursements: " + pending;
    }

    public void deleteDisbursement(Long id) {
        repo.deleteById(id);
    }
}
