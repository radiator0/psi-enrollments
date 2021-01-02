package com.psi.service;

import com.psi.domain.Building;
import com.psi.repository.BuildingRepository;
import com.psi.service.dto.BuildingDTO;
import com.psi.service.mapper.BuildingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Building}.
 */
@Service
@Transactional
public class BuildingService {

    private final Logger log = LoggerFactory.getLogger(BuildingService.class);

    private final BuildingRepository buildingRepository;

    private final BuildingMapper buildingMapper;

    public BuildingService(BuildingRepository buildingRepository, BuildingMapper buildingMapper) {
        this.buildingRepository = buildingRepository;
        this.buildingMapper = buildingMapper;
    }

    /**
     * Save a building.
     *
     * @param buildingDTO the entity to save.
     * @return the persisted entity.
     */
    public BuildingDTO save(BuildingDTO buildingDTO) {
        log.debug("Request to save Building : {}", buildingDTO);
        Building building = buildingMapper.toEntity(buildingDTO);
        building = buildingRepository.save(building);
        return buildingMapper.toDto(building);
    }

    /**
     * Get all the buildings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<BuildingDTO> findAll() {
        log.debug("Request to get all Buildings");
        return buildingRepository.findAll().stream()
            .map(buildingMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one building by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BuildingDTO> findOne(Long id) {
        log.debug("Request to get Building : {}", id);
        return buildingRepository.findById(id)
            .map(buildingMapper::toDto);
    }

    /**
     * Delete the building by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Building : {}", id);
        buildingRepository.deleteById(id);
    }
}
