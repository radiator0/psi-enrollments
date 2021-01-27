package com.psi.service;

import com.psi.domain.ClassGroup;
import com.psi.domain.EnrollmentDate;
import com.psi.domain.EnrollmentRight;
import com.psi.domain.User;
import com.psi.service.dto.EnrollmentRightDetailsDTO;
import com.psi.service.mapper.EnrollmentRightDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
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

    private final EnrollmentRightDetailsMapper enrollmentRightDetailsMapper;
    private final UserService userService;

    public EnrollmentRightDetailsService(EnrollmentRightDetailsMapper enrollmentRightDetailsMapper, UserService userService) {
        this.enrollmentRightDetailsMapper = enrollmentRightDetailsMapper;
        this.userService = userService;
    }

    /**
     * Get all the enrollmentRights of student.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EnrollmentRightDetailsDTO> findAllForStudent(User user) {
        log.debug("Request to get all EnrollmentRights of student");
        return userService.getStudentInstance(user)
            .getEnrollmentRights().stream()
            .sorted(Comparator.comparing(EnrollmentRight::getId))
            .map(enrollmentRightDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
}
