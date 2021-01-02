package com.psi.web.rest;

import com.psi.service.SelectableModuleService;
import com.psi.web.rest.errors.BadRequestAlertException;
import com.psi.service.dto.SelectableModuleDTO;

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
 * REST controller for managing {@link com.psi.domain.SelectableModule}.
 */
@RestController
@RequestMapping("/api")
public class SelectableModuleResource {

    private final Logger log = LoggerFactory.getLogger(SelectableModuleResource.class);

    private static final String ENTITY_NAME = "selectableModule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SelectableModuleService selectableModuleService;

    public SelectableModuleResource(SelectableModuleService selectableModuleService) {
        this.selectableModuleService = selectableModuleService;
    }

    /**
     * {@code POST  /selectable-modules} : Create a new selectableModule.
     *
     * @param selectableModuleDTO the selectableModuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new selectableModuleDTO, or with status {@code 400 (Bad Request)} if the selectableModule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/selectable-modules")
    public ResponseEntity<SelectableModuleDTO> createSelectableModule(@RequestBody SelectableModuleDTO selectableModuleDTO) throws URISyntaxException {
        log.debug("REST request to save SelectableModule : {}", selectableModuleDTO);
        if (selectableModuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new selectableModule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SelectableModuleDTO result = selectableModuleService.save(selectableModuleDTO);
        return ResponseEntity.created(new URI("/api/selectable-modules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /selectable-modules} : Updates an existing selectableModule.
     *
     * @param selectableModuleDTO the selectableModuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated selectableModuleDTO,
     * or with status {@code 400 (Bad Request)} if the selectableModuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the selectableModuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/selectable-modules")
    public ResponseEntity<SelectableModuleDTO> updateSelectableModule(@RequestBody SelectableModuleDTO selectableModuleDTO) throws URISyntaxException {
        log.debug("REST request to update SelectableModule : {}", selectableModuleDTO);
        if (selectableModuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SelectableModuleDTO result = selectableModuleService.save(selectableModuleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, selectableModuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /selectable-modules} : get all the selectableModules.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of selectableModules in body.
     */
    @GetMapping("/selectable-modules")
    public List<SelectableModuleDTO> getAllSelectableModules() {
        log.debug("REST request to get all SelectableModules");
        return selectableModuleService.findAll();
    }

    /**
     * {@code GET  /selectable-modules/:id} : get the "id" selectableModule.
     *
     * @param id the id of the selectableModuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the selectableModuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/selectable-modules/{id}")
    public ResponseEntity<SelectableModuleDTO> getSelectableModule(@PathVariable Long id) {
        log.debug("REST request to get SelectableModule : {}", id);
        Optional<SelectableModuleDTO> selectableModuleDTO = selectableModuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(selectableModuleDTO);
    }

    /**
     * {@code DELETE  /selectable-modules/:id} : delete the "id" selectableModule.
     *
     * @param id the id of the selectableModuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/selectable-modules/{id}")
    public ResponseEntity<Void> deleteSelectableModule(@PathVariable Long id) {
        log.debug("REST request to delete SelectableModule : {}", id);
        selectableModuleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
