package com.psi.web.rest;

import com.psi.domain.User;
import com.psi.security.SecurityUtils;
import com.psi.service.SelectableModuleDetailsService;
import com.psi.service.SelectableModuleService;
import com.psi.service.UserService;
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

    private static class SelectableModuleDetailsResourceException extends RuntimeException {
        private SelectableModuleDetailsResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(SelectableModuleDetailsResource.class);

    private final UserService userService;
    private final SelectableModuleDetailsService selectableModuleDetailsService;

    public SelectableModuleDetailsResource(UserService userService, SelectableModuleDetailsService selectableModuleDetailsService) {
        this.userService = userService;
        this.selectableModuleDetailsService = selectableModuleDetailsService;
    }

    /**
     * {@code GET  /enrollment/{id}/selectable-modules} : get all the selectableModules for enrollments with id for which student has rights.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of selectableModules in body.
     */
    @GetMapping("/enrollment/{id}/selectable-modules")
    public List<SelectableModuleDetailsDTO> getAllSelectableModulesForStudent(@PathVariable Long id) {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new SelectableModuleDetailsResource.SelectableModuleDetailsResourceException("User cannot be found"));
        log.debug("REST request to get all SelectableModules for enrollments {}", id);
        return selectableModuleDetailsService.getAllSelectableModulesForStudent(id, user);
    }
}
