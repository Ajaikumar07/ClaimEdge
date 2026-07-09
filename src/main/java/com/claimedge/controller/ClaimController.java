package com.claimedge.controller;

import com.claimedge.entity.Claim;
import com.claimedge.service.ClaimService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private final ClaimService service;

    public ClaimController(ClaimService service) {
        this.service = service;
    }

    @PostMapping("/submit")
    public Claim submit(@RequestBody Claim claim) {
        return service.submitClaim(claim);
    }

    @GetMapping
    public List<Claim> getAll() {
        return service.getAllClaims();
    }

    @GetMapping("/{id}")
    public Claim get(@PathVariable Long id) {
        return service.getClaimById(id);
    }

    @PutMapping("/{id}")
    public Claim update(@PathVariable Long id, @RequestBody Claim claim) {
        return service.updateClaim(id, claim);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteClaim(id);
        return "Claim deleted successfully";
    }

    @PatchMapping("/{id}/assign")
    public Claim assignAdjuster(@PathVariable Long id, @RequestParam Long adjusterId) {
        return service.assignAdjuster(id, adjusterId);
    }

    @PatchMapping("/{id}/approve")
    public Claim approve(@PathVariable Long id) {
        return service.approveClaim(id);
    }

    @PatchMapping("/{id}/reject")
    public Claim reject(@PathVariable Long id, @RequestParam String reason) {
        return service.rejectClaim(id, reason);
    }

    @PatchMapping("/{id}/settle")
    public Claim settle(@PathVariable Long id) {
        return service.settleClaim(id);
    }

    @PatchMapping("/{id}/reopen")
    public Claim reopen(@PathVariable Long id) {
        return service.reopenClaim(id);
    }

    @GetMapping("/status/submitted")
    public List<Claim> getSubmitted() {
        return service.getByStatus(Claim.ClaimStatus.SUBMITTED);
    }

    @GetMapping("/status/under-review")
    public List<Claim> getUnderReview() {
        return service.getByStatus(Claim.ClaimStatus.UNDER_REVIEW);
    }

    @GetMapping("/status/approved")
    public List<Claim> getApproved() {
        return service.getByStatus(Claim.ClaimStatus.APPROVED);
    }

    @GetMapping("/status/rejected")
    public List<Claim> getRejected() {
        return service.getByStatus(Claim.ClaimStatus.REJECTED);
    }

    @GetMapping("/status/settled")
    public List<Claim> getSettled() {
        return service.getByStatus(Claim.ClaimStatus.SETTLED);
    }
}
