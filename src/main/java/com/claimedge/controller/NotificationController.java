package com.claimedge.controller;

import com.claimedge.entity.Notification;
import com.claimedge.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @PostMapping
    public Notification create(@RequestParam Long userId,
                               @RequestParam String message,
                               @RequestParam String category) {
        return service.createNotification(userId, message, category);
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getByUser(@PathVariable Integer userId) {
        return service.getNotificationsByUser(userId);
    }

    @PutMapping("/{id}/read")
    public Notification markRead(@PathVariable Long id) {
        return service.markAsRead(id);
    }

    @PutMapping("/{id}/dismiss")
    public Notification dismiss(@PathVariable Long id) {
        return service.dismiss(id);
    }
}
