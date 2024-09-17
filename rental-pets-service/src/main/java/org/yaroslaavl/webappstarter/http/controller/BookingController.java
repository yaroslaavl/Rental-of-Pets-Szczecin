package org.yaroslaavl.webappstarter.http.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.yaroslaavl.webappstarter.database.entity.*;
import org.yaroslaavl.webappstarter.database.repository.NotificationRepository;
import org.yaroslaavl.webappstarter.dto.BookingCreateEditDto;
import org.yaroslaavl.webappstarter.dto.UserCreateEditDto;
import org.yaroslaavl.webappstarter.service.BookingService;
import org.yaroslaavl.webappstarter.service.PetService;
import org.yaroslaavl.webappstarter.service.UserService;
import org.yaroslaavl.webappstarter.validation.ContactNumber;
import org.yaroslaavl.webappstarter.validation.EditForOrderAction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/pet")
@Tag(name = "Booking Controller")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;
    private final PetService petService;
    private final NotificationRepository notificationRepository;

    @Operation(summary = "Booking form")
    @GetMapping("/booking/{petId}")
    public String showBookingForm(Model model, @AuthenticationPrincipal UserDetails userDetails, @PathVariable Long petId, BookingCreateEditDto bookingCreateEditDto) {
        var username = userDetails.getUsername();
        var userOptional = userService.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("userBooking", user);

            Optional<Pet> optionalPet = petService.findPetById(petId);
            Pet pet = optionalPet.orElseThrow(() -> new NoSuchElementException("Pet not found"));
            model.addAttribute("pet", pet);

            model.addAttribute("booking", bookingCreateEditDto);
            return "pet/booking";
        }
        return "error/404";
    }

    @Operation(summary = "Order pet")
    @PostMapping("/booking/{petId}")
    public String makeOrder(@Validated({EditForOrderAction.class}) @ModelAttribute BookingCreateEditDto booking,
                            BindingResult bookingResult,
                            @Validated({EditForOrderAction.class, ContactNumber.class}) @ModelAttribute UserCreateEditDto userBooking,
                            BindingResult userBookingResult,
                            @RequestParam Long petId,
                            @RequestParam("duration") int duration,
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("startDate") LocalDate startDate,
                            Model model) {

        if (bookingResult.hasErrors() || userBookingResult.hasErrors()) {
            model.addAttribute("userBooking", userBooking);
            model.addAttribute("errorsBooking", bookingResult.getAllErrors());
            model.addAttribute("errorsUser", userBookingResult.getAllErrors());
            model.addAttribute("petId", petId);
            model.addAttribute("booking", booking);
            return "pet/booking";
        }

        User optionalUser = userService.findUserById(userBooking.getId());

        Optional<Pet> optionalPet = petService.findPetById(petId);
        Pet petEntity = optionalPet.orElseThrow(() -> new NoSuchElementException("Pet not found"));

        Booking bookingOrders = new Booking();
        bookingOrders.setUser(optionalUser);
        bookingOrders.setPet(petEntity);
        bookingOrders.setStartDate(startDate);
        LocalDate endDate = startDate.plusDays(duration);
        bookingOrders.setEndDate(endDate);
        bookingOrders.setStatus(BookingStatus.AwaitingApproval);

        String message = "Your order status: " + bookingOrders.getStatus();


        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Warsaw"));
        String formattedTime = zonedDateTime.format(
                DateTimeFormatter.ofLocalizedDateTime(
                        FormatStyle.SHORT)
                        .withLocale(new Locale("pl", "PL")));
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

        Notification notification = Notification.builder()
                .user(optionalUser)
                .booking(bookingOrders)
                .message(message)
                .time(localDateTime)
                .formattedTime(formattedTime)
                .read(false)
                .build();

        bookingService.save(bookingOrders);

        notificationRepository.save(notification);
        userService.update(userBooking.getId(), userBooking);

        return "redirect:/pet/bookings";
    }

    @Operation(summary = "Bookings by user")
    @GetMapping("/bookings")
    public String getBookings(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        var username = userDetails.getUsername();
        var userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Booking> bookings = bookingService.findBookingByUser(user);
            model.addAttribute("bookings", bookings);
            return "pet/bookings/bookings";
        } else {
            return "error/404";
        }
    }

}
