package com.psi.service;

import com.psi.domain.EnrollmentRight;
import com.psi.domain.SelectableModule;
import com.psi.domain.Student;
import com.psi.domain.User;
import com.psi.repository.StudentRepository;
import com.psi.service.dto.SelectableModuleDetailsDTO;
import com.psi.service.mapper.SelectableModuleDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SelectableModule}.
 */
@Service
@Transactional
public class SelectableModuleDetailsService {

    private final Logger log = LoggerFactory.getLogger(SelectableModuleDetailsService.class);

    private final UserService userService;

    private final SelectableModuleDetailsMapper selectableModuleDetailsMapper;

    public SelectableModuleDetailsService(UserService userService, SelectableModuleDetailsMapper selectableModuleDetailsMapper) {
        this.userService = userService;
        this.selectableModuleDetailsMapper = selectableModuleDetailsMapper;
    }

    /**
     * Get all the selectableModules for enrollment where student has rights.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<SelectableModuleDetailsDTO> getAllSelectableModulesForStudent(Long enrollmentsId, User user) {
        log.debug("Request to get all SelectableModules of enrollments");
        Student student = userService.getStudentInstance(user);
        EnrollmentRight right = student.getEnrollmentRights().stream()
            .filter(r -> r.getEnrollmentDate().getId().equals(enrollmentsId)).findFirst().get();

        return selectableModuleDetailsMapper.toDtos(
            right.getEnrollmentDate().getCourses().stream()
            .filter(c -> c.getSpecialties().isEmpty() || c.getSpecialties().contains(right.getSpecialty()))
            .collect(Collectors.toCollection(LinkedList::new)), student
        );
    }
}
