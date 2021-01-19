package com.psi.service;

import com.psi.domain.ClassSchedule;
import com.psi.repository.ClassScheduleRepository;
import com.psi.repository.ClassUnitRepository;
import com.psi.service.dto.RecurringScheduleElementDTO;
import com.psi.service.dto.ScheduleElementDTO;
import com.psi.service.mapper.RecurringScheduleElementMapper;
import com.psi.service.mapper.ScheduleElementMapper;
import org.hibernate.cfg.NotYetImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing schedules.
 */
@Service
@Transactional
public class ScheduleService {

    private final Logger log = LoggerFactory.getLogger(ScheduleService.class);

    private final ClassUnitRepository classUnitRepository;
    private final ClassScheduleRepository classScheduleRepository;

    private final ScheduleElementMapper scheduleElementMapper;
    private final RecurringScheduleElementMapper recurringScheduleElementMapper;

    public ScheduleService(ClassUnitRepository classUnitRepository, ClassScheduleRepository classScheduleRepository,
                           ScheduleElementMapper scheduleElementMapper, RecurringScheduleElementMapper recurringScheduleElementMapper) {
        this.classUnitRepository = classUnitRepository;
        this.classScheduleRepository = classScheduleRepository;
        this.scheduleElementMapper = scheduleElementMapper;
        this.recurringScheduleElementMapper = recurringScheduleElementMapper;
    }

    /**
     * Get all the schedule elements of groups where student with id is enrolled.
     *
     * @return the list of entities by student id.
     */
    @Transactional(readOnly = true)
    public List<ScheduleElementDTO> findAllForUser(String userLogin) {
        log.debug("Request to get all schedule elements for user: {}", userLogin);

        Long studentId = Long.parseLong(userLogin);
        return classUnitRepository.findAll().stream()
            .filter(unit -> unit.getClassGroup().getEnrollments().stream().anyMatch(e -> e.getStudent().getId().equals(studentId)))
            .map(scheduleElementMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all recurring schedule elements of groups of last started semester.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RecurringScheduleElementDTO> findAllRecurringScheduleElementsOfLastSemesterForUser(String userLogin) {
        log.debug("Request to get all recurring schedule elements of last started semester for user: {}", userLogin);

        Map<Integer, List<ClassSchedule>> groupedSchedules = findAllRecurringForStudent(userLogin);

        Integer lastSemesterNumber = Collections.max(groupedSchedules.keySet());

        return groupedSchedules.get(lastSemesterNumber).stream()
            .map(recurringScheduleElementMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    private Map<Integer, List<ClassSchedule>> findAllRecurringForStudent(String studentLogin) {
        Long studentId = Long.parseLong(studentLogin);
        return classScheduleRepository.findAll().stream()
            .filter(s -> s.getClassGroup().getEnrollments().stream().anyMatch(e -> e.getStudent().getId().equals(studentId)))
            .collect(Collectors.groupingBy(s -> s.getClassGroup().getCourse().getEnrollmentDate().getSemester().getNumber()));
    }

    private Map<Integer, List<ClassSchedule>> findAllRecurringForLecturer(String lecturerLogin) {
        throw new NotYetImplementedException();
    }
}
