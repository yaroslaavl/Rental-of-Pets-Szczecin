package org.yaroslaavl.webappstarter.dto;

import lombok.Value;
import org.yaroslaavl.webappstarter.database.entity.BookingStatus;
import org.yaroslaavl.webappstarter.database.entity.Pet;
import org.yaroslaavl.webappstarter.database.entity.User;

import java.time.LocalDate;

@Value
public class BookingReadDto {

    Long id;
    User user;
    Pet pet;
    LocalDate startDate;
    LocalDate endDate;
    BookingStatus status;

}
