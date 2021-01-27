package com.psi.web.rest;

import com.psi.domain.User;
import com.psi.security.AuthoritiesConstants;
import com.psi.service.EnrollingGroupDetailsService;
import com.psi.service.SelectableModuleDetailsService;
import com.psi.service.UserService;
import com.psi.service.dto.EnrollingGroupDetailsDTO;
import com.psi.service.dto.SelectableModuleDetailsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing {@link com.psi.domain.SelectableModule}.
 */
@RestController
@RequestMapping("/api")
public class EnrollingGroupDetailsResource {

    private static class EnrollingGroupDetailsResourceException extends RuntimeException {
        private EnrollingGroupDetailsResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(EnrollingGroupDetailsResource.class);

    private final UserService userService;
    private final EnrollingGroupDetailsService enrollingGroupDetailsService;

    public EnrollingGroupDetailsResource(UserService userService, EnrollingGroupDetailsService enrollingGroupDetailsService) {
        this.userService = userService;
        this.enrollingGroupDetailsService = enrollingGroupDetailsService;
    }

    /**
     * {@code GET  /enrollment/{id}/selectable-modules} : get all the selectableModules for enrollments with id for which student has rights.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of selectableModules in body.
     */
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.STUDENT + "\")")
    @GetMapping("/course/{id}/groups")
    public List<EnrollingGroupDetailsDTO> getAllGroupsForCourse(@PathVariable Long id) {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new EnrollingGroupDetailsResource.EnrollingGroupDetailsResourceException("User cannot be found"));
        log.debug("REST request to get all groups for course {}", id);
        return enrollingGroupDetailsService.getAllGroupsForCourse(id, user);
    }
}
