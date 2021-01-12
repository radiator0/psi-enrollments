package com.psi.web.rest;

import com.psi.service.ClassScheduleService;
import com.psi.web.rest.errors.BadRequestAlertException;
import com.psi.service.dto.ClassScheduleDTO;

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
 * REST controller for managing {@link com.psi.domain.ClassSchedule}.
 */
@RestController
@RequestMapping("/api")
public class ClassScheduleResource {

    private final Logger log = LoggerFactory.getLogger(ClassScheduleResource.class);

    private static final String ENTITY_NAME = "classSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassScheduleService classScheduleService;

    public ClassScheduleResource(ClassScheduleService classScheduleService) {
        this.classScheduleService = classScheduleService;
    }

    /**
     * {@code POST  /class-schedules} : Create a new classSchedule.
     *
     * @param classScheduleDTO the classScheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classScheduleDTO, or with status {@code 400 (Bad Request)} if the classSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/class-schedules")
    public ResponseEntity<ClassScheduleDTO> createClassSchedule(@Valid @RequestBody ClassScheduleDTO classScheduleDTO) throws URISyntaxException {
        log.debug("REST request to save ClassSchedule : {}", classScheduleDTO);
        if (classScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new classSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassScheduleDTO result = classScheduleService.save(classScheduleDTO);
        return ResponseEntity.created(new URI("/api/class-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /class-schedules} : Updates an existing classSchedule.
     *
     * @param classScheduleDTO the classScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the classScheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/class-schedules")
    public ResponseEntity<ClassScheduleDTO> updateClassSchedule(@Valid @RequestBody ClassScheduleDTO classScheduleDTO) throws URISyntaxException {
        log.debug("REST request to update ClassSchedule : {}", classScheduleDTO);
        if (classScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassScheduleDTO result = classScheduleService.save(classScheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classScheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /class-schedules} : get all the classSchedules.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classSchedules in body.
     */
    @GetMapping("/class-schedules")
    public List<ClassScheduleDTO> getAllClassSchedules() {
        log.debug("REST request to get all ClassSchedules");
        return classScheduleService.findAll();
    }

    /**
     * {@code GET  /class-schedules/:id} : get the "id" classSchedule.
     *
     * @param id the id of the classScheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classScheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-schedules/{id}")
    public ResponseEntity<ClassScheduleDTO> getClassSchedule(@PathVariable Long id) {
        log.debug("REST request to get ClassSchedule : {}", id);
        Optional<ClassScheduleDTO> classScheduleDTO = classScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classScheduleDTO);
    }

    /**
     * {@code DELETE  /class-schedules/:id} : delete the "id" classSchedule.
     *
     * @param id the id of the classScheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/class-schedules/{id}")
    public ResponseEntity<Void> deleteClassSchedule(@PathVariable Long id) {
        log.debug("REST request to delete ClassSchedule : {}", id);
        classScheduleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
