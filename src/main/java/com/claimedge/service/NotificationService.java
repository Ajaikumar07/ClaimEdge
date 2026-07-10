package com.claimedge.service;

import com.claimedge.entity.Notification;
import com.claimedge.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repo;

    public NotificationService(NotificationRepository repo) {
        this.repo = repo;
    }

    public Notification createNotification(Long userId, String message, String category) {
        Notification n = new Notification();
        n.setUserId(userId);
        n.setMessage(message);
        n.setCategory(category);
        n.setStatus("UNREAD");
        n.setCreatedDate(LocalDateTime.now());
        return repo.save(n);
    }

    public List<Notification> getNotificationsByUser(Integer userId) {
        return repo.findByUserId(userId);
    }

    public Notification markAsRead(Long id) {
        Notification n = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found: " + id));
        n.setStatus("READ");
        return repo.save(n);
    }

    public Notification dismiss(Long id) {
        Notification n = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found: " + id));
        n.setStatus("DISMISSED");
        return repo.save(n);
    }
}
