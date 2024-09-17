package org.yaroslaavl.webappstarter.http.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yaroslaavl.webappstarter.dto.PetFilter;
import org.yaroslaavl.webappstarter.dto.PetReadDto;
import org.yaroslaavl.webappstarter.service.PetService;
import org.yaroslaavl.webappstarter.service.SpeciesService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pets")
@Tag(name = "Pet Controller")
public class PetController {

    private final PetService petService;
    private final SpeciesService speciesService;

    @Operation(summary = "Find all pets")
    @GetMapping
    public String findAll(Model model, PetFilter petFilter, @PageableDefault(size = 4) Pageable pageable){
        Page<PetReadDto> page = petService.findAll(petFilter,pageable);
        model.addAttribute("pets",page);
        model.addAttribute("filter",petFilter);
        model.addAttribute("species",speciesService.findAll());
        return "pet/pets";
    }
}


