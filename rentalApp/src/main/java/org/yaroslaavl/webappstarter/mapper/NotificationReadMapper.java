package org.yaroslaavl.webappstarter.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.yaroslaavl.webappstarter.database.entity.Notification;
import org.yaroslaavl.webappstarter.dto.NotificationReadDto;

@Component
@RequiredArgsConstructor
public class NotificationReadMapper implements Mapper<Notification, NotificationReadDto>{

    @Override
    public NotificationReadDto map(Notification object) {
        return new NotificationReadDto(
            object.getId(),
            object.getUser(),
            object.getBooking(),
            object.getMessage(),
            object.getTime(),
            object.getFormattedTime(),
            object.isRead()
        );
    }
}
