package org.yaroslaavl.webappstarter.unit.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yaroslaavl.webappstarter.database.entity.Booking;
import org.yaroslaavl.webappstarter.database.entity.BookingStatus;
import org.yaroslaavl.webappstarter.database.entity.Role;
import org.yaroslaavl.webappstarter.database.entity.User;
import org.yaroslaavl.webappstarter.database.repository.BookingRepository;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingRepositoryTest {

    @Mock
    private BookingRepository bookingRepository;

    private User user;

    @BeforeEach
    public void setUp() {

       user = User.builder()
                .username("ylopatkin1@gmail.com")
                .password("123passwordA")
                .firstname("User")
                .lastname("User")
                .role(Role.USER)
                .emailVerified(Boolean.FALSE)
                .build();
    }

    @Test
    public void find_by_status(){
        Booking booking = Booking.builder()
                .status(BookingStatus.AwaitingApproval)
                .build();

        Booking booking2 = Booking.builder()
                .status(BookingStatus.AwaitingApproval)
                .build();

        when(bookingRepository.findByStatus(booking.getStatus())).thenReturn(Arrays.asList(booking,booking2));

        List<Booking> bookings = bookingRepository.findByStatus(BookingStatus.AwaitingApproval);

        assertThat(bookings).isNotNull();
        assertThat(bookings).allMatch(b -> b.getStatus() == BookingStatus.AwaitingApproval);
        assertThat(bookings).hasSize(2);
    }

    @Test
    public void find_by_status_when_list_is_empty() {

        when(bookingRepository.findByStatus(BookingStatus.InProcess)).thenReturn(Collections.emptyList());

        List<Booking> bookings = bookingRepository.findByStatus(BookingStatus.InProcess);

        assertThat(bookings).isEmpty();
    }

    @Test
    public void find_booking_by_user(){

        Booking booking1 = Booking.builder()
                .user(user)
                .status(BookingStatus.AwaitingApproval)
                .build();

        Booking booking2 = Booking.builder()
                .user(user)
                .status(BookingStatus.CANCELED)
                .build();

        Booking booking3 = Booking.builder()
                .user(user)
                .status(BookingStatus.CANCELED)
                .build();

        when(bookingRepository.findBookingByUser(user)).thenReturn(Arrays.asList(booking1,booking2,booking3));

        List<Booking> bookingsByUser = bookingRepository.findBookingByUser(user);

        assertThat(bookingsByUser).hasSize(3);
        assertThat(bookingsByUser).extracting(Booking::getId).containsExactly(2L, 3L, 4L);
        assertThat(bookingsByUser).allMatch(booking -> booking.getUser().getUsername().equals(user.getUsername()));
    }

    @Test
    public void find_by_id(){

        Booking booking = Booking.builder()
                .status(BookingStatus.CANCELED)
                .build();

        when(bookingRepository.findById(booking.getId())).thenReturn(Optional.of(booking));

        Optional<Booking> foundBooking = bookingRepository.findById(booking.getId());

        assertThat(foundBooking).isPresent();
        assertThat(foundBooking.get().getId()).isEqualTo(4L);
        assertThat(foundBooking.get().getStatus()).isEqualTo(BookingStatus.CANCELED);
    }
}
