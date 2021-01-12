package com.psi.web.rest;

import com.psi.service.ClassUnitService;
import com.psi.web.rest.errors.BadRequestAlertException;
import com.psi.service.dto.ClassUnitDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
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
 * REST controller for managing {@link com.psi.domain.ClassUnit}.
 */
@RestController
@RequestMapping("/api")
public class ClassUnitResource {

    private final Logger log = LoggerFactory.getLogger(ClassUnitResource.class);

    private static final String ENTITY_NAME = "classUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassUnitService classUnitService;

    public ClassUnitResource(ClassUnitService classUnitService) {
        this.classUnitService = classUnitService;
    }

    /**
     * {@code POST  /class-units} : Create a new classUnit.
     *
     * @param classUnitDTO the classUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classUnitDTO, or with status {@code 400 (Bad Request)} if the classUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/class-units")
    public ResponseEntity<ClassUnitDTO> createClassUnit(@RequestBody ClassUnitDTO classUnitDTO) throws URISyntaxException {
        log.debug("REST request to save ClassUnit : {}", classUnitDTO);
        if (classUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new classUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassUnitDTO result = classUnitService.save(classUnitDTO);
        return ResponseEntity.created(new URI("/api/class-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /class-units} : Updates an existing classUnit.
     *
     * @param classUnitDTO the classUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classUnitDTO,
     * or with status {@code 400 (Bad Request)} if the classUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/class-units")
    public ResponseEntity<ClassUnitDTO> updateClassUnit(@RequestBody ClassUnitDTO classUnitDTO) throws URISyntaxException {
        log.debug("REST request to update ClassUnit : {}", classUnitDTO);
        if (classUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassUnitDTO result = classUnitService.save(classUnitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /class-units} : get all the classUnits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classUnits in body.
     */
    @GetMapping("/class-units")
    public List<ClassUnitDTO> getAllClassUnits() {
        log.debug("REST request to get all ClassUnits");
        return classUnitService.findAll();
    }

    /**
     * {@code GET  /class-units/:id} : get the "id" classUnit.
     *
     * @param id the id of the classUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-units/{id}")
    public ResponseEntity<ClassUnitDTO> getClassUnit(@PathVariable Long id) {
        log.debug("REST request to get ClassUnit : {}", id);
        Optional<ClassUnitDTO> classUnitDTO = classUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classUnitDTO);
    }

    /**
     * {@code DELETE  /class-units/:id} : delete the "id" classUnit.
     *
     * @param id the id of the classUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/class-units/{id}")
    public ResponseEntity<Void> deleteClassUnit(@PathVariable Long id) {
        log.debug("REST request to delete ClassUnit : {}", id);
        classUnitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
