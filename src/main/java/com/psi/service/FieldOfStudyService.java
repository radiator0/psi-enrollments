package com.psi.service;

import com.psi.domain.FieldOfStudy;
import com.psi.repository.FieldOfStudyRepository;
import com.psi.service.dto.FieldOfStudyDTO;
import com.psi.service.mapper.FieldOfStudyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link FieldOfStudy}.
 */
@Service
@Transactional
public class FieldOfStudyService {

    private final Logger log = LoggerFactory.getLogger(FieldOfStudyService.class);

    private final FieldOfStudyRepository fieldOfStudyRepository;

    private final FieldOfStudyMapper fieldOfStudyMapper;

    public FieldOfStudyService(FieldOfStudyRepository fieldOfStudyRepository, FieldOfStudyMapper fieldOfStudyMapper) {
        this.fieldOfStudyRepository = fieldOfStudyRepository;
        this.fieldOfStudyMapper = fieldOfStudyMapper;
    }

    /**
     * Save a fieldOfStudy.
     *
     * @param fieldOfStudyDTO the entity to save.
     * @return the persisted entity.
     */
    public FieldOfStudyDTO save(FieldOfStudyDTO fieldOfStudyDTO) {
        log.debug("Request to save FieldOfStudy : {}", fieldOfStudyDTO);
        FieldOfStudy fieldOfStudy = fieldOfStudyMapper.toEntity(fieldOfStudyDTO);
        fieldOfStudy = fieldOfStudyRepository.save(fieldOfStudy);
        return fieldOfStudyMapper.toDto(fieldOfStudy);
    }

    /**
     * Get all the fieldOfStudies.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FieldOfStudyDTO> findAll() {
        log.debug("Request to get all FieldOfStudies");
        return fieldOfStudyRepository.findAll().stream()
            .map(fieldOfStudyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one fieldOfStudy by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FieldOfStudyDTO> findOne(Long id) {
        log.debug("Request to get FieldOfStudy : {}", id);
        return fieldOfStudyRepository.findById(id)
            .map(fieldOfStudyMapper::toDto);
    }

    /**
     * Delete the fieldOfStudy by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FieldOfStudy : {}", id);
        fieldOfStudyRepository.deleteById(id);
    }
}
