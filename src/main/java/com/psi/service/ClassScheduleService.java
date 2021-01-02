package com.psi.service;

import com.psi.domain.ClassSchedule;
import com.psi.repository.ClassScheduleRepository;
import com.psi.service.dto.ClassScheduleDTO;
import com.psi.service.mapper.ClassScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ClassSchedule}.
 */
@Service
@Transactional
public class ClassScheduleService {

    private final Logger log = LoggerFactory.getLogger(ClassScheduleService.class);

    private final ClassScheduleRepository classScheduleRepository;

    private final ClassScheduleMapper classScheduleMapper;

    public ClassScheduleService(ClassScheduleRepository classScheduleRepository, ClassScheduleMapper classScheduleMapper) {
        this.classScheduleRepository = classScheduleRepository;
        this.classScheduleMapper = classScheduleMapper;
    }

    /**
     * Save a classSchedule.
     *
     * @param classScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    public ClassScheduleDTO save(ClassScheduleDTO classScheduleDTO) {
        log.debug("Request to save ClassSchedule : {}", classScheduleDTO);
        ClassSchedule classSchedule = classScheduleMapper.toEntity(classScheduleDTO);
        classSchedule = classScheduleRepository.save(classSchedule);
        return classScheduleMapper.toDto(classSchedule);
    }

    /**
     * Get all the classSchedules.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ClassScheduleDTO> findAll() {
        log.debug("Request to get all ClassSchedules");
        return classScheduleRepository.findAll().stream()
            .map(classScheduleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one classSchedule by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClassScheduleDTO> findOne(Long id) {
        log.debug("Request to get ClassSchedule : {}", id);
        return classScheduleRepository.findById(id)
            .map(classScheduleMapper::toDto);
    }

    /**
     * Delete the classSchedule by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ClassSchedule : {}", id);
        classScheduleRepository.deleteById(id);
    }
}
