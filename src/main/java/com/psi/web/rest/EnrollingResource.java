package com.psi.web.rest;

import com.psi.domain.User;
import com.psi.security.AuthoritiesConstants;
import com.psi.service.EnrollingService;
import com.psi.service.UserService;
import com.psi.service.dto.EnrollmentDTO;
import com.psi.service.dto.IdDTO;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for managing {@link com.psi.domain.Enrollment}.
 */
@RestController
@RequestMapping("/api")
public class EnrollingResource {

    private static class EnrollingResourceException extends RuntimeException {
        private EnrollingResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(EnrollingResource.class);

    private static final String ENTITY_NAME = "enrollment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserService userService;
    private final EnrollingService enrollingService;

    public EnrollingResource(UserService userService, EnrollingService enrollingService) {
        this.userService = userService;
        this.enrollingService = enrollingService;
    }

    /**
     * {@code POST  /enrolling} : Enroll student in course.
     *
     * @param groupId group to enroll student to.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enrollmentDTO, or with status {@code 400 (Bad Request)} if the enrollment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.STUDENT + "\")")
    @PostMapping("/enrolling")
    public ResponseEntity<EnrollmentDTO> createEnrollment(@Valid @RequestBody IdDTO groupId) throws Exception, URISyntaxException {
        log.debug("REST request to enroll student to group : {}", groupId);
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new EnrollingResource.EnrollingResourceException("User cannot be found"));

        EnrollmentDTO result = enrollingService.enrollStudent(groupId.getId(), user);
        return ResponseEntity.created(new URI("/api/enrollments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code DELETE  /enrolling/:id} : delete the enrollment to "groupId".
     *
     * @param groupId the id of the group to disenroll the student.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.STUDENT + "\")")
    @DeleteMapping("/enrolling")
    public ResponseEntity<Void> deleteEnrollment(@Valid @RequestBody IdDTO groupId) {
        log.debug("REST request to disenroll student from group : {}", groupId);
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new EnrollingResource.EnrollingResourceException("User cannot be found"));

        Long enrollmentId = enrollingService.disenroll(groupId.getId(), user);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(
            applicationName, true, ENTITY_NAME, enrollmentId.toString()
        )).build();
    }
}
