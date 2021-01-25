package com.psi.service;

import com.psi.domain.*;
import com.psi.repository.CourseRepository;
import com.psi.service.dto.EnrollingGroupDetailsDTO;
import com.psi.service.dto.SelectableModuleDetailsDTO;
import com.psi.service.mapper.EnrollingGroupDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ClassGroup}.
 */
@Service
@Transactional
public class EnrollingGroupDetailsService {

    private final Logger log = LoggerFactory.getLogger(EnrollingGroupDetailsService.class);

    private final UserService userService;

    private final CourseRepository courseRepository;

    private final EnrollingGroupDetailsMapper enrollingGroupDetailsMapper;

    public EnrollingGroupDetailsService(UserService userService, CourseRepository courseRepository, EnrollingGroupDetailsMapper enrollingGroupDetailsMapper) {
        this.userService = userService;
        this.courseRepository = courseRepository;
        this.enrollingGroupDetailsMapper = enrollingGroupDetailsMapper;
    }

    /**
     * Get all the selectableModules for enrollment where student has rights.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EnrollingGroupDetailsDTO> getAllGroupsForCourse(Long courseId, User user) {
        log.debug("Request to get all SelectableModules of enrollments");
        Student student = userService.getStudentInstance(user);

        Optional<Course> course = courseRepository.findById(courseId);

        return course.get().getClassGroups().stream()
            .map(c -> enrollingGroupDetailsMapper.toDto(c, student))
            .collect(Collectors.toList());
    }
}
