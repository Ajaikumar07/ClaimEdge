package com.claimedge.service;

import com.claimedge.entity.RiskFactor;
import com.claimedge.entity.UnderwritingApplication;
import com.claimedge.repository.RiskFactorRepository;
import com.claimedge.repository.UnderwritingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UnderwritingService {

    private final RiskFactorRepository riskRepo;
    private final UnderwritingRepository appRepo;

    public UnderwritingService(RiskFactorRepository riskRepo, UnderwritingRepository appRepo) {
        this.riskRepo = riskRepo;
        this.appRepo = appRepo;
    }

    public UnderwritingApplication createApplication(UnderwritingApplication app) {
        return appRepo.save(app);
    }

    public UnderwritingApplication getApplication(Long id) {
        return appRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found: " + id));
    }

    public List<UnderwritingApplication> getAllApplications() {
        return appRepo.findAll();
    }

    public RiskFactor addRiskFactor(RiskFactor factor) {
        return riskRepo.save(factor);
    }

    public List<RiskFactor> getRiskFactors(Long applicationId) {
        return riskRepo.findByApplicationId(applicationId);
    }

    public UnderwritingApplication evaluateRisk(Long applicationId) {
        List<RiskFactor> factors = riskRepo.findByApplicationId(applicationId);

        double totalScore = factors.stream()
                .mapToDouble(RiskFactor::getWeight)
                .sum();

        UnderwritingApplication app = getApplication(applicationId);
        app.setRiskScore(totalScore);

        if (totalScore > 70) {
            app.setDecision(UnderwritingApplication.Decision.DECLINED);
        } else if (totalScore > 40) {
            app.setDecision(UnderwritingApplication.Decision.REFERRED);
        } else {
            app.setDecision(UnderwritingApplication.Decision.APPROVED);
        }

        app.setPremiumRecommended(5000 + (totalScore * 100));
        app.setDecisionDate(LocalDate.now());

        return appRepo.save(app);
    }
}
