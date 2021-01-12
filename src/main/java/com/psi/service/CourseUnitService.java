package com.psi.service;

import com.psi.domain.CourseUnit;
import com.psi.repository.CourseUnitRepository;
import com.psi.service.dto.CourseUnitDTO;
import com.psi.service.mapper.CourseUnitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link CourseUnit}.
 */
@Service
@Transactional
public class CourseUnitService {

    private final Logger log = LoggerFactory.getLogger(CourseUnitService.class);

    private final CourseUnitRepository courseUnitRepository;

    private final CourseUnitMapper courseUnitMapper;

    public CourseUnitService(CourseUnitRepository courseUnitRepository, CourseUnitMapper courseUnitMapper) {
        this.courseUnitRepository = courseUnitRepository;
        this.courseUnitMapper = courseUnitMapper;
    }

    /**
     * Save a courseUnit.
     *
     * @param courseUnitDTO the entity to save.
     * @return the persisted entity.
     */
    public CourseUnitDTO save(CourseUnitDTO courseUnitDTO) {
        log.debug("Request to save CourseUnit : {}", courseUnitDTO);
        CourseUnit courseUnit = courseUnitMapper.toEntity(courseUnitDTO);
        courseUnit = courseUnitRepository.save(courseUnit);
        return courseUnitMapper.toDto(courseUnit);
    }

    /**
     * Get all the courseUnits.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<CourseUnitDTO> findAll() {
        log.debug("Request to get all CourseUnits");
        return courseUnitRepository.findAll().stream()
            .map(courseUnitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one courseUnit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CourseUnitDTO> findOne(Long id) {
        log.debug("Request to get CourseUnit : {}", id);
        return courseUnitRepository.findById(id)
            .map(courseUnitMapper::toDto);
    }

    /**
     * Delete the courseUnit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CourseUnit : {}", id);
        courseUnitRepository.deleteById(id);
    }
}
