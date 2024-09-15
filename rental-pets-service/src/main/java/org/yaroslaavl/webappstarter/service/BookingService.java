package org.yaroslaavl.webappstarter.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaroslaavl.webappstarter.database.entity.*;
import org.yaroslaavl.webappstarter.database.repository.BookingRepository;
import org.yaroslaavl.webappstarter.database.repository.NotificationRepository;
import org.yaroslaavl.webappstarter.database.repository.PetRepository;
import org.yaroslaavl.webappstarter.dto.BookingCreateEditDto;
import org.yaroslaavl.webappstarter.dto.BookingReadDto;
import org.yaroslaavl.webappstarter.mapper.mapStruct.BookingMapper;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService {

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final NotificationRepository notificationRepository;
    private final PetRepository petRepository;

    @Transactional
    public void save(Booking booking) {
        bookingRepository.save(booking);
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void updateStatus(Long id, BookingCreateEditDto bookingCreateEditDto) {
        bookingRepository.findById(id)
                .map(booking -> {
                    booking.setStatus(bookingCreateEditDto.getStatus());
                    return bookingRepository.saveAndFlush(booking);
                })
                .map(booking -> {
                    String message = "Your order updated to status: " + bookingCreateEditDto.getStatus();
                    User user = userService.findUserById(booking.getUser().getId());
                    Booking updatedBooking = this.findBookingById(booking.getId());

                    if(bookingCreateEditDto.getStatus() == BookingStatus.InProcess){
                       Pet pet = booking.getPet();
                       pet.setIsAvailable(false);
                       petRepository.save(pet);
                    }
                    if (bookingCreateEditDto.getStatus() == BookingStatus.RentalEnded) {
                        Pet pet = booking.getPet();
                        pet.setIsAvailable(true);
                        petRepository.save(pet);
                    }

                    ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Warsaw"));
                    String formattedTime = zonedDateTime.format(
                            DateTimeFormatter.ofLocalizedDateTime(
                                            FormatStyle.SHORT)
                                    .withLocale(new Locale("pl", "PL")));
                    LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

                    Notification notification = Notification.builder()
                            .user(user)
                            .booking(updatedBooking)
                            .message(message)
                            .time(localDateTime)
                            .formattedTime(formattedTime)
                            .read(false)
                            .build();
                    notificationRepository.save(notification);
                    log.info("Message sent to notification Table: {}", message);
                    return notification;
                })
                .orElseThrow(() -> new EntityNotFoundException("Booking not found with id: " + id));
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public List<BookingReadDto> findAllBookings(BookingStatus status) {
        long startTime = System.currentTimeMillis();
        List<Booking> bookings;
        if (status != null) {
            bookings = bookingRepository.findByStatus(status);
        } else {
            bookings = bookingRepository.findAll();
        }
        long endTime = System.currentTimeMillis();
        log.info("Lazy fetch took {} ms",(endTime - startTime));
        return bookings.stream()
                .map(bookingMapper::toDto)
                .collect(Collectors.toList());
    }

    public Booking findBookingById(Long id){
        return bookingRepository.findById(id).orElse(null);
    }


    public List<Booking> findBookingByUser(User user){
        return bookingRepository.findBookingByUser(user);
    }

}
