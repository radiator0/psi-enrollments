package com.psi.web.rest;

import com.psi.service.CourseUnitService;
import com.psi.web.rest.errors.BadRequestAlertException;
import com.psi.service.dto.CourseUnitDTO;

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
 * REST controller for managing {@link com.psi.domain.CourseUnit}.
 */
@RestController
@RequestMapping("/api")
public class CourseUnitResource {

    private final Logger log = LoggerFactory.getLogger(CourseUnitResource.class);

    private static final String ENTITY_NAME = "courseUnit";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CourseUnitService courseUnitService;

    public CourseUnitResource(CourseUnitService courseUnitService) {
        this.courseUnitService = courseUnitService;
    }

    /**
     * {@code POST  /course-units} : Create a new courseUnit.
     *
     * @param courseUnitDTO the courseUnitDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseUnitDTO, or with status {@code 400 (Bad Request)} if the courseUnit has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/course-units")
    public ResponseEntity<CourseUnitDTO> createCourseUnit(@Valid @RequestBody CourseUnitDTO courseUnitDTO) throws URISyntaxException {
        log.debug("REST request to save CourseUnit : {}", courseUnitDTO);
        if (courseUnitDTO.getId() != null) {
            throw new BadRequestAlertException("A new courseUnit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CourseUnitDTO result = courseUnitService.save(courseUnitDTO);
        return ResponseEntity.created(new URI("/api/course-units/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /course-units} : Updates an existing courseUnit.
     *
     * @param courseUnitDTO the courseUnitDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseUnitDTO,
     * or with status {@code 400 (Bad Request)} if the courseUnitDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseUnitDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/course-units")
    public ResponseEntity<CourseUnitDTO> updateCourseUnit(@Valid @RequestBody CourseUnitDTO courseUnitDTO) throws URISyntaxException {
        log.debug("REST request to update CourseUnit : {}", courseUnitDTO);
        if (courseUnitDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CourseUnitDTO result = courseUnitService.save(courseUnitDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, courseUnitDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /course-units} : get all the courseUnits.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of courseUnits in body.
     */
    @GetMapping("/course-units")
    public List<CourseUnitDTO> getAllCourseUnits() {
        log.debug("REST request to get all CourseUnits");
        return courseUnitService.findAll();
    }

    /**
     * {@code GET  /course-units/:id} : get the "id" courseUnit.
     *
     * @param id the id of the courseUnitDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the courseUnitDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/course-units/{id}")
    public ResponseEntity<CourseUnitDTO> getCourseUnit(@PathVariable Long id) {
        log.debug("REST request to get CourseUnit : {}", id);
        Optional<CourseUnitDTO> courseUnitDTO = courseUnitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(courseUnitDTO);
    }

    /**
     * {@code DELETE  /course-units/:id} : delete the "id" courseUnit.
     *
     * @param id the id of the courseUnitDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/course-units/{id}")
    public ResponseEntity<Void> deleteCourseUnit(@PathVariable Long id) {
        log.debug("REST request to delete CourseUnit : {}", id);
        courseUnitService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
