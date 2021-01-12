package com.psi.service;

import com.psi.domain.EnrollmentDate;
import com.psi.repository.EnrollmentDateRepository;
import com.psi.service.dto.EnrollmentDateDTO;
import com.psi.service.mapper.EnrollmentDateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EnrollmentDate}.
 */
@Service
@Transactional
public class EnrollmentDateService {

    private final Logger log = LoggerFactory.getLogger(EnrollmentDateService.class);

    private final EnrollmentDateRepository enrollmentDateRepository;

    private final EnrollmentDateMapper enrollmentDateMapper;

    public EnrollmentDateService(EnrollmentDateRepository enrollmentDateRepository, EnrollmentDateMapper enrollmentDateMapper) {
        this.enrollmentDateRepository = enrollmentDateRepository;
        this.enrollmentDateMapper = enrollmentDateMapper;
    }

    /**
     * Save a enrollmentDate.
     *
     * @param enrollmentDateDTO the entity to save.
     * @return the persisted entity.
     */
    public EnrollmentDateDTO save(EnrollmentDateDTO enrollmentDateDTO) {
        log.debug("Request to save EnrollmentDate : {}", enrollmentDateDTO);
        EnrollmentDate enrollmentDate = enrollmentDateMapper.toEntity(enrollmentDateDTO);
        enrollmentDate = enrollmentDateRepository.save(enrollmentDate);
        return enrollmentDateMapper.toDto(enrollmentDate);
    }

    /**
     * Get all the enrollmentDates.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EnrollmentDateDTO> findAll() {
        log.debug("Request to get all EnrollmentDates");
        return enrollmentDateRepository.findAll().stream()
            .map(enrollmentDateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one enrollmentDate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EnrollmentDateDTO> findOne(Long id) {
        log.debug("Request to get EnrollmentDate : {}", id);
        return enrollmentDateRepository.findById(id)
            .map(enrollmentDateMapper::toDto);
    }

    /**
     * Delete the enrollmentDate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EnrollmentDate : {}", id);
        enrollmentDateRepository.deleteById(id);
    }
}
