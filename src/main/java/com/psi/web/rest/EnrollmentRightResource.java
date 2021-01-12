package com.psi.web.rest;

import com.psi.service.EnrollmentRightService;
import com.psi.web.rest.errors.BadRequestAlertException;
import com.psi.service.dto.EnrollmentRightDTO;

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
 * REST controller for managing {@link com.psi.domain.EnrollmentRight}.
 */
@RestController
@RequestMapping("/api")
public class EnrollmentRightResource {

    private final Logger log = LoggerFactory.getLogger(EnrollmentRightResource.class);

    private static final String ENTITY_NAME = "enrollmentRight";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnrollmentRightService enrollmentRightService;

    public EnrollmentRightResource(EnrollmentRightService enrollmentRightService) {
        this.enrollmentRightService = enrollmentRightService;
    }

    /**
     * {@code POST  /enrollment-rights} : Create a new enrollmentRight.
     *
     * @param enrollmentRightDTO the enrollmentRightDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enrollmentRightDTO, or with status {@code 400 (Bad Request)} if the enrollmentRight has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enrollment-rights")
    public ResponseEntity<EnrollmentRightDTO> createEnrollmentRight(@Valid @RequestBody EnrollmentRightDTO enrollmentRightDTO) throws URISyntaxException {
        log.debug("REST request to save EnrollmentRight : {}", enrollmentRightDTO);
        if (enrollmentRightDTO.getId() != null) {
            throw new BadRequestAlertException("A new enrollmentRight cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnrollmentRightDTO result = enrollmentRightService.save(enrollmentRightDTO);
        return ResponseEntity.created(new URI("/api/enrollment-rights/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enrollment-rights} : Updates an existing enrollmentRight.
     *
     * @param enrollmentRightDTO the enrollmentRightDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enrollmentRightDTO,
     * or with status {@code 400 (Bad Request)} if the enrollmentRightDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enrollmentRightDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enrollment-rights")
    public ResponseEntity<EnrollmentRightDTO> updateEnrollmentRight(@Valid @RequestBody EnrollmentRightDTO enrollmentRightDTO) throws URISyntaxException {
        log.debug("REST request to update EnrollmentRight : {}", enrollmentRightDTO);
        if (enrollmentRightDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnrollmentRightDTO result = enrollmentRightService.save(enrollmentRightDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enrollmentRightDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /enrollment-rights} : get all the enrollmentRights.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enrollmentRights in body.
     */
    @GetMapping("/enrollment-rights")
    public List<EnrollmentRightDTO> getAllEnrollmentRights() {
        log.debug("REST request to get all EnrollmentRights");
        return enrollmentRightService.findAll();
    }

    /**
     * {@code GET  /enrollment-rights/:id} : get the "id" enrollmentRight.
     *
     * @param id the id of the enrollmentRightDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enrollmentRightDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enrollment-rights/{id}")
    public ResponseEntity<EnrollmentRightDTO> getEnrollmentRight(@PathVariable Long id) {
        log.debug("REST request to get EnrollmentRight : {}", id);
        Optional<EnrollmentRightDTO> enrollmentRightDTO = enrollmentRightService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enrollmentRightDTO);
    }

    /**
     * {@code DELETE  /enrollment-rights/:id} : delete the "id" enrollmentRight.
     *
     * @param id the id of the enrollmentRightDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enrollment-rights/{id}")
    public ResponseEntity<Void> deleteEnrollmentRight(@PathVariable Long id) {
        log.debug("REST request to delete EnrollmentRight : {}", id);
        enrollmentRightService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
