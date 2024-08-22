package org.yaroslaavl.webappstarter.mapper;

import org.springframework.stereotype.Component;
import org.yaroslaavl.webappstarter.database.entity.Notification;
import org.yaroslaavl.webappstarter.dto.NotificationCreateEditDto;

@Component
public class NotificationCreateEditMapper implements Mapper<NotificationCreateEditDto, Notification>{

    @Override
    public Notification map(NotificationCreateEditDto fromObject, Notification toObject){
        copy(fromObject,toObject);
        return toObject;
    }

    @Override
    public Notification map(NotificationCreateEditDto object) {
        Notification notification = new Notification();
        copy(object,notification);
        return notification;
    }

    private void copy(NotificationCreateEditDto object, Notification notification){
        notification.setId(object.getId());
        notification.setUser(object.getUser());
        notification.setBooking(object.getBooking());
        notification.setMessage(object.getMessage());
        notification.setTime(object.getTime());
        notification.setFormattedTime(object.getFormattedTime());
        notification.setRead(object.getRead());
    }
}
