package org.yaroslaavl.webappstarter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.yaroslaavl.webappstarter.database.repository.SpeciesRepository;
import org.yaroslaavl.webappstarter.dto.SpeciesReadDto;
import org.yaroslaavl.webappstarter.mapper.SpeciesReadMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpeciesService {

    private final SpeciesRepository speciesRepository;
    private final SpeciesReadMapper speciesReadMapper;

    public List<SpeciesReadDto> findAll() {
        return speciesRepository.findAll().stream()
                .map(speciesReadMapper::map)
                .toList();
    }

}
