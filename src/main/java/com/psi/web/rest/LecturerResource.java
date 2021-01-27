package com.psi.web.rest;

import com.psi.service.LecturerService;
import com.psi.web.rest.errors.BadRequestAlertException;
import com.psi.service.dto.LecturerDTO;

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
 * REST controller for managing {@link com.psi.domain.Lecturer}.
 */
@ApiIgnore
@RestController
@RequestMapping("/api")
public class LecturerResource {

    private final Logger log = LoggerFactory.getLogger(LecturerResource.class);

    private static final String ENTITY_NAME = "lecturer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LecturerService lecturerService;

    public LecturerResource(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    /**
     * {@code POST  /lecturers} : Create a new lecturer.
     *
     * @param lecturerDTO the lecturerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lecturerDTO, or with status {@code 400 (Bad Request)} if the lecturer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lecturers")
    public ResponseEntity<LecturerDTO> createLecturer(@Valid @RequestBody LecturerDTO lecturerDTO) throws URISyntaxException {
        log.debug("REST request to save Lecturer : {}", lecturerDTO);
        if (lecturerDTO.getId() != null) {
            throw new BadRequestAlertException("A new lecturer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LecturerDTO result = lecturerService.save(lecturerDTO);
        return ResponseEntity.created(new URI("/api/lecturers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lecturers} : Updates an existing lecturer.
     *
     * @param lecturerDTO the lecturerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lecturerDTO,
     * or with status {@code 400 (Bad Request)} if the lecturerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lecturerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lecturers")
    public ResponseEntity<LecturerDTO> updateLecturer(@Valid @RequestBody LecturerDTO lecturerDTO) throws URISyntaxException {
        log.debug("REST request to update Lecturer : {}", lecturerDTO);
        if (lecturerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LecturerDTO result = lecturerService.save(lecturerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lecturerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lecturers} : get all the lecturers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lecturers in body.
     */
    @GetMapping("/lecturers")
    public List<LecturerDTO> getAllLecturers() {
        log.debug("REST request to get all Lecturers");
        return lecturerService.findAll();
    }

    /**
     * {@code GET  /lecturers/:id} : get the "id" lecturer.
     *
     * @param id the id of the lecturerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lecturerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lecturers/{id}")
    public ResponseEntity<LecturerDTO> getLecturer(@PathVariable Long id) {
        log.debug("REST request to get Lecturer : {}", id);
        Optional<LecturerDTO> lecturerDTO = lecturerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lecturerDTO);
    }

    /**
     * {@code DELETE  /lecturers/:id} : delete the "id" lecturer.
     *
     * @param id the id of the lecturerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lecturers/{id}")
    public ResponseEntity<Void> deleteLecturer(@PathVariable Long id) {
        log.debug("REST request to delete Lecturer : {}", id);
        lecturerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
