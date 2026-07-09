package com.claimedge.controller;

import com.claimedge.entity.ComplianceReport;
import com.claimedge.service.ComplianceService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/compliance")
@PreAuthorize("hasAnyRole('ADMIN','COMPLIANCE')")
public class ComplianceController {

    private final ComplianceService service;

    public ComplianceController(ComplianceService service) {
        this.service = service;
    }

    @PostMapping
    public ComplianceReport addReport(@RequestBody ComplianceReport report) {
        return service.addReport(report);
    }

    @GetMapping
    public List<ComplianceReport> getAll() {
        return service.getAllReports();
    }

    @GetMapping("/{id}")
    public ComplianceReport get(@PathVariable Long id) {
        return service.getReportById(id);
    }

    @GetMapping("/status/{status}")
    public List<ComplianceReport> getByStatus(@PathVariable String status) {
        return service.getByStatus(status);
    }

    @PutMapping("/{id}/approve")
    public ComplianceReport approve(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return service.approveReport(id, body.get("approvedBy"));
    }

    @PutMapping("/{id}/reject")
    public ComplianceReport reject(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return service.rejectReport(id, body.get("reason"));
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteReport(id);
        return "Report deleted successfully";
    }
}
