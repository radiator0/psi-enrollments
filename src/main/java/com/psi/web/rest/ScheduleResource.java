package com.psi.web.rest;

import com.psi.service.ScheduleService;
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

    private final Logger log = LoggerFactory.getLogger(ScheduleResource.class);

    private final ScheduleService scheduleService;

    public ScheduleResource(ScheduleService classUnitService) {
        this.scheduleService = classUnitService;
    }


    /**
     * {@code GET  /class-units/student/:id} : get all the classUnits for student with id.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of classUnits in body.
     */
    @GetMapping("/schedule/:id")
    public List<ScheduleElementDTO> getAllClassUnitsByStudentId(@PathVariable Long id) {
        log.debug("REST request to get all ClassUnits by student id : {}", id);
        return scheduleService.findAllByStudentId(id);
    }
}
