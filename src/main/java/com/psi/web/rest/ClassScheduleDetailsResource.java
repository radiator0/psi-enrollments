package com.psi.web.rest;

import com.psi.security.AuthoritiesConstants;
import com.psi.service.ScheduleService;
import com.psi.service.dto.ScheduleElementDTO;
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
 * REST controller for getting class schedules.
 */
@RestController
@RequestMapping("/api")
public class ClassScheduleDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ClassScheduleDetailsResource.class);
    private final ScheduleService scheduleService;

    public ClassScheduleDetailsResource(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * {@code GET  /class-schedules-details/:groupCode} : get all the classSchedules by groupCode.
     *
     * @param groupCode the key of the group.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classSchedules in body.
     */
    @GetMapping("/class-schedules-details/{groupCode}")
    public List<ScheduleElementDTO> getAllClassSchedulesByGroupCode(@PathVariable String groupCode) {
        log.debug("REST request to get all ScheduleElements by groupCode");
        return scheduleService.findAllByGroupCode(groupCode);
    }

}
