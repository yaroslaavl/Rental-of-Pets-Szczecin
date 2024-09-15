package org.yaroslaavl.webappstarter.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaroslaavl.webappstarter.database.entity.Pet;
import org.yaroslaavl.webappstarter.database.querydsl.QPredicate;
import org.yaroslaavl.webappstarter.database.repository.PetRepository;
import org.yaroslaavl.webappstarter.dto.PetCreateEditDto;
import org.yaroslaavl.webappstarter.dto.PetFilter;
import org.yaroslaavl.webappstarter.dto.PetReadDto;
import org.yaroslaavl.webappstarter.mapper.PetCreateEditMapper;
import org.yaroslaavl.webappstarter.mapper.mapStruct.PetMapper;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.yaroslaavl.webappstarter.database.entity.QPet.pet;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class PetService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;

    public Page<PetReadDto> findAll(PetFilter petFilter, Pageable pageable){
        var predicate = QPredicate.builder()
                .add(petFilter.species(),pet.species::eq)
                .add(petFilter.age(),pet.age::eq)
                .add(petFilter.gender(),pet.gender::eq)
                .add(petFilter.isAvailable(),pet.isAvailable::eq)
                .buildAnd();
        log.info("Filters: {} ",petFilter);
        return petRepository.findAll(predicate,pageable)
                .map(petMapper::toDto);
    }

    public Optional<Pet> findPetById(Long petId) {
        return petRepository.findById(petId);
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void updatePet(Long id, PetCreateEditDto petCreateEditDto) {
        Pet petToUpdate = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet not found with id: " + id));
        petMapper.toEntity(petCreateEditDto);

        petRepository.saveAndFlush(petToUpdate);
    }

}

