package com.psi.web.rest;

import com.psi.service.SemesterService;
import com.psi.web.rest.errors.BadRequestAlertException;
import com.psi.service.dto.SemesterDTO;

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
 * REST controller for managing {@link com.psi.domain.Semester}.
 */
@RestController
@RequestMapping("/api")
public class SemesterResource {

    private final Logger log = LoggerFactory.getLogger(SemesterResource.class);

    private static final String ENTITY_NAME = "semester";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SemesterService semesterService;

    public SemesterResource(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    /**
     * {@code POST  /semesters} : Create a new semester.
     *
     * @param semesterDTO the semesterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new semesterDTO, or with status {@code 400 (Bad Request)} if the semester has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/semesters")
    public ResponseEntity<SemesterDTO> createSemester(@Valid @RequestBody SemesterDTO semesterDTO) throws URISyntaxException {
        log.debug("REST request to save Semester : {}", semesterDTO);
        if (semesterDTO.getId() != null) {
            throw new BadRequestAlertException("A new semester cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SemesterDTO result = semesterService.save(semesterDTO);
        return ResponseEntity.created(new URI("/api/semesters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /semesters} : Updates an existing semester.
     *
     * @param semesterDTO the semesterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated semesterDTO,
     * or with status {@code 400 (Bad Request)} if the semesterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the semesterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/semesters")
    public ResponseEntity<SemesterDTO> updateSemester(@Valid @RequestBody SemesterDTO semesterDTO) throws URISyntaxException {
        log.debug("REST request to update Semester : {}", semesterDTO);
        if (semesterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SemesterDTO result = semesterService.save(semesterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, semesterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /semesters} : get all the semesters.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of semesters in body.
     */
    @GetMapping("/semesters")
    public List<SemesterDTO> getAllSemesters() {
        log.debug("REST request to get all Semesters");
        return semesterService.findAll();
    }

    /**
     * {@code GET  /semesters/:id} : get the "id" semester.
     *
     * @param id the id of the semesterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the semesterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/semesters/{id}")
    public ResponseEntity<SemesterDTO> getSemester(@PathVariable Long id) {
        log.debug("REST request to get Semester : {}", id);
        Optional<SemesterDTO> semesterDTO = semesterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(semesterDTO);
    }

    /**
     * {@code DELETE  /semesters/:id} : delete the "id" semester.
     *
     * @param id the id of the semesterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/semesters/{id}")
    public ResponseEntity<Void> deleteSemester(@PathVariable Long id) {
        log.debug("REST request to delete Semester : {}", id);
        semesterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
