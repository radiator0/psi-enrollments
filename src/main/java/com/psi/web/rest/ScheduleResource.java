package com.psi.web.rest;

import com.psi.security.SecurityUtils;
import com.psi.service.ScheduleService;
import com.psi.service.dto.ScheduleElementDTO;
import com.psi.web.rest.errors.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing schedules.
 */
@RestController
@RequestMapping("/api")
public class ScheduleResource {

    private final Logger log = LoggerFactory.getLogger(ScheduleResource.class);

    private final ScheduleService scheduleService;

    public ScheduleResource(ScheduleService classUnitService) {
        this.scheduleService = classUnitService;
    }


    /**
     * {@code GET  /schedule/:id} : get all the schedule elements for student with id.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schedule elements in body.
     */
    @GetMapping("/my-schedule")
    public List<ScheduleElementDTO> getAllScheduleElementsForUser() {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();
        if(userLogin.isPresent()) {
            log.debug("REST request to get all schedule elements for user : {}", userLogin);
            return scheduleService.findAllForUser(userLogin.get());
        }
        else {
            throw new UnauthorizedException();
        }
    }
}
