package org.yaroslaavl.webappstarter.http.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yaroslaavl.webappstarter.database.entity.*;
import org.yaroslaavl.webappstarter.database.repository.MedicalRecordRepository;
import org.yaroslaavl.webappstarter.dto.*;
import org.yaroslaavl.webappstarter.service.BookingService;
import org.yaroslaavl.webappstarter.service.PetService;
import org.yaroslaavl.webappstarter.service.SpeciesService;
import org.yaroslaavl.webappstarter.service.UserService;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "Admin Controller")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final BookingService bookingService;
    private final PetService petService;
    private final SpeciesService speciesService;
    private final MedicalRecordRepository medicalRecordRepository;
    private final UserService userService;

    @Operation(summary = "Admin panel")
    @GetMapping("/admin-panel")
    public String adminPanel(){
        return "admin/adminPanel";
    }

    @Operation(summary = "Find all bookings by app users")
    @GetMapping("/listOfBookings")
    public String findAllBookings(@RequestParam(name = "bookingStatus",required = false) BookingStatus bookingStatus, Model model){
        var allBookings = bookingService.findAllBookings(bookingStatus);
        model.addAttribute("bookings",allBookings);
        model.addAttribute("statusFilter",BookingStatus.values());
        model.addAttribute("selectedFilter",bookingStatus);
        return "admin/listOfBookings";
    }

    @Operation(summary = "Booking details specific user")
    @GetMapping("/listOfBookingDetails/{id}")
    public String showBookingDetails(@PathVariable("id") Long id, Model model) {
        var booking = bookingService.findBookingById(id);
        model.addAttribute("booking", booking);
        return "admin/listOfBookingDetails";
    }

    @Operation(summary = "Update user status of booking")
    @PostMapping("/listOfBookingDetails/{id}")
    public String updateBookingStatus(@PathVariable("id") Long id, @ModelAttribute BookingCreateEditDto bookingCreateEditDto) {
        bookingService.updateStatus(id, bookingCreateEditDto);

        return "redirect:/admin/listOfBookings";
    }

    @Operation(summary = "Find all pets")
    @GetMapping("/pets-info")
    public String findAllPets(Model model, PetFilter petFilter, @PageableDefault(size = 4) Pageable pageable){
        log.info("Filtering pets with: {}", petFilter);
        var page = petService.findAll(petFilter,pageable);
        model.addAttribute("pets",page);
        model.addAttribute("filter",petFilter);
        model.addAttribute("species",speciesService.findAll());

        return "admin/pets-info";
    }

    @Operation(summary = "Pet details")
    @GetMapping("/update-pet/{id}")
    public String showPetDetails(@PathVariable("id") Long id, Model model) {
        var pet = petService.findPetById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found with id: " + id));
        model.addAttribute("pet", pet);

        return "admin/update-pet";
    }

    @Operation(summary = "Update pet data")
    @PostMapping("/update-pet/{id}")
    public String updatePet(@PathVariable Long id, PetCreateEditDto petCreateEditDto, Model model) {
        var petToUpdate = petService.findPetById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found with id: " + id));
        petToUpdate.setAge(petCreateEditDto.getAge());
        petToUpdate.setIsAvailable(petCreateEditDto.getIsAvailable());
        model.addAttribute("pet", petToUpdate);

        petService.updatePet(id, petCreateEditDto);
        return "redirect:/admin/pets-info";
    }

    @Operation(summary = "Pet medical records")
    @GetMapping("/medical-record-info/{petId}")
    public String medicalInfo(@PathVariable Long petId, Model model) {
        var pet = petService.findPetById(petId);
        if (pet.isPresent()) {
            var petEntity = pet.get();
            var medicalRecords = medicalRecordRepository.findByIdWithPetAndVeterinarianAndClinic(petId);
            model.addAttribute("pet", petEntity);
            model.addAttribute("medicalRecords", medicalRecords);
            return "admin/medical-records";
        } else {
            return "error/404";
        }
    }

    @Operation(summary = "Find all users of app")
    @GetMapping("/user-info")
    public String usersOfApp(Model model){
        model.addAttribute("users", userService.findAll());

        return "admin/user-info";
    }

    @Operation(summary = "User data")
    @GetMapping("/update-user/{id}")
    public String showUserDetails(@PathVariable("id") Long id, Model model) {
        var user = userService.findUserById(id);
        model.addAttribute("user", user);

        return "admin/update-user";
    }

    @Operation(summary = "Update user data")
    @PostMapping("/update-user/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute UserCreateEditDto user, Model model){
        model.addAttribute("user",userService.findByUsername(user.getUsername()));

        return userService.update(id,user)
                .map(it -> "redirect:/admin/user-info")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
