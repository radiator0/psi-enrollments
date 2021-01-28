package com.psi.service;

import com.psi.domain.*;
import com.psi.repository.ClassGroupRepository;
import com.psi.repository.ClassScheduleRepository;
import com.psi.repository.ClassUnitRepository;
import com.psi.service.dto.RecurringScheduleElementDTO;
import com.psi.service.dto.ScheduleElementDTO;
import com.psi.service.mapper.RecurringScheduleElementMapper;
import com.psi.service.mapper.ScheduleElementMapper;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import net.fortuna.ical4j.util.RandomUidGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service Implementation for managing schedules.
 */
@Service
@Transactional
public class ScheduleService {

    private final Logger log = LoggerFactory.getLogger(ScheduleService.class);

    private final ClassUnitRepository classUnitRepository;
    private final ClassScheduleRepository classScheduleRepository;
    private final ClassGroupRepository classGroupRepository;

    private final UserService userService;

    private final ScheduleElementMapper scheduleElementMapper;
    private final RecurringScheduleElementMapper recurringScheduleElementMapper;

    private final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    private final RandomUidGenerator randomUidGenerator = new RandomUidGenerator();

    public ScheduleService(ClassUnitRepository classUnitRepository, ClassScheduleRepository classScheduleRepository, ClassGroupRepository classGroupRepository, UserService userService,
                           ScheduleElementMapper scheduleElementMapper, RecurringScheduleElementMapper recurringScheduleElementMapper) {
        this.classUnitRepository = classUnitRepository;
        this.classScheduleRepository = classScheduleRepository;
        this.classGroupRepository = classGroupRepository;
        this.userService = userService;
        this.scheduleElementMapper = scheduleElementMapper;
        this.recurringScheduleElementMapper = recurringScheduleElementMapper;

        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setBasename("i18n/messages");
    }

    /**
     * Get all the schedule elements for lecturer and student
     *
     * @return the list of entities by user.
     */
    @Transactional(readOnly = true)
    public List<ScheduleElementDTO> findAllScheduleElementsForUser(User user) {
        log.debug("Request to get all schedule elements for user: {}", user.getLogin());
        return findAllClassUnitsForUser(user)
            .map(scheduleElementMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all recurring schedule elements of groups of last started semester.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RecurringScheduleElementDTO> findAllRecurringScheduleElementsOfLastSemesterForUser(User user) {
        log.debug("Request to get all recurring schedule elements of last started semester for user: {} ", user.getLogin());

        Map<LocalDate, List<ClassSchedule>> groupedSchedules;
        if (userService.isUserStudent(user)) {
            groupedSchedules = findAllRecurringForStudent(userService.getStudentInstance(user));
        } else {
            groupedSchedules = findAllRecurringForLecturer(userService.getLecturerInstance(user));
        }

        if (!groupedSchedules.isEmpty()) {
            Optional<LocalDate> lastSemesterNumber =  groupedSchedules.keySet().stream().max(LocalDate::compareTo);
            if(lastSemesterNumber.isPresent() && lastSemesterNumber.get() != null) {
                return groupedSchedules.get(lastSemesterNumber.get()).stream()
                    .map(recurringScheduleElementMapper::toDto)
                    .collect(Collectors.toCollection(LinkedList::new));
            }
        }
        return new LinkedList<>();
    }

    /**
     * Get all the recurring schedule elements by groupCode.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ScheduleElementDTO> findAllByGroupCode(String groupCode) {
        log.debug("Request to get all ScheduleElementDTO by GroupCode");
        Optional<ClassGroup> classGroup = classGroupRepository.findOneByCode(groupCode);
        if (classGroup.isPresent()) {
            return classGroup.get().getClassUnits().stream()
                .map(scheduleElementMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        } else {
            throw new NotFoundException("This classGroup does not exists.");
        }
    }

    /**
     * Get all the schedule elements for lecturer and student in iCal format
     *
     * @return the list of entities by user in iCal format.
     */
    @Transactional(readOnly = true)
    public byte[] findAllScheduleElementsInICalFormatForUser(User user, String language) throws IOException {
        log.debug("Request to get all schedule elements in iCal format for user: {}", user.getLogin());
        Stream<ClassUnit> classUnits = findAllClassUnitsForUser(user);

        Calendar calendar = createCalendar(classUnits, language);

        CalendarOutputter outputter = new CalendarOutputter();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputter.output(calendar, outputStream);

        return outputStream.toByteArray();
    }

    private Calendar createCalendar(Stream<ClassUnit> classUnits, String language) {
        Calendar calendar = new net.fortuna.ical4j.model.Calendar();
        calendar.getProperties().add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        Stream<VEvent> events = classUnits.map(cu -> createEvent(cu, language));
        events.forEach(e -> calendar.getComponents().add(e));

        return calendar;
    }

    private VEvent createEvent(ClassUnit classUnit, String language) {
        Locale locale = Locale.forLanguageTag(language);
        Course course = classUnit.getClassGroup().getCourse();
        Lecturer lecturer = classUnit.getClassGroup().getMainLecturer();
        String formName = messageSource.getMessage(course.getForm().name(), new Object[0], locale);
        String courseName = course.getShortName() != null ? course.getShortName() : course.getName();
        DateTime start = new DateTime(DateTime.from(classUnit.getStartTime()).getTime());
        DateTime end = new DateTime(DateTime.from(classUnit.getEndTime()).getTime());

        VEvent event = new VEvent(start, end, String.format("%s (%s)", courseName, formName));
        event.getProperties().add(randomUidGenerator.generateUid());
        event.getProperties().add(new Categories(formName));

        if(classUnit.getRoom() != null && classUnit.getRoom().getBuilding() != null) {
            event.getProperties().add(new Location(classUnit.getRoom().getBuilding().getName() + " " + classUnit.getRoom().getNumber()));
        }

        if(lecturer != null) {
            event.getProperties().add(new Contact(lecturer.getInternalUser().getEmail()));
        }

        if(lecturer != null) {
            event.getProperties().add(new Description(lecturer.getTitle() != null ? lecturer.getTitle() + " " : lecturer.getInternalUser().getFirstName() + " "));
        }

        return event;
    }

    private Stream<ClassUnit> findAllClassUnitsForUser(User user) {
        if (userService.isUserStudent(user)) {
            Student student = userService.getStudentInstance(user);
            return classUnitRepository.findAll().stream()
                .filter(unit -> unit.getClassGroup().getEnrollments().stream().anyMatch(e -> e.getStudent().equals(student)));
        } else {
            Lecturer lecturer = userService.getLecturerInstance(user);
            return lecturer.getClassGroups().stream().map(ClassGroup::getClassUnits).flatMap(Collection::stream);
        }
    }

    private Map<LocalDate, List<ClassSchedule>> findAllRecurringForStudent(Student student) {
        return groupAndCollectClassSchedules(classScheduleRepository.findAll().stream().
            filter(s -> s.getClassGroup().getEnrollments().stream().anyMatch(e -> e.getStudent().equals(student))));
    }

    private Map<LocalDate, List<ClassSchedule>> findAllRecurringForLecturer(Lecturer lecturer) {
        List<ClassSchedule> classSchedules = classScheduleRepository.findAllByLecturer(lecturer);
        return groupAndCollectClassSchedules(classSchedules.stream());
    }

    private Map<LocalDate, List<ClassSchedule>> groupAndCollectClassSchedules(Stream<ClassSchedule> classSchedulesStream) {
        return classSchedulesStream.collect(Collectors.groupingBy(s -> s.getClassGroup().getCourse().getEnrollmentDate().getSemester().getStartDate()));
    }
}
