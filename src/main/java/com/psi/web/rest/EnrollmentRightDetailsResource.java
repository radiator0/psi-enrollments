package com.psi.web.rest;

import com.psi.security.SecurityUtils;
import com.psi.service.EnrollmentRightDetailsService;
import com.psi.service.dto.EnrollmentDateDTO;
import com.psi.service.dto.EnrollmentRightDetailsDTO;
import com.psi.web.rest.errors.BadRequestAlertException;
import com.psi.web.rest.errors.UnauthorizedException;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.psi.domain.EnrollmentRight}.
 */
@RestController
@RequestMapping("/api")
public class EnrollmentRightDetailsResource {

    private final Logger log = LoggerFactory.getLogger(EnrollmentRightDetailsResource.class);

    private static final String ENTITY_NAME = "enrollmentDate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnrollmentRightDetailsService enrollmentRightDetailsService;

    public EnrollmentRightDetailsResource(EnrollmentRightDetailsService enrollmentRightDetailsService) {
        this.enrollmentRightDetailsService = enrollmentRightDetailsService;
    }

    /**
     * {@code GET  /enrollment-right} : Get all enrollment rights of student.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enrollment rights of student in body.
     */
    @GetMapping("/enrollment-right")
    public List<EnrollmentRightDetailsDTO> getAllEnrollmentRights() {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();
        if(userLogin.isPresent()) {
            log.debug("REST request to get EnrollmentDates: {}", userLogin);
            return enrollmentRightDetailsService.findAll(1L);
        }
        else {
            throw new UnauthorizedException();
        }
    }
}
