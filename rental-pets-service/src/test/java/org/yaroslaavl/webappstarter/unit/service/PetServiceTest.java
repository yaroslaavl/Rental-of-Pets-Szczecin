package org.yaroslaavl.webappstarter.unit.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.yaroslaavl.webappstarter.database.entity.Gender;
import org.yaroslaavl.webappstarter.database.entity.Pet;
import org.yaroslaavl.webappstarter.database.entity.Species;
import org.yaroslaavl.webappstarter.database.repository.PetRepository;
import org.yaroslaavl.webappstarter.dto.PetFilter;
import org.yaroslaavl.webappstarter.dto.PetReadDto;
import org.yaroslaavl.webappstarter.mapper.mapStruct.PetMapper;
import org.yaroslaavl.webappstarter.service.PetService;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetMapper petMapper;

    @InjectMocks
    private PetService petService;

    private Pet pet1;
    private Pet pet2;

    @BeforeEach
    void setUp() {
        pet1 = Pet.builder()
                .species(Species.builder().build())
                .name("Dogger")
                .age(3)
                .gender(Gender.FEMALE)
                .isAvailable(true)
                .build();
        pet2 = Pet.builder()
                .species(Species.builder().build())
                .name("Shopper")
                .age(3)
                .gender(Gender.MALE)
                .isAvailable(false)
                .build();
    }

    @Test
    public void find_all_pets_using_filters() {
        PetFilter petFilter = new PetFilter(Species.builder().name("Dog").build(), 3, Gender.FEMALE, true);
        Pageable pageable = PageRequest.of(0, 10);

        PetReadDto petReadDto1 = new PetReadDto(pet1.getId(), null, null, pet1.getName(), pet1.getAge(), pet1.getGender(),
                null, null, null, pet1.getIsAvailable());

        Page<Pet> petPage = new PageImpl<>(List.of(pet1, pet2));

        when(petRepository.findAll(any(com.querydsl.core.types.Predicate.class), eq(pageable))).thenReturn(petPage);

        when(petMapper.toDto(pet1)).thenReturn(petReadDto1);

        Page<PetReadDto> resultPets = petService.findAll(petFilter, pageable);

        assertThat(resultPets.getContent()).hasSize(2);
        assertThat(resultPets.getContent().get(0)).isEqualTo(petReadDto1);
        log.info(resultPets.getContent().toString());
    }
}