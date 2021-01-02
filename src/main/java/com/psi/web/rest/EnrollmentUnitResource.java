package com.psi.web.rest;

import com.psi.service.EnrollmentUnitService;
import com.psi.web.rest.errors.BadRequestAlertException;
import com.psi.service.dto.EnrollmentUnitDTO;

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
 * REST controller for managing {@link com.psi.domain.EnrollmentUnit}.
 */
@RestController
@RequestMapping("/api")
public class EnrollmentUnitResource {

    private final Logger log = LoggerFactory.getLogger(EnrollmentUnitResource.class);

    private static final String ENTITY_NAME = "enrollmentUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnrollmentUnitService enrollmentUnitService;

    public EnrollmentUnitResource(EnrollmentUnitService enrollmentUnitService) {
        this.enrollmentUnitService = enrollmentUnitService;
    }

    /**
     * {@code POST  /enrollment-units} : Create a new enrollmentUnit.
     *
     * @param enrollmentUnitDTO the enrollmentUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enrollmentUnitDTO, or with status {@code 400 (Bad Request)} if the enrollmentUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enrollment-units")
    public ResponseEntity<EnrollmentUnitDTO> createEnrollmentUnit(@Valid @RequestBody EnrollmentUnitDTO enrollmentUnitDTO) throws URISyntaxException {
        log.debug("REST request to save EnrollmentUnit : {}", enrollmentUnitDTO);
        if (enrollmentUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new enrollmentUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnrollmentUnitDTO result = enrollmentUnitService.save(enrollmentUnitDTO);
        return ResponseEntity.created(new URI("/api/enrollment-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enrollment-units} : Updates an existing enrollmentUnit.
     *
     * @param enrollmentUnitDTO the enrollmentUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enrollmentUnitDTO,
     * or with status {@code 400 (Bad Request)} if the enrollmentUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enrollmentUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enrollment-units")
    public ResponseEntity<EnrollmentUnitDTO> updateEnrollmentUnit(@Valid @RequestBody EnrollmentUnitDTO enrollmentUnitDTO) throws URISyntaxException {
        log.debug("REST request to update EnrollmentUnit : {}", enrollmentUnitDTO);
        if (enrollmentUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnrollmentUnitDTO result = enrollmentUnitService.save(enrollmentUnitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enrollmentUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /enrollment-units} : get all the enrollmentUnits.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enrollmentUnits in body.
     */
    @GetMapping("/enrollment-units")
    public List<EnrollmentUnitDTO> getAllEnrollmentUnits() {
        log.debug("REST request to get all EnrollmentUnits");
        return enrollmentUnitService.findAll();
    }

    /**
     * {@code GET  /enrollment-units/:id} : get the "id" enrollmentUnit.
     *
     * @param id the id of the enrollmentUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enrollmentUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enrollment-units/{id}")
    public ResponseEntity<EnrollmentUnitDTO> getEnrollmentUnit(@PathVariable Long id) {
        log.debug("REST request to get EnrollmentUnit : {}", id);
        Optional<EnrollmentUnitDTO> enrollmentUnitDTO = enrollmentUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enrollmentUnitDTO);
    }

    /**
     * {@code DELETE  /enrollment-units/:id} : delete the "id" enrollmentUnit.
     *
     * @param id the id of the enrollmentUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enrollment-units/{id}")
    public ResponseEntity<Void> deleteEnrollmentUnit(@PathVariable Long id) {
        log.debug("REST request to delete EnrollmentUnit : {}", id);
        enrollmentUnitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
