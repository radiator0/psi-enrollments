package com.psi.service;

import com.psi.domain.EnrollmentDate;
import com.psi.repository.StudentRepository;
import com.psi.service.dto.EnrollmentRightDetailsDTO;
import com.psi.service.mapper.EnrollmentRightDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link EnrollmentDate}.
 */
@Service
@Transactional
public class EnrollmentRightDetailsService {

    private final Logger log = LoggerFactory.getLogger(EnrollmentRightDetailsService.class);

    private final StudentRepository studentRepository;

    private final EnrollmentRightDetailsMapper enrollmentRightDetailsMapper;

    public EnrollmentRightDetailsService(StudentRepository studentRepository, EnrollmentRightDetailsMapper enrollmentRightDetailsMapper) {
        this.studentRepository = studentRepository;
        this.enrollmentRightDetailsMapper = enrollmentRightDetailsMapper;
    }

    /**
     * Get all the enrollmentRights of student.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EnrollmentRightDetailsDTO> findAll(Long studentId) {
        log.debug("Request to get all EnrollmentRights of student");
        return studentRepository.findById(studentId).get()
            .getEnrollmentRights().stream()
            .map(enrollmentRightDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
