package com.psi.service;

import com.psi.domain.*;
import com.psi.repository.ClassGroupRepository;
import com.psi.repository.EnrollmentRepository;
import com.psi.repository.RequestRepository;
import com.psi.service.dto.EnrollmentDTO;
import com.psi.service.mapper.EnrollmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Enrollment}.
 */
@Service
@Transactional
public class EnrollingService {

    private final Logger log = LoggerFactory.getLogger(EnrollingService.class);

    private final EnrollmentRepository enrollmentRepository;
    private final ClassGroupRepository classGroupRepository;
    private final RequestRepository requestRepository;

    private final EnrollmentMapper enrollmentMapper;

    private final UserService userService;

    public EnrollingService(EnrollmentRepository enrollmentRepository, ClassGroupRepository classGroupRepository,
                            RequestRepository requestRepository, EnrollmentMapper enrollmentMapper, UserService userService) {
        this.enrollmentRepository = enrollmentRepository;
        this.classGroupRepository = classGroupRepository;
        this.requestRepository = requestRepository;
        this.enrollmentMapper = enrollmentMapper;
        this.userService = userService;
    }

    /**
     * Save a enrollment.
     *
     * @param groupId group id to enroll student to.
     * @param user student to enroll.
     * @return the persisted entity.
     */
    @Transactional
    public EnrollmentDTO enrollStudent(Long groupId, User user) throws Exception {
        Instant requestDate = Instant.now();

        Student student = userService.getStudentInstance(user);
        log.debug("Request to enroll student : {}", student);
        ClassGroup classGroup = classGroupRepository.getOne(groupId);

        validateTimeAndRights(requestDate, classGroup, student);
        validateGroupNotFull(classGroup);

        Set<Enrollment> collidingEnrollments = getCollidingEnrollments(classGroup, student);
        for(Enrollment e : collidingEnrollments) {
            enrollmentRepository.delete(e);
        }

        requestRepository.deleteAll(classGroup.getRequests().stream()
            .filter(r -> r.getStudent().equals(student) && !r.isIsExamined()).collect(Collectors.toList()));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setClassGroup(classGroup);
        enrollment.setDate(requestDate);
        enrollment.setIsAdministrative(false);

        enrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDto(enrollment);
    }

    private void validateTimeAndRights(Instant requestDate, ClassGroup classGroup, Student student) throws Exception {  // TODO xD
        EnrollmentRight correspondingRight = student.getEnrollmentRights().stream()
            .filter(r -> r.getEnrollmentDate().getCourses().stream().anyMatch(c -> c.getClassGroups().contains(classGroup)))
            .findFirst().orElseThrow(() -> new Exception("Student has no rights to perform this enrollment"));

        if(requestDate.isBefore(correspondingRight.getStartDate())) {
            throw new Exception("Student can not enroll in this moment.");
        }
        if(correspondingRight.getEnrollmentDate().getEnrollmentUnits().stream()
            .noneMatch(u -> u.getStartDate().isBefore(requestDate) && u.getEndDate().isAfter(requestDate))) {
            throw new Exception("Student can not enroll because enrollments are not active.");
        }

        if(classGroup.getCourse().getSpecialties() != null && !classGroup.getCourse().getSpecialties().isEmpty()
            && !classGroup.getCourse().getSpecialties().contains(correspondingRight.getSpecialty())) {
            throw new Exception("Student has no rights to enroll to a course with speciality restrictions.");
        }
    }
    private void validateGroupNotFull(ClassGroup classGroup) throws Exception {  // TODO xD
        if (classGroup.getEnrollments().size() >= classGroup.getPeopleLimit()) {
            throw new Exception("Student can not enroll because group is full.");
        }
    }

    private Set<Enrollment> getCollidingEnrollments(ClassGroup classGroup, Student student)
    {
        Course course = classGroup.getCourse();
        CourseUnit courseUnit = course.getCourseUnit();
        SelectableModule selectableModule = courseUnit != null ? courseUnit.getSelectableModule() : null;

        Set<Enrollment> collidingEnrollments = student.getEnrollments().stream()
            .filter(e -> e.getClassGroup().getCourse().equals(course))
            .collect(Collectors.toSet());

        if(selectableModule != null) {
            collidingEnrollments.addAll(
                student.getEnrollments().stream()
                .filter(e -> e.getClassGroup().getCourse().getCourseUnit() != null &&
                    selectableModule.equals(e.getClassGroup().getCourse().getCourseUnit().getSelectableModule())
                    && !courseUnit.equals(e.getClassGroup().getCourse().getCourseUnit()))
                .collect(Collectors.toSet())
            );
        }

        return collidingEnrollments;
    }
    /**
     * Delete the enrollment by group id.
     *
     * @param groupId group id to enroll student to.
     * @param user student to enroll.
     */
    @Transactional
    public Long disenroll(Long groupId, User user) {
        Student student = userService.getStudentInstance(user);
        log.debug("Request to disenroll student : {}", student);
        ClassGroup classGroup = classGroupRepository.getOne(groupId);
        Optional<Enrollment> enrollment = classGroup.getEnrollments().stream().filter(e -> e.getStudent().equals(student)).findFirst();
        if(!enrollment.isPresent()) {
            // TODO xD throw 400
        }
        Long enrollmentId = enrollment.get().getId();
        enrollmentRepository.deleteById(enrollmentId);
        return enrollmentId;
    }


    private EnrollmentDTO save(EnrollmentDTO enrollmentDTO) {
        log.debug("Request to save Enrollment : {}", enrollmentDTO);
        Enrollment enrollment = enrollmentMapper.toEntity(enrollmentDTO);
        enrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDto(enrollment);
    }

}
