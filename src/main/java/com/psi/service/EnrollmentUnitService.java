package com.psi.service;

import com.psi.domain.EnrollmentUnit;
import com.psi.repository.EnrollmentUnitRepository;
import com.psi.service.dto.EnrollmentUnitDTO;
import com.psi.service.mapper.EnrollmentUnitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EnrollmentUnit}.
 */
@Service
@Transactional
public class EnrollmentUnitService {

    private final Logger log = LoggerFactory.getLogger(EnrollmentUnitService.class);

    private final EnrollmentUnitRepository enrollmentUnitRepository;

    private final EnrollmentUnitMapper enrollmentUnitMapper;

    public EnrollmentUnitService(EnrollmentUnitRepository enrollmentUnitRepository, EnrollmentUnitMapper enrollmentUnitMapper) {
        this.enrollmentUnitRepository = enrollmentUnitRepository;
        this.enrollmentUnitMapper = enrollmentUnitMapper;
    }

    /**
     * Save a enrollmentUnit.
     *
     * @param enrollmentUnitDTO the entity to save.
     * @return the persisted entity.
     */
    public EnrollmentUnitDTO save(EnrollmentUnitDTO enrollmentUnitDTO) {
        log.debug("Request to save EnrollmentUnit : {}", enrollmentUnitDTO);
        EnrollmentUnit enrollmentUnit = enrollmentUnitMapper.toEntity(enrollmentUnitDTO);
        enrollmentUnit = enrollmentUnitRepository.save(enrollmentUnit);
        return enrollmentUnitMapper.toDto(enrollmentUnit);
    }

    /**
     * Get all the enrollmentUnits.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EnrollmentUnitDTO> findAll() {
        log.debug("Request to get all EnrollmentUnits");
        return enrollmentUnitRepository.findAll().stream()
            .map(enrollmentUnitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one enrollmentUnit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnrollmentUnitDTO> findOne(Long id) {
        log.debug("Request to get EnrollmentUnit : {}", id);
        return enrollmentUnitRepository.findById(id)
            .map(enrollmentUnitMapper::toDto);
    }

    /**
     * Delete the enrollmentUnit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EnrollmentUnit : {}", id);
        enrollmentUnitRepository.deleteById(id);
    }
}
