package org.yaroslaavl.webappstarter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yaroslaavl.webappstarter.database.entity.Notification;
import org.yaroslaavl.webappstarter.database.entity.User;
import org.yaroslaavl.webappstarter.service.NotificationService;
import org.yaroslaavl.webappstarter.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    @GetMapping("/notifications")
    public String getNotificationsForCurrentUser(Model model, Principal principal) {
        String username = principal.getName();
        Optional<User> userId = userService.findByUsername(username);
        List<Notification> notifications = notificationService.notificationsForUser(userId);
        model.addAttribute("notifications", notifications);
        notifications.forEach(notification -> notification.setRead(true));

        notificationService.saveAll(notifications);
        return "notifications/notifications";
    }

}
