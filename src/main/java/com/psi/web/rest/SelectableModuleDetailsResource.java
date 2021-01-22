package com.psi.web.rest;

import com.psi.security.SecurityUtils;
import com.psi.service.SelectableModuleDetailsService;
import com.psi.service.SelectableModuleService;
import com.psi.service.dto.SelectableModuleDTO;
import com.psi.service.dto.SelectableModuleDetailsDTO;
import com.psi.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.psi.domain.SelectableModule}.
 */
@RestController
@RequestMapping("/api")
public class SelectableModuleDetailsResource {

    private final Logger log = LoggerFactory.getLogger(SelectableModuleDetailsResource.class);

    private final SelectableModuleDetailsService selectableModuleDetailsService;

    public SelectableModuleDetailsResource(SelectableModuleDetailsService selectableModuleDetailsService) {
        this.selectableModuleDetailsService = selectableModuleDetailsService;
    }

    /**
     * {@code GET  /enrollment/{id}/selectable-modules} : get all the selectableModules for enrollments with id for which student has rights.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of selectableModules in body.
     */
    @GetMapping("/enrollment/{id}/selectable-modules")
    public List<SelectableModuleDetailsDTO> getAllSelectableModulesForStudent(@PathVariable Long id) {
        log.debug("REST request to get all SelectableModules for enrollments {}", id);
        String userLogin = SecurityUtils.getCurrentUserLogin().get();
        Long studentId = Long.parseLong(userLogin);
        return selectableModuleDetailsService.getAllSelectableModulesForStudent(id, studentId);
    }
}
