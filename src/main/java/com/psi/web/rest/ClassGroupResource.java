package com.psi.web.rest;

import com.psi.service.ClassGroupService;
import com.psi.web.rest.errors.BadRequestAlertException;
import com.psi.service.dto.ClassGroupDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.psi.domain.ClassGroup}.
 */
@RestController
@RequestMapping("/api")
public class ClassGroupResource {

    private final Logger log = LoggerFactory.getLogger(ClassGroupResource.class);

    private static final String ENTITY_NAME = "classGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClassGroupService classGroupService;

    public ClassGroupResource(ClassGroupService classGroupService) {
        this.classGroupService = classGroupService;
    }

    /**
     * {@code POST  /class-groups} : Create a new classGroup.
     *
     * @param classGroupDTO the classGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new classGroupDTO, or with status {@code 400 (Bad Request)} if the classGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/class-groups")
    public ResponseEntity<ClassGroupDTO> createClassGroup(@Valid @RequestBody ClassGroupDTO classGroupDTO) throws URISyntaxException {
        log.debug("REST request to save ClassGroup : {}", classGroupDTO);
        if (classGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new classGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassGroupDTO result = classGroupService.save(classGroupDTO);
        return ResponseEntity.created(new URI("/api/class-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /class-groups} : Updates an existing classGroup.
     *
     * @param classGroupDTO the classGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated classGroupDTO,
     * or with status {@code 400 (Bad Request)} if the classGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the classGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/class-groups")
    public ResponseEntity<ClassGroupDTO> updateClassGroup(@Valid @RequestBody ClassGroupDTO classGroupDTO) throws URISyntaxException {
        log.debug("REST request to update ClassGroup : {}", classGroupDTO);
        if (classGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassGroupDTO result = classGroupService.save(classGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, classGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /class-groups} : get all the classGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classGroups in body.
     */
    @GetMapping("/class-groups")
    public ResponseEntity<List<ClassGroupDTO>> getAllClassGroups(Pageable pageable) {
        log.debug("REST request to get a page of ClassGroups");
        Page<ClassGroupDTO> page = classGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /class-groups/:id} : get the "id" classGroup.
     *
     * @param id the id of the classGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the classGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/class-groups/{id}")
    public ResponseEntity<ClassGroupDTO> getClassGroup(@PathVariable Long id) {
        log.debug("REST request to get ClassGroup : {}", id);
        Optional<ClassGroupDTO> classGroupDTO = classGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classGroupDTO);
    }

    /**
     * {@code DELETE  /class-groups/:id} : delete the "id" classGroup.
     *
     * @param id the id of the classGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/class-groups/{id}")
    public ResponseEntity<Void> deleteClassGroup(@PathVariable Long id) {
        log.debug("REST request to delete ClassGroup : {}", id);
        classGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
