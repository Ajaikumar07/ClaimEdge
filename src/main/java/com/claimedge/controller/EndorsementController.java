package com.claimedge.controller;

import com.claimedge.entity.Endorsement;
import com.claimedge.service.EndorsementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/endorsements")
public class EndorsementController {

    private final EndorsementService service;

    public EndorsementController(EndorsementService service) {
        this.service = service;
    }

    @PostMapping
    public Endorsement create(@RequestBody Endorsement endorsement) {
        return service.createEndorsement(endorsement);
    }

    @GetMapping
    public List<Endorsement> getAll() {
        return service.getAllEndorsements();
    }

    @GetMapping("/{id}")
    public Endorsement get(@PathVariable Long id) {
        return service.getEndorsement(id);
    }

    @GetMapping("/policy/{policyId}")
    public List<Endorsement> getByPolicy(@PathVariable Long policyId) {
        return service.getEndorsementsByPolicyId(policyId);
    }

    @PutMapping("/{id}")
    public Endorsement update(@PathVariable Long id, @RequestBody Endorsement endorsement) {
        return service.updateEndorsement(id, endorsement);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteEndorsement(id);
        return "Endorsement deleted successfully";
    }
}
