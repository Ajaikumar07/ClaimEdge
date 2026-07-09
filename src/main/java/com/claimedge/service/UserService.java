package com.claimedge.service;

import com.claimedge.dto.LoginRequest;
import com.claimedge.entity.*;
import com.claimedge.repository.*;
import com.claimedge.utilities.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final AuditRepository auditRepo;
    private final BCryptPasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepo, AuditRepository auditRepo,
                       BCryptPasswordEncoder encoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.auditRepo = auditRepo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public User register(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered: " + user.getEmail());
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setTokenVersion(0);
        User saved = userRepo.save(user);
        audit(saved.getUserId(), "REGISTER");
        return saved;
    }

    public String login(LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (user.getStatus() != Status.ACTIVE) {
            throw new RuntimeException("Account is inactive");
        }
        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        user.setTokenVersion(user.getTokenVersion() + 1);
        userRepo.save(user);
        audit(user.getUserId(), "LOGIN");

        return jwtUtil.generateToken(user.getEmail(), user.getRole().name(), user.getTokenVersion());
    }

    public User updateStatus(Integer id, String status) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
        try {
            user.setStatus(Status.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }
        userRepo.save(user);
        audit(id, "STATUS_CHANGE to " + status.toUpperCase());
        return user;
    }

    private void audit(Integer userId, String action) {
        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setAction(action);
        log.setModule("IAM");
        log.setTimestamp(LocalDateTime.now());
        auditRepo.save(log);
    }
}
