package com.psi.web.rest;

import com.psi.service.EnrollmentDateService;
import com.psi.web.rest.errors.BadRequestAlertException;
import com.psi.service.dto.EnrollmentDateDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.psi.domain.EnrollmentDate}.
 */
@ApiIgnore
@RestController
@RequestMapping("/api")
public class EnrollmentDateResource {

    private final Logger log = LoggerFactory.getLogger(EnrollmentDateResource.class);

    private static final String ENTITY_NAME = "enrollmentDate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnrollmentDateService enrollmentDateService;

    public EnrollmentDateResource(EnrollmentDateService enrollmentDateService) {
        this.enrollmentDateService = enrollmentDateService;
    }

    /**
     * {@code POST  /enrollment-dates} : Create a new enrollmentDate.
     *
     * @param enrollmentDateDTO the enrollmentDateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enrollmentDateDTO, or with status {@code 400 (Bad Request)} if the enrollmentDate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enrollment-dates")
    public ResponseEntity<EnrollmentDateDTO> createEnrollmentDate(@Valid @RequestBody EnrollmentDateDTO enrollmentDateDTO) throws URISyntaxException {
        log.debug("REST request to save EnrollmentDate : {}", enrollmentDateDTO);
        if (enrollmentDateDTO.getId() != null) {
            throw new BadRequestAlertException("A new enrollmentDate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnrollmentDateDTO result = enrollmentDateService.save(enrollmentDateDTO);
        return ResponseEntity.created(new URI("/api/enrollment-dates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enrollment-dates} : Updates an existing enrollmentDate.
     *
     * @param enrollmentDateDTO the enrollmentDateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enrollmentDateDTO,
     * or with status {@code 400 (Bad Request)} if the enrollmentDateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enrollmentDateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enrollment-dates")
    public ResponseEntity<EnrollmentDateDTO> updateEnrollmentDate(@Valid @RequestBody EnrollmentDateDTO enrollmentDateDTO) throws URISyntaxException {
        log.debug("REST request to update EnrollmentDate : {}", enrollmentDateDTO);
        if (enrollmentDateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnrollmentDateDTO result = enrollmentDateService.save(enrollmentDateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enrollmentDateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /enrollment-dates} : get all the enrollmentDates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enrollmentDates in body.
     */
    @GetMapping("/enrollment-dates")
    public List<EnrollmentDateDTO> getAllEnrollmentDates() {
        log.debug("REST request to get all EnrollmentDates");
        return enrollmentDateService.findAll();
    }

    /**
     * {@code GET  /enrollment-dates/:id} : get the "id" enrollmentDate.
     *
     * @param id the id of the enrollmentDateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enrollmentDateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enrollment-dates/{id}")
    public ResponseEntity<EnrollmentDateDTO> getEnrollmentDate(@PathVariable Long id) {
        log.debug("REST request to get EnrollmentDate : {}", id);
        Optional<EnrollmentDateDTO> enrollmentDateDTO = enrollmentDateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enrollmentDateDTO);
    }

    /**
     * {@code DELETE  /enrollment-dates/:id} : delete the "id" enrollmentDate.
     *
     * @param id the id of the enrollmentDateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enrollment-dates/{id}")
    public ResponseEntity<Void> deleteEnrollmentDate(@PathVariable Long id) {
        log.debug("REST request to delete EnrollmentDate : {}", id);
        enrollmentDateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
