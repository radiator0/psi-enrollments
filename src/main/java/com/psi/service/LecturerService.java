package com.psi.service;

import com.psi.domain.Lecturer;
import com.psi.repository.LecturerRepository;
import com.psi.service.dto.LecturerDTO;
import com.psi.service.mapper.LecturerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Lecturer}.
 */
@Service
@Transactional
public class LecturerService {

    private final Logger log = LoggerFactory.getLogger(LecturerService.class);

    private final LecturerRepository lecturerRepository;

    private final LecturerMapper lecturerMapper;

    public LecturerService(LecturerRepository lecturerRepository, LecturerMapper lecturerMapper) {
        this.lecturerRepository = lecturerRepository;
        this.lecturerMapper = lecturerMapper;
    }

    /**
     * Save a lecturer.
     *
     * @param lecturerDTO the entity to save.
     * @return the persisted entity.
     */
    public LecturerDTO save(LecturerDTO lecturerDTO) {
        log.debug("Request to save Lecturer : {}", lecturerDTO);
        Lecturer lecturer = lecturerMapper.toEntity(lecturerDTO);
        lecturer = lecturerRepository.save(lecturer);
        return lecturerMapper.toDto(lecturer);
    }

    /**
     * Get all the lecturers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<LecturerDTO> findAll() {
        log.debug("Request to get all Lecturers");
        return lecturerRepository.findAll().stream()
            .map(lecturerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one lecturer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LecturerDTO> findOne(Long id) {
        log.debug("Request to get Lecturer : {}", id);
        return lecturerRepository.findById(id)
            .map(lecturerMapper::toDto);
    }

    /**
     * Delete the lecturer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Lecturer : {}", id);
        lecturerRepository.deleteById(id);
    }
}
