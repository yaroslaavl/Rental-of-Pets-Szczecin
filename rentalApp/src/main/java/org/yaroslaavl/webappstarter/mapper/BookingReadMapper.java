package org.yaroslaavl.webappstarter.mapper;

import org.springframework.stereotype.Component;
import org.yaroslaavl.webappstarter.database.entity.Booking;
import org.yaroslaavl.webappstarter.dto.BookingCreateEditDto;
import org.yaroslaavl.webappstarter.dto.BookingReadDto;

@Component
public class BookingReadMapper implements Mapper<Booking, BookingReadDto>{
    @Override
    public BookingReadDto map(Booking object) {
        return new BookingReadDto(
                object.getId(),
                object.getUser(),
                object.getPet(),
                object.getStartDate(),
                object.getEndDate(),
                object.getStatus()
        );
    }
    public Booking mapStatus(BookingCreateEditDto bookingCreateEditDto) {
        Booking booking = new Booking();
        booking.setStatus(bookingCreateEditDto.getStatus());
        return booking;
    }
}
