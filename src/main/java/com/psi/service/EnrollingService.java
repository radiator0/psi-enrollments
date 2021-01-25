package com.psi.service;

import com.psi.domain.ClassGroup;
import com.psi.domain.Enrollment;
import com.psi.domain.Student;
import com.psi.domain.User;
import com.psi.repository.ClassGroupRepository;
import com.psi.repository.EnrollmentRepository;
import com.psi.service.dto.EnrollmentDTO;
import com.psi.service.mapper.EnrollmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Enrollment}.
 */
@Service
@Transactional
public class EnrollingService {

    private final Logger log = LoggerFactory.getLogger(EnrollingService.class);

    private final EnrollmentRepository enrollmentRepository;
    private final ClassGroupRepository classGroupRepository;

    private final EnrollmentMapper enrollmentMapper;

    private final UserService userService;

    public EnrollingService(EnrollmentRepository enrollmentRepository, ClassGroupRepository classGroupRepository, EnrollmentMapper enrollmentMapper, UserService userService) {
        this.enrollmentRepository = enrollmentRepository;
        this.classGroupRepository = classGroupRepository;
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
    public EnrollmentDTO enrollStudent(Long groupId, User user) {
        Student student = userService.getStudentInstance(user);
        log.debug("Request to enroll student : {}", student);
        ClassGroup classGroup = classGroupRepository.getOne(groupId);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setClassGroup(classGroup);
        enrollment.setDate(Instant.now());
        enrollment.setIsAdministrative(false);

        enrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toDto(enrollment);
    }

    /**
     * Delete the enrollment by group id.
     *
     * @param groupId group id to enroll student to.
     * @param user student to enroll.
     */
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