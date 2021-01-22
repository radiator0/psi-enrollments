package com.psi.web.rest;

import com.psi.domain.User;
import com.psi.service.EnrollmentRightDetailsService;
import com.psi.service.UserService;
import com.psi.service.dto.EnrollmentRightDetailsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link com.psi.domain.EnrollmentRight}.
 */
@RestController
@RequestMapping("/api")
public class EnrollmentRightDetailsResource {

    private static class EnrollmentRightDetailsResourceException extends RuntimeException {
        private EnrollmentRightDetailsResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(EnrollmentRightDetailsResource.class);

    private final EnrollmentRightDetailsService enrollmentRightDetailsService;
    private final UserService userService;

    public EnrollmentRightDetailsResource(EnrollmentRightDetailsService enrollmentRightDetailsService, UserService userService) {
        this.enrollmentRightDetailsService = enrollmentRightDetailsService;
        this.userService = userService;
    }

    /**
     * {@code GET  /enrollment-right} : Get all enrollment rights of student.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enrollment rights of student in body.
     */
    @GetMapping("/enrollment-right")
    public List<EnrollmentRightDetailsDTO> getAllEnrollmentRights() {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new EnrollmentRightDetailsResource.EnrollmentRightDetailsResourceException("User cannot be found"));
        log.debug("REST request to get EnrollmentDates: {}", user);
        return enrollmentRightDetailsService.findAllForStudent(user);
    }
}
