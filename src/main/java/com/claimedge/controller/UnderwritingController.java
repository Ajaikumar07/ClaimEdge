package com.claimedge.controller;

import com.claimedge.entity.RiskFactor;
import com.claimedge.entity.UnderwritingApplication;
import com.claimedge.service.UnderwritingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/underwriting")
public class UnderwritingController {

    private final UnderwritingService service;

    public UnderwritingController(UnderwritingService service) {
        this.service = service;
    }

    @PostMapping("/applications")
    public UnderwritingApplication createApplication(@RequestBody UnderwritingApplication app) {
        return service.createApplication(app);
    }

    @GetMapping("/applications")
    public List<UnderwritingApplication> getAllApplications() {
        return service.getAllApplications();
    }

    @GetMapping("/applications/{id}")
    public UnderwritingApplication getApplication(@PathVariable Long id) {
        return service.getApplication(id);
    }

    @PostMapping("/risk-factors")
    public RiskFactor addRiskFactor(@RequestBody RiskFactor factor) {
        return service.addRiskFactor(factor);
    }

    @GetMapping("/risk-factors/{applicationId}")
    public List<RiskFactor> getRiskFactors(@PathVariable Long applicationId) {
        return service.getRiskFactors(applicationId);
    }

    @PostMapping("/applications/{id}/evaluate")
    public UnderwritingApplication evaluate(@PathVariable Long id) {
        return service.evaluateRisk(id);
    }
}
