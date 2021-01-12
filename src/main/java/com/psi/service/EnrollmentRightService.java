package com.psi.service;

import com.psi.domain.EnrollmentRight;
import com.psi.repository.EnrollmentRightRepository;
import com.psi.service.dto.EnrollmentRightDTO;
import com.psi.service.mapper.EnrollmentRightMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EnrollmentRight}.
 */
@Service
@Transactional
public class EnrollmentRightService {

    private final Logger log = LoggerFactory.getLogger(EnrollmentRightService.class);

    private final EnrollmentRightRepository enrollmentRightRepository;

    private final EnrollmentRightMapper enrollmentRightMapper;

    public EnrollmentRightService(EnrollmentRightRepository enrollmentRightRepository, EnrollmentRightMapper enrollmentRightMapper) {
        this.enrollmentRightRepository = enrollmentRightRepository;
        this.enrollmentRightMapper = enrollmentRightMapper;
    }

    /**
     * Save a enrollmentRight.
     *
     * @param enrollmentRightDTO the entity to save.
     * @return the persisted entity.
     */
    public EnrollmentRightDTO save(EnrollmentRightDTO enrollmentRightDTO) {
        log.debug("Request to save EnrollmentRight : {}", enrollmentRightDTO);
        EnrollmentRight enrollmentRight = enrollmentRightMapper.toEntity(enrollmentRightDTO);
        enrollmentRight = enrollmentRightRepository.save(enrollmentRight);
        return enrollmentRightMapper.toDto(enrollmentRight);
    }

    /**
     * Get all the enrollmentRights.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EnrollmentRightDTO> findAll() {
        log.debug("Request to get all EnrollmentRights");
        return enrollmentRightRepository.findAll().stream()
            .map(enrollmentRightMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one enrollmentRight by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnrollmentRightDTO> findOne(Long id) {
        log.debug("Request to get EnrollmentRight : {}", id);
        return enrollmentRightRepository.findById(id)
            .map(enrollmentRightMapper::toDto);
    }

    /**
     * Delete the enrollmentRight by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EnrollmentRight : {}", id);
        enrollmentRightRepository.deleteById(id);
    }
}
