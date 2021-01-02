package com.psi.service;

import com.psi.domain.ClassUnit;
import com.psi.repository.ClassUnitRepository;
import com.psi.service.dto.ClassUnitDTO;
import com.psi.service.mapper.ClassUnitMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ClassUnit}.
 */
@Service
@Transactional
public class ClassUnitService {

    private final Logger log = LoggerFactory.getLogger(ClassUnitService.class);

    private final ClassUnitRepository classUnitRepository;

    private final ClassUnitMapper classUnitMapper;

    public ClassUnitService(ClassUnitRepository classUnitRepository, ClassUnitMapper classUnitMapper) {
        this.classUnitRepository = classUnitRepository;
        this.classUnitMapper = classUnitMapper;
    }

    /**
     * Save a classUnit.
     *
     * @param classUnitDTO the entity to save.
     * @return the persisted entity.
     */
    public ClassUnitDTO save(ClassUnitDTO classUnitDTO) {
        log.debug("Request to save ClassUnit : {}", classUnitDTO);
        ClassUnit classUnit = classUnitMapper.toEntity(classUnitDTO);
        classUnit = classUnitRepository.save(classUnit);
        return classUnitMapper.toDto(classUnit);
    }

    /**
     * Get all the classUnits.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ClassUnitDTO> findAll() {
        log.debug("Request to get all ClassUnits");
        return classUnitRepository.findAll().stream()
            .map(classUnitMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one classUnit by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClassUnitDTO> findOne(Long id) {
        log.debug("Request to get ClassUnit : {}", id);
        return classUnitRepository.findById(id)
            .map(classUnitMapper::toDto);
    }

    /**
     * Delete the classUnit by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClassUnit : {}", id);
        classUnitRepository.deleteById(id);
    }
}
