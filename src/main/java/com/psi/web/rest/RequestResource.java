package com.psi.web.rest;

import com.psi.domain.Lecturer;
import com.psi.domain.Student;
import com.psi.domain.User;
import com.psi.service.RequestService;
import com.psi.service.UserService;
import com.psi.service.dto.RequestDTO;
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
 * REST controller for managing {@link com.psi.domain.Request}.
 */
@RestController
@RequestMapping("/api")
public class RequestResource {

    private static class RequestResourceException extends RuntimeException {
        private RequestResourceException(String message) {
            super(message);
        }
    }

    private final Logger log = LoggerFactory.getLogger(RequestResource.class);

    private static final String ENTITY_NAME = "request";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestService requestService;
    private final UserService userService;

    public RequestResource(RequestService requestService, UserService userService) {
        this.requestService = requestService;
        this.userService = userService;
    }

    /**
     * {@code POST  /requests} : Create a new request.
     *
     * @param requestDTO the requestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestDTO, or with status {@code 400 (Bad Request)} if the request has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requests")
    public ResponseEntity<RequestDTO> createRequest(@Valid @RequestBody RequestDTO requestDTO) throws URISyntaxException {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new RequestResource.RequestResourceException("User cannot be found"));
        if (userService.isUserStudent(user)) {
            Student student = userService.getStudentInstance(user);
            log.debug("REST request to save Request : {}", requestDTO);
            if (requestDTO.getId() != null) {
                throw new BadRequestAlertException("A new request cannot already have an ID", ENTITY_NAME, "idexists");
            }
            requestDTO.setIsExamined(false);
            requestDTO.setIsAccepted(false);
            RequestDTO result = requestService.save(requestDTO);
            return ResponseEntity.created(new URI("/api/requests/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
        } else {
            throw new RequestResource.RequestResourceException("User hasn't correct authorities");
        }
    }

    /**
     * {@code PUT  /requests} : Accepts an existing request.
     *
     * @param id the id of the requestDTO to accept.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestDTO,
     * or with status {@code 400 (Bad Request)} if the id is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestDTO couldn't be accepted.
     */
    @PostMapping("/requests/{id}/accept")
    public ResponseEntity<RequestDTO> acceptRequest(@PathVariable Long id) {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new RequestResource.RequestResourceException("User cannot be found"));
        if (userService.isUserLecturer(user)) {
            requestService.accept(id);
        } else {
            throw new RequestResource.RequestResourceException("User hasn't authorities to accept request");
        }
        log.debug("REST request to accept Request : {}", id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code PUT  /requests} : Declines an existing request.
     *
     * @param id the id of the requestDTO to accept.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestDTO,
     * or with status {@code 400 (Bad Request)} if the id is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestDTO couldn't be accepted.
     */
    @PostMapping("/requests/{id}/decline")
    public ResponseEntity<RequestDTO> declineRequest(@PathVariable Long id) {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new RequestResource.RequestResourceException("User cannot be found"));
        if (userService.isUserLecturer(user)) {
            requestService.decline(id);
        } else {
            throw new RequestResource.RequestResourceException("User hasn't authorities to decline request");
        }
        log.debug("REST request to decline Request : {}", id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code GET  /requests} : get all the requests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requests in body.
     */
    @GetMapping("/requests")
    public ResponseEntity<List<RequestDTO>> getAllRequests() {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new RequestResource.RequestResourceException("User cannot be found"));
        List<RequestDTO> requestDTOList;
        if (userService.isUserStudent(user)) {
            Student student = userService.getStudentInstance(user);
            log.debug("REST request to get a list of Student Requests");
            requestDTOList = requestService.findAllByStudent(student);
        } else if (userService.isUserLecturer(user)) {
            Lecturer lecturer = userService.getLecturerInstance(user);
            log.debug("REST request to get a list of Lecturer Requests");
            requestDTOList = requestService.findAllByLecturer(lecturer);
        } else {
            throw new RequestResource.RequestResourceException("User hasn't correct authorities");
        }
        return ResponseEntity.ok().body(requestDTOList);
    }

    /**
     * {@code GET  /requests/:id} : get the "id" request.
     *
     * @param id the id of the requestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requests/{id}")
    public ResponseEntity<RequestDTO> getRequest(@PathVariable Long id) {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new RequestResource.RequestResourceException("User cannot be found"));
        if (!userService.isUserStudent(user) && !userService.isUserLecturer(user)) {
            throw new RequestResource.RequestResourceException("User hasn't correct authorities");
        }
        log.debug("REST request to get Request : {}", id);
        Optional<RequestDTO> requestDTO = requestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestDTO);
    }

    /**
     * {@code DELETE  /requests/:id} : delete the "id" request.
     *
     * @param id the id of the requestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requests/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        User user = userService.getUserWithAuthorities().orElseThrow(() -> new RequestResource.RequestResourceException("User cannot be found"));
        if (userService.isUserStudent(user)) {
            requestService.delete(id);
        } else if (userService.isUserLecturer(user)) {
            throw new RequestResource.RequestResourceException("User hasn't correct authorities to delete request");
        }
        log.debug("REST request to delete Request : {}", id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
