package org.yaroslaavl.webappstarter.mapper.mapStruct;

import org.mapstruct.Mapper;
import org.yaroslaavl.webappstarter.database.entity.Booking;
import org.yaroslaavl.webappstarter.dto.BookingReadDto;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingReadDto toDto(Booking booking);
}
