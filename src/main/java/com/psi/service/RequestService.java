package com.psi.service;

import com.psi.domain.ClassGroup;
import com.psi.domain.Lecturer;
import com.psi.domain.Request;
import com.psi.domain.Student;
import com.psi.repository.LecturerRepository;
import com.psi.repository.RequestRepository;
import com.psi.repository.StudentRepository;
import com.psi.service.dto.RequestDTO;
import com.psi.service.mapper.RequestMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Request}.
 */
@Service
@Transactional
public class RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestService.class);

    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    private final ClassGroupService classGroupService;

    private final StudentRepository studentRepository;

    private final LecturerRepository lecturerRepository;

    public RequestService(RequestRepository requestRepository, RequestMapper requestMapper,
                          ClassGroupService classGroupService, StudentRepository studentRepository,
                          LecturerRepository lecturerRepository) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
        this.classGroupService = classGroupService;
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
    }

    /**
     * Save a request.
     *
     * @param requestDTO the entity to save.
     * @return the persisted entity.
     */
    public RequestDTO save(RequestDTO requestDTO) {
        log.debug("Request to save Request : {}", requestDTO);
        Request request = requestMapper.toEntity(requestDTO);
        request.setDate(Instant.now());
        request.setUuid(UUID.randomUUID());
        request.setIsExamined(false);
        request.setIsAccepted(false);
        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    /**
     * Get all the requests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Requests");
        Page<RequestDTO> requestDTOS = requestRepository.findAll(pageable)
            .map(requestMapper::toDto);
        requestDTOS.stream().forEach(requestDTO -> addAdditionalDetails(Optional.of(requestDTO)));
        return requestDTOS;
    }

    /**
     * Get all the requests.
     *
     * @param student the student.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RequestDTO> findAllByStudent(Student student) {
        log.debug("Request to get all Requests");
        List<RequestDTO> requestDTOS = requestRepository.findAllByStudent(student).stream()
            .map(requestMapper::toDto).collect(Collectors.toList());
        requestDTOS.forEach(requestDTO -> addAdditionalDetails(Optional.of(requestDTO)));
        return requestDTOS;
    }

    /**
     * Get all the requests.
     *
     * @param lecturer the lecturer.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RequestDTO> findAllByLecturer(Lecturer lecturer) {
        log.debug("Request to get all Requests");
        List<ClassGroup> classGroups = classGroupService.findAllByLecturer(lecturer);
        List<RequestDTO> requestDTOS = requestRepository.findAllByClassGroupIn(classGroups).stream()
            .map(requestMapper::toDto).collect(Collectors.toList());
        requestDTOS.forEach(requestDTO -> addAdditionalDetails(Optional.of(requestDTO)));
        return requestDTOS;
    }

    /**
     * Get one request by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RequestDTO> findOne(Long id) {
        log.debug("Request to get Request : {}", id);
        Optional<RequestDTO> request = requestRepository.findById(id)
            .map(requestMapper::toDto);
        addAdditionalDetails(request);
        return request;
    }

    /**
     * Delete the request by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Request : {}", id);
        requestRepository.deleteById(id);
    }

    /**
     * Accept the request by id.
     *
     * @param id the id of the entity.
     */
    public void accept(Long id) {
        log.debug("Request to accept Request : {}", id);
        requestRepository.findById(id).ifPresent(request -> {
            if (request.isIsExamined()) {
                throw new RequestAlreadyExaminedException();
            }
            request.setIsExamined(true);
            request.setIsAccepted(true);
            requestRepository.save(request);
        });
    }

    /**
     * Decline the request by id.
     *
     * @param id the id of the entity.
     */
    public void decline(Long id) {
        log.debug("Request to decline Request : {}", id);
        requestRepository.findById(id).ifPresent(request -> {
            if (request.isIsExamined()) {
                throw new RequestAlreadyExaminedException();
            }
            request.setIsExamined(true);
            request.setIsAccepted(false);
            requestRepository.save(request);
        });
    }

    /**
     * Get number of not examined requests for lecturer.
     *
     * @param isExamined is Request examined.
     * @param lecturer the Lecturer.
     */
    public Integer countAllNotExaminedRequestsForLecturer(Boolean isExamined, Lecturer lecturer) {
        List<ClassGroup> classGroups = classGroupService.findAllByLecturer(lecturer);
        return requestRepository.countAllByIsExaminedAndClassGroupIn(isExamined, classGroups);
    }

    /**
     * Get number of not examined requests for student.
     *
     * @param isExamined is Request examined.
     * @param student the Student.
     */
    public Integer countAllNotExaminedRequestsForStudent(Boolean isExamined, Student student) {
        return requestRepository.countAllByIsExaminedAndStudent(isExamined, student);
    }


    private void addAdditionalDetails(Optional<RequestDTO> request) {
        request.ifPresent((r) -> {
            classGroupService.findOne(r.getClassGroupId()).ifPresent((classGroup) -> {
                r.setClassGroupCode(classGroup.getCode());
                lecturerRepository.findById(classGroup.getMainLecturerId()).ifPresent(l ->
                    r.setLecturerName(l.getName()));
            });
            studentRepository.findById(r.getStudentId()).ifPresent((student) ->
                r.setStudentName(student.getName()));
        });
    }
}
