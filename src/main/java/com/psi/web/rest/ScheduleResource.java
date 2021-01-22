package com.psi.web.rest;

import com.psi.domain.User;
import com.psi.service.ScheduleService;
import com.psi.service.UserService;
import com.psi.service.dto.RecurringScheduleElementDTO;
import com.psi.service.dto.ScheduleElementDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing schedules.
 */
@RestController
@RequestMapping("/api")
public class ScheduleResource {

    private static class ScheduleResourceException extends RuntimeException {
        private ScheduleResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(ScheduleResource.class);

    private final ScheduleService scheduleService;
    private final UserService userService;

    public ScheduleResource(ScheduleService classUnitService, UserService userService) {
        this.scheduleService = classUnitService;
        this.userService = userService;
    }


    /**
     * {@code GET  /week-schedule} : get all schedule elements for student.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of schedule elements in body.
     */
    @GetMapping("/week-schedule")
    public List<ScheduleElementDTO> getAllScheduleElementsForUser() {
        User user = userService.getUserWithAuthorities().orElseThrow(() ->  new ScheduleResourceException("User cannot be found"));
        log.debug("REST request to get all schedule elements for user : {}", user.getLogin());
        return scheduleService.findAllForUser(user);
    }

    /**
     * {@code GET  /semester-schedule} : get all recurring schedule elements of user for last started semester.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recurring schedule elements for last started semester in body.
     */
    @GetMapping("/semester-schedule")
    public List<RecurringScheduleElementDTO> getAllRecurringScheduleElementsForUser() {
        User user = userService.getUserWithAuthorities().orElseThrow(() ->  new ScheduleResourceException("User cannot be found"));
        log.debug("REST request to get all recurring schedule elements for last started semester for user : {}", user.getLogin());
        return scheduleService.findAllRecurringScheduleElementsOfLastSemesterForUser(user);
    }
}
