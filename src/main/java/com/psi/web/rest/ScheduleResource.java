package com.psi.web.rest;

import com.psi.security.SecurityUtils;
import com.psi.service.ScheduleService;
import com.psi.service.dto.RecurringScheduleElementDTO;
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
     * {@code GET  /week-schedule} : get all schedule elements for student.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schedule elements in body.
     */
    @GetMapping("/week-schedule")
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

    /**
     * {@code GET  /semester-schedule} : get all recurring schedule elements for user for last started semester.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recurring schedule elements for last started semester in body.
     */
    @GetMapping("/semester-schedule")
    public List<RecurringScheduleElementDTO> getAllRecurringScheduleElementsForUser() {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();
        if(userLogin.isPresent()) {
            log.debug("REST request to get all recurring schedule elements for last started semester for user : {}", userLogin);
            return scheduleService.findAllRecurringScheduleElementsOfLastSemesterForUser(userLogin.get());
        }
        else {
            throw new UnauthorizedException();
        }
    }

    /**
     * {@code GET  /semester-schedule/:id} : get all recurring schedule elements for user.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recurring schedule elements in body.
     */
    @GetMapping("/semester-schedule/{id}")
    public List<RecurringScheduleElementDTO> getAllRecurringScheduleElementsForUser(@PathVariable Long id) {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();
        if(userLogin.isPresent()) {
            log.debug("REST request to get all recurring schedule elements for user : {}", userLogin);
            return scheduleService.findAllRecurringScheduleElementsOfLastSemesterForUser(userLogin.get());
        }
        else {
            throw new UnauthorizedException();
        }
    }
}
