package com.claimedge.controller;

import com.claimedge.entity.ClaimDisbursement;
import com.claimedge.service.ClaimDisbursementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disbursements")
public class ClaimDisbursementController {

    private final ClaimDisbursementService service;

    public ClaimDisbursementController(ClaimDisbursementService service) {
        this.service = service;
    }

    @PostMapping("/initiate")
    public ClaimDisbursement initiate(@RequestBody ClaimDisbursement disbursement) {
        return service.initiate(disbursement);
    }

    @GetMapping
    public List<ClaimDisbursement> getAll() {
        return service.getAllDisbursements();
    }

    @GetMapping("/{id}")
    public ClaimDisbursement get(@PathVariable Long id) {
        return service.getDisbursementById(id);
    }

    @GetMapping("/claim/{claimId}")
    public List<ClaimDisbursement> getByClaimId(@PathVariable Long claimId) {
        return service.getByClaimId(claimId);
    }

    @PatchMapping("/{id}/process")
    public ClaimDisbursement process(@PathVariable Long id) {
        return service.process(id);
    }

    @PatchMapping("/{id}/fail")
    public ClaimDisbursement fail(@PathVariable Long id) {
        return service.fail(id);
    }

    @PatchMapping("/{id}/retry")
    public ClaimDisbursement retry(@PathVariable Long id) {
        return service.retry(id);
    }

    @GetMapping("/summary")
    public String summary() {
        return service.getSettlementSummary();
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteDisbursement(id);
        return "Disbursement deleted successfully";
    }
}
