package com.psi.service;

import com.psi.domain.*;
import com.psi.repository.ClassScheduleRepository;
import com.psi.repository.ClassUnitRepository;
import com.psi.repository.EnrollmentRepository;
import com.psi.service.dto.RecurringScheduleElementDTO;
import com.psi.service.dto.ScheduleElementDTO;
import com.psi.service.mapper.RecurringScheduleElementMapper;
import com.psi.service.mapper.ScheduleElementMapper;
import org.hibernate.cfg.NotYetImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
    private final EnrollmentRepository enrollmentRepository;

    private final UserService userService;

    private final ScheduleElementMapper scheduleElementMapper;
    private final RecurringScheduleElementMapper recurringScheduleElementMapper;

    public ScheduleService(ClassUnitRepository classUnitRepository, ClassScheduleRepository classScheduleRepository, UserService userService,
                           ScheduleElementMapper scheduleElementMapper, EnrollmentRepository enrollmentRepository, RecurringScheduleElementMapper recurringScheduleElementMapper) {
        this.classUnitRepository = classUnitRepository;
        this.classScheduleRepository = classScheduleRepository;
        this.userService = userService;
        this.scheduleElementMapper = scheduleElementMapper;
        this.enrollmentRepository = enrollmentRepository;
        this.recurringScheduleElementMapper = recurringScheduleElementMapper;
    }

    /**
     * Get all the schedule elements of groups where student is enrolled.
     *
     * @return the list of entities by user.
     */
    @Transactional(readOnly = true)
    public List<ScheduleElementDTO> findAllForUser(User user) {
        log.debug("Request to get all schedule elements for user: {}", user.getLogin());

        if (userService.isUserStudent(user)) {
            Student student = userService.getStudentInstance(user);
            return classUnitRepository.findAll().stream()
                .filter(unit -> unit.getClassGroup().getEnrollments().stream().anyMatch(e -> e.getStudent().equals(student)))
                .map(scheduleElementMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        } else {
            Lecturer lecturer = userService.getLecturerInstance(user);
            List<ClassUnit> classUnits = new ArrayList<>();
            lecturer.getClassGroups().stream().map(ClassGroup::getClassUnits).forEach(classUnits::addAll);
            return classUnits.stream().map(scheduleElementMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
        }
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
