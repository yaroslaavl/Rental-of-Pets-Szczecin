package org.yaroslaavl.webappstarter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaroslaavl.webappstarter.database.entity.Notification;
import org.yaroslaavl.webappstarter.database.entity.User;
import org.yaroslaavl.webappstarter.database.repository.NotificationRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Cacheable(value = "notification",  unless = "#result == null")
    public List<Notification> notificationsForUser(Optional<User> userOptional) {
        User user = userOptional.orElseThrow(() -> new RuntimeException("User not found"));
        return notificationRepository.findAllByUser(user);
    }

    @Transactional
    public void saveAll(List<Notification> notifications) {
        notificationRepository.saveAll(notifications);
    }
}
