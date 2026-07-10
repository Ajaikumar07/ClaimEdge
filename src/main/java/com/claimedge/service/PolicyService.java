package com.claimedge.service;

import com.claimedge.entity.Policy;
import com.claimedge.repository.PolicyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PolicyService {

    private final PolicyRepository repo;
    private final NotificationService notificationService;

    public PolicyService(PolicyRepository repo, NotificationService notificationService) {
        this.repo = repo;
        this.notificationService = notificationService; 
    }

    public Policy createPolicy(Policy policy) {
        policy.setStatus("DRAFT");
        return repo.save(policy);
    }

    public List<Policy> getAllPolicies() {
        return repo.findAll();
    }

    public Policy getPolicy(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Policy not found: " + id));
    }

    public Policy updatePolicy(Long id, Policy updated) {
        Policy policy = getPolicy(id);
        policy.setPolicyHolderId(updated.getPolicyHolderId());
        policy.setProductType(updated.getProductType());
        policy.setCoverageAmount(updated.getCoverageAmount());
        policy.setPremium(updated.getPremium());
        policy.setStartDate(updated.getStartDate());
        policy.setEndDate(updated.getEndDate());
        notificationService.createNotification(
                policy.getPolicyId(),
                "Your policy has been updated.",
                "POLICY_UPDATED"
        );
        return repo.save(policy);
    }

    public void deletePolicy(Long id) {
        repo.delete(getPolicy(id));
    }

    public Policy activatePolicy(Long id) {
        Policy policy = getPolicy(id);
        policy.setStatus("ACTIVE");
        notificationService.createNotification(
                policy.getPolicyId(),
                "Your policy has been activated.",
                "POLICY_ACTIVATED"
        );
        return repo.save(policy);
    }

    public Policy lapsePolicy(Long id) {
        Policy policy = getPolicy(id);
        policy.setStatus("LAPSED");
        notificationService.createNotification(
                policy.getPolicyId(),
                "Your policy has been lapsed.",
                "POLICY_LAPSED"
        );
        return repo.save(policy);
    }

    public Policy cancelPolicy(Long id) {
        Policy policy = getPolicy(id);
        policy.setStatus("CANCELLED");
        notificationService.createNotification(
                policy.getPolicyId(),
                "Your policy has been cancelled.",
                "POLICY_CANCELLED"
        );
        return repo.save(policy);
    }
}
