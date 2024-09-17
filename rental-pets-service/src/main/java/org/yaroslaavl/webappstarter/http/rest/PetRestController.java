package org.yaroslaavl.webappstarter.http.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaroslaavl.webappstarter.dto.PetFilter;
import org.yaroslaavl.webappstarter.dto.PetReadDto;
import org.yaroslaavl.webappstarter.service.PetService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pets")
@Tag(name = "Pet Rest Controller")
public class PetRestController {

    private final PetService petService;

    @Operation(summary = "Find all pets")
    @GetMapping
    public Page<PetReadDto> findAllPets(PetFilter petFilter, @PageableDefault(size = 4) Pageable pageable) {
        return petService.findAll(petFilter, pageable);
    }
}
