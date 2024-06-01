package org.yaroslaavl.webappstarter.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
@Transactional(readOnly = true)
@Slf4j
public class AdminController {

    private final BookingService bookingService;
    private final PetService petService;
    private final SpeciesService speciesService;
    private final MedicalRecordRepository medicalRecordRepository;
    private final UserService userService;

    @GetMapping("/listOfBookings")
    public String findAllBookings(@RequestParam(name = "bookingStatus",required = false) BookingStatus bookingStatus, Model model){
        var allBookings = bookingService.findAllBookings(bookingStatus);
        model.addAttribute("bookings",allBookings);
        model.addAttribute("statusFilter",BookingStatus.values());
        model.addAttribute("selectedFilter",bookingStatus);
        return "admin/listOfBookings";
    }

    @GetMapping("/listOfBookingDetails/{id}")
    public String showBookingDetails(@PathVariable("id") Long id, Model model) {
        var booking = bookingService.findBookingById(id);
        model.addAttribute("booking", booking);
        return "admin/listOfBookingDetails";
    }

    @Transactional
    @PostMapping("/listOfBookingDetails/{id}")
    public String updateBooking(@PathVariable("id") Long id, @ModelAttribute BookingCreateEditDto bookingCreateEditDto) {
        bookingService.updateStatus(id, bookingCreateEditDto);

        return "redirect:/admin/listOfBookings";
    }

    @GetMapping("/pets-info")
    public String findAllPets(Model model, PetFilter petFilter, @PageableDefault(size = 6) Pageable pageable){
        log.info("Filtering pets with: {}", petFilter);
        var page = petService.findAll(petFilter,pageable);
        model.addAttribute("pets",page);
        model.addAttribute("filter",petFilter);
        model.addAttribute("species",speciesService.findAll());

        return "admin/pets-info";
    }

    @GetMapping("/update-pet/{id}")
    public String showPetDetails(@PathVariable("id") Long id, Model model) {
        var pet = petService.findPetById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found with id: " + id));
        model.addAttribute("pet", pet);

        return "admin/update-pet";
    }

    @Transactional
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

    @GetMapping("/user-info")
    public String usersOfApp(Model model){
        model.addAttribute("users", userService.findAll());

        return "admin/user-info";
    }

    @GetMapping("/update-user/{id}")
    public String showUserDetails(@PathVariable("id") Long id, Model model) {
        var user = userService.findUserById(id);
        model.addAttribute("user", user);

        return "admin/update-user";
    }

    @Transactional
    @PostMapping("/update-user/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute UserCreateEditDto user, Model model){
        model.addAttribute("user",userService.findByUsername(user.getUsername()));

        return userService.update(id,user)
                .map(it -> "redirect:/admin/user-info")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
