package org.yaroslaavl.webappstarter.http.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaroslaavl.webappstarter.database.entity.Notification;
import org.yaroslaavl.webappstarter.database.entity.User;
import org.yaroslaavl.webappstarter.dto.NotificationReadDto;
import org.yaroslaavl.webappstarter.mapper.NotificationReadMapper;
import org.yaroslaavl.webappstarter.service.NotificationService;
import org.yaroslaavl.webappstarter.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/user")
@Tag(name = "Notification Rest Controller")
public class NotificationRestController {

    private final NotificationService notificationService;
    private final UserService userService;
    private final NotificationReadMapper notificationReadMapper;

    @Operation(summary = "Find all user notification")
    @GetMapping("/notifications")
    public List<NotificationReadDto> getNotificationsForCurrentUser(@AuthenticationPrincipal UserDetails userDetails) throws Exception {
        String username = userDetails.getUsername();
        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Notification> notifications = notificationService.notificationsForUser(Optional.of(user));
            notifications.forEach(notification -> notification.setRead(true));
            notificationService.saveAll(notifications);

            List<NotificationReadDto> notificationDtos = notifications.stream()
                    .map(notificationReadMapper::map)
                    .collect(Collectors.toList());

            return notificationDtos;
        } else {
            throw new Exception("User not found");
        }
    }

}