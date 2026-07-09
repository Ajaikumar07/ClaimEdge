package com.claimedge.controller;

import com.claimedge.entity.FraudFlag;
import com.claimedge.service.FraudService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fraud")
@PreAuthorize("hasAnyRole('ADMIN','FRAUD_ANALYST')")
public class FraudController {

    private final FraudService service;

    public FraudController(FraudService service) {
        this.service = service;
    }

    @PostMapping
    public FraudFlag addFlag(@RequestBody FraudFlag flag) {
        return service.addFlag(flag);
    }

    @GetMapping
    public List<FraudFlag> getAll() {
        return service.getAllFlags();
    }

    @GetMapping("/{id}")
    public FraudFlag get(@PathVariable Long id) {
        return service.getFlagById(id);
    }

    @GetMapping("/claim/{claimId}")
    public List<FraudFlag> getByClaimId(@PathVariable Long claimId) {
        return service.getFlagsByClaimId(claimId);
    }

    @GetMapping("/status/{status}")
    public List<FraudFlag> getByStatus(@PathVariable String status) {
        return service.getFlagsByStatus(status);
    }

    @GetMapping("/severity/{severity}")
    public List<FraudFlag> getBySeverity(@PathVariable String severity) {
        return service.getFlagsBySeverity(severity);
    }

    @PutMapping("/{id}/investigate")
    public FraudFlag investigate(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return service.investigateFlag(id, body.get("reviewedBy"), body.get("notes"));
    }

    @PutMapping("/{id}/clear")
    public FraudFlag clear(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return service.clearFlag(id, body.get("clearedBy"));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteFlag(id);
        return "Flag deleted successfully";
    }
}
