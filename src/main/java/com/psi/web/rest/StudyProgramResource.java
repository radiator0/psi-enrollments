package com.psi.web.rest;

import com.psi.service.StudyProgramService;
import com.psi.web.rest.errors.BadRequestAlertException;
import com.psi.service.dto.StudyProgramDTO;

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
 * REST controller for managing {@link com.psi.domain.StudyProgram}.
 */
@RestController
@RequestMapping("/api")
public class StudyProgramResource {

    private final Logger log = LoggerFactory.getLogger(StudyProgramResource.class);

    private static final String ENTITY_NAME = "studyProgram";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudyProgramService studyProgramService;

    public StudyProgramResource(StudyProgramService studyProgramService) {
        this.studyProgramService = studyProgramService;
    }

    /**
     * {@code POST  /study-programs} : Create a new studyProgram.
     *
     * @param studyProgramDTO the studyProgramDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studyProgramDTO, or with status {@code 400 (Bad Request)} if the studyProgram has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/study-programs")
    public ResponseEntity<StudyProgramDTO> createStudyProgram(@Valid @RequestBody StudyProgramDTO studyProgramDTO) throws URISyntaxException {
        log.debug("REST request to save StudyProgram : {}", studyProgramDTO);
        if (studyProgramDTO.getId() != null) {
            throw new BadRequestAlertException("A new studyProgram cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudyProgramDTO result = studyProgramService.save(studyProgramDTO);
        return ResponseEntity.created(new URI("/api/study-programs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /study-programs} : Updates an existing studyProgram.
     *
     * @param studyProgramDTO the studyProgramDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studyProgramDTO,
     * or with status {@code 400 (Bad Request)} if the studyProgramDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studyProgramDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/study-programs")
    public ResponseEntity<StudyProgramDTO> updateStudyProgram(@Valid @RequestBody StudyProgramDTO studyProgramDTO) throws URISyntaxException {
        log.debug("REST request to update StudyProgram : {}", studyProgramDTO);
        if (studyProgramDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudyProgramDTO result = studyProgramService.save(studyProgramDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, studyProgramDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /study-programs} : get all the studyPrograms.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of studyPrograms in body.
     */
    @GetMapping("/study-programs")
    public List<StudyProgramDTO> getAllStudyPrograms() {
        log.debug("REST request to get all StudyPrograms");
        return studyProgramService.findAll();
    }

    /**
     * {@code GET  /study-programs/:id} : get the "id" studyProgram.
     *
     * @param id the id of the studyProgramDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the studyProgramDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/study-programs/{id}")
    public ResponseEntity<StudyProgramDTO> getStudyProgram(@PathVariable Long id) {
        log.debug("REST request to get StudyProgram : {}", id);
        Optional<StudyProgramDTO> studyProgramDTO = studyProgramService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studyProgramDTO);
    }

    /**
     * {@code DELETE  /study-programs/:id} : delete the "id" studyProgram.
     *
     * @param id the id of the studyProgramDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/study-programs/{id}")
    public ResponseEntity<Void> deleteStudyProgram(@PathVariable Long id) {
        log.debug("REST request to delete StudyProgram : {}", id);
        studyProgramService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
