package org.yaroslaavl.webappstarter.mapper;

import org.springframework.stereotype.Component;
import org.yaroslaavl.webappstarter.database.entity.Booking;
import org.yaroslaavl.webappstarter.dto.BookingCreateEditDto;

@Component
public class BookingCreateEditMapper implements Mapper<BookingCreateEditDto, Booking>{

    @Override
    public Booking map(BookingCreateEditDto fromObject, Booking toObject) {
        copy(fromObject,toObject);
        return toObject;
    }

    @Override
    public Booking map(BookingCreateEditDto object) {
        Booking booking = new Booking();
        copy(object,booking);
        return booking;
    }

    private void copy(BookingCreateEditDto object, Booking booking) {
        booking.setId(object.getId());
        booking.setUser(object.getUser());
        booking.setPet(object.getPet());
        booking.setStartDate(object.getStartDate());
        booking.setEndDate(object.getEndDate());
        booking.setStatus(object.getStatus());

    }
}
