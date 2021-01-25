package com.psi.web.rest;

import com.psi.domain.User;
import com.psi.service.EnrollingService;
import com.psi.service.UserService;
import com.psi.service.dto.EnrollmentDTO;
import com.psi.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
    @PostMapping("/enrolling")
    public ResponseEntity<EnrollmentDTO> createEnrollment(@RequestBody Long groupId) throws URISyntaxException {
        log.debug("REST request to enroll student to group : {}", groupId);
        if (groupId == null) {
            // TODO xD throw 400
        }
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new EnrollingResource.EnrollingResourceException("User cannot be found"));

        EnrollmentDTO result = enrollingService.enrollStudent(groupId, user);
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
    @DeleteMapping("/enrolling")
    public ResponseEntity<Void> deleteEnrollment(@RequestBody Long groupId) {
        log.debug("REST request to disenroll student from group : {}", groupId);
        if (groupId == null) {
            // TODO xD throw 400
        }
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new EnrollingResource.EnrollingResourceException("User cannot be found"));

        Long enrollmentId = enrollingService.disenroll(groupId, user);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(
            applicationName, true, ENTITY_NAME, enrollmentId.toString()
        )).build();
    }

//    /**
//     * {@code PUT  /enrollments} : Updates an existing enrollment.
//     *
//     * @param enrollmentDTO the enrollmentDTO to update.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enrollmentDTO,
//     * or with status {@code 400 (Bad Request)} if the enrollmentDTO is not valid,
//     * or with status {@code 500 (Internal Server Error)} if the enrollmentDTO couldn't be updated.
//     * @throws URISyntaxException if the Location URI syntax is incorrect.
//     */
//    @PutMapping("/enrollments")
//    public ResponseEntity<EnrollmentDTO> updateEnrollment(@Valid @RequestBody EnrollmentDTO enrollmentDTO) throws URISyntaxException {
//        log.debug("REST request to update Enrollment : {}", enrollmentDTO);
//        if (enrollmentDTO.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        EnrollmentDTO result = enrollmentService.save(enrollmentDTO);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enrollmentDTO.getId().toString()))
//            .body(result);
//    }
//
//    /**
//     * {@code GET  /enrollments} : get all the enrollments.
//     *
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enrollments in body.
//     */
//    @GetMapping("/enrollments")
//    public List<EnrollmentDTO> getAllEnrollments() {
//        log.debug("REST request to get all Enrollments");
//        return enrollmentService.findAll();
//    }
//
//    /**
//     * {@code GET  /enrollments/:id} : get the "id" enrollment.
//     *
//     * @param id the id of the enrollmentDTO to retrieve.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enrollmentDTO, or with status {@code 404 (Not Found)}.
//     */
//    @GetMapping("/enrollments/{id}")
//    public ResponseEntity<EnrollmentDTO> getEnrollment(@PathVariable Long id) {
//        log.debug("REST request to get Enrollment : {}", id);
//        Optional<EnrollmentDTO> enrollmentDTO = enrollmentService.findOne(id);
//        return ResponseUtil.wrapOrNotFound(enrollmentDTO);
//    }
//
//    /**
//     * {@code DELETE  /enrollments/:id} : delete the "id" enrollment.
//     *
//     * @param id the id of the enrollmentDTO to delete.
//     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//     */
//    @DeleteMapping("/enrollments/{id}")
//    public ResponseEntity<Void> deleteEnrollment(@PathVariable Long id) {
//        log.debug("REST request to delete Enrollment : {}", id);
//        enrollmentService.delete(id);
//        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
//    }
}
