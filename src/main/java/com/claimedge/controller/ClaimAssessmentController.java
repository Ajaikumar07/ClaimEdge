package com.claimedge.controller;

import com.claimedge.entity.Claim;
import com.claimedge.entity.ClaimAssessment;
import com.claimedge.service.ClaimAssessmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assessments")
public class ClaimAssessmentController {

    private final ClaimAssessmentService service;

    public ClaimAssessmentController(ClaimAssessmentService service) {
        this.service = service;
    }

    @PostMapping
    public ClaimAssessment create(@RequestBody ClaimAssessment assessment) {
        return service.createAssessment(assessment);
    }

    @GetMapping
    public List<ClaimAssessment> getAll() {
        return service.getAllAssessments();
    }

    @GetMapping("/{id}")
    public ClaimAssessment get(@PathVariable Long id) {
        return service.getAssessmentById(id);
    }

    @PutMapping("/{id}")
    public ClaimAssessment update(@PathVariable Long id, @RequestBody ClaimAssessment assessment) {
        return service.updateAssessment(id, assessment);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteAssessment(id);
        return "Assessment deleted successfully";
    }

    @PostMapping("/claims/{id}/validate")
    public Claim validateClaim(@PathVariable Long id) {
        return service.validateClaim(id);
    }

    @PostMapping("/claims/{id}/fraud-check")
    public String fraudCheck(@PathVariable Long id) {
        return service.fraudCheck(id);
    }

    @GetMapping("/claims/{id}/timeline")
    public List<String> getTimeline(@PathVariable Long id) {
        return service.getTimeline(id);
    }
}
