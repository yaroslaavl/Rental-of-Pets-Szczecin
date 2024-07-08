package org.yaroslaavl.webappstarter.unit.service;

import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.yaroslaavl.webappstarter.database.entity.Gender;
import org.yaroslaavl.webappstarter.database.entity.Pet;
import org.yaroslaavl.webappstarter.database.entity.Species;
import org.yaroslaavl.webappstarter.database.repository.PetRepository;
import org.yaroslaavl.webappstarter.dto.PetFilter;
import org.yaroslaavl.webappstarter.dto.PetReadDto;
import org.yaroslaavl.webappstarter.mapper.PetCreateEditMapper;
import org.yaroslaavl.webappstarter.mapper.PetReadMapper;
import org.yaroslaavl.webappstarter.service.PetService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetReadMapper petReadMapper;

    @Mock
    private PetCreateEditMapper petCreateEditMapper;

    @InjectMocks
    private PetService petService;

    @Test
    public void find_all_pets_using_filters(){
        PetFilter petFilter = new PetFilter(null, 3, Gender.MALE, true);
        Pageable pageable = Pageable.ofSize(10).withPage(0);

        Page<Pet> mockPage = mock(Page.class);
        when(petRepository.findAll((Predicate) any(), eq(pageable))).thenReturn(mockPage);
        Page<PetReadDto> expectedPage = mock(Page.class);
        when(mockPage.map(petReadMapper::map)).thenReturn(expectedPage);

        Page<PetReadDto> resultPage = petService.findAll(petFilter, pageable);

        verify(petRepository).findAll((Predicate) any(), eq(pageable));
        verify(mockPage).map(petReadMapper::map);

        assertThat(resultPage).isNotNull();

    }

}
