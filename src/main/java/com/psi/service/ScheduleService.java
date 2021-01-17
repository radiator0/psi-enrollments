package com.psi.service;

import com.psi.repository.ClassUnitRepository;
import com.psi.service.dto.ScheduleElementDTO;
import com.psi.service.mapper.ClassUnitMapper;
import com.psi.service.mapper.ScheduleElementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing schedules.
 */
@Service
@Transactional
public class ScheduleService {

    private final Logger log = LoggerFactory.getLogger(ScheduleService.class);

    private final ClassUnitRepository classUnitRepository;

    private final ScheduleElementMapper scheduleElementMapper;

    public ScheduleService(ClassUnitRepository classUnitRepository, ScheduleElementMapper scheduleElementMapper) {
        this.classUnitRepository = classUnitRepository;
        this.scheduleElementMapper = scheduleElementMapper;
    }

    /**
     * Get all the schedule elements of groups where student with id is enrolled.
     *
     * @return the list of entities by student id.
     */
    @Transactional(readOnly = true)
    public List<ScheduleElementDTO> findAllByStudentId(Long id) {
        log.debug("Request to get all ClassUnits by student id : {}", id);
        return classUnitRepository.findAll().stream()
            .filter(unit -> unit.getClassGroup().getEnrollments().stream().anyMatch(e -> e.getStudent().getId().equals(id)))
            .map(scheduleElementMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
