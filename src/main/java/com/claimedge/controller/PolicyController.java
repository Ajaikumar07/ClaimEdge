package com.claimedge.controller;

import com.claimedge.entity.Policy;
import com.claimedge.service.PolicyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService service;

    public PolicyController(PolicyService service) {
        this.service = service;
    }

    @PostMapping
    public Policy create(@RequestBody Policy policy) {
        return service.createPolicy(policy);
    }

    @GetMapping
    public List<Policy> getAll() {
        return service.getAllPolicies();
    }

    @GetMapping("/{id}")
    public Policy get(@PathVariable Integer id) {
        return service.getPolicy(id);
    }

    @PutMapping("/{id}")
    public Policy update(@PathVariable Integer id, @RequestBody Policy policy) {
        return service.updatePolicy(id, policy);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        service.deletePolicy(id);
        return "Policy deleted successfully";
    }

    @PatchMapping("/{id}/activate")
    public Policy activate(@PathVariable Integer id) {
        return service.activatePolicy(id);
    }

    @PatchMapping("/{id}/lapse")
    public Policy lapse(@PathVariable Integer id) {
        return service.lapsePolicy(id);
    }

    @PatchMapping("/{id}/cancel")
    public Policy cancel(@PathVariable Integer id) {
        return service.cancelPolicy(id);
    }
}
