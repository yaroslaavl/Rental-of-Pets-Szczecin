package org.yaroslaavl.webappstarter.dto;


import lombok.Value;
import org.yaroslaavl.webappstarter.database.entity.Booking;
import org.yaroslaavl.webappstarter.database.entity.User;

import java.time.LocalDateTime;

@Value
public class NotificationReadDto {

    Long id;
    User user;
    Booking booking;
    String message;
    LocalDateTime time;
    String formattedTime;
    Boolean read;

}
