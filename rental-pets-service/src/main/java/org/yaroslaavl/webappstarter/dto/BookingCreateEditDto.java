package org.yaroslaavl.webappstarter.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import org.yaroslaavl.webappstarter.database.entity.BookingStatus;
import org.yaroslaavl.webappstarter.database.entity.Pet;
import org.yaroslaavl.webappstarter.database.entity.User;
import org.yaroslaavl.webappstarter.validation.EditForOrderAction;
import org.yaroslaavl.webappstarter.validation.StartDateLimit;

import java.time.LocalDate;
@Value
@FieldNameConstants
public class BookingCreateEditDto {

    Long id;

    User user;

    Pet pet;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @StartDateLimit(groups = {EditForOrderAction.class},message = "{booking.fields.error.start.date.booking.create.edit.dto}")
    LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate endDate;

    BookingStatus status;
}
