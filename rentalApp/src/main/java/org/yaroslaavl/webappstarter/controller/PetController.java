package org.yaroslaavl.webappstarter.controller;

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
@RequestMapping("/pets")
@Transactional
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;
    private final SpeciesService speciesService;

    @GetMapping
    public String findAll(Model model, PetFilter petFilter, @PageableDefault(size = 6) Pageable pageable){
        Page<PetReadDto> page = petService.findAll(petFilter,pageable);
        model.addAttribute("pets",page);
        model.addAttribute("filter",petFilter);
        model.addAttribute("species",speciesService.findAll());
        return "pet/pets";
    }
}


