package com.psi.service.mapper;


import com.psi.domain.*;
import com.psi.service.dto.CourseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Course} and its DTO {@link CourseDTO}.
 */
@Mapper(componentModel = "spring", uses = {SpecialtyMapper.class, EnrollmentDateMapper.class, CourseUnitMapper.class})
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {

    @Mapping(source = "enrollmentDate.id", target = "enrollmentDateId")
    @Mapping(source = "courseUnit.id", target = "courseUnitId")
    CourseDTO toDto(Course course);

    @Mapping(target = "classGroups", ignore = true)
    @Mapping(target = "removeClassGroup", ignore = true)
    @Mapping(target = "removeSpecialty", ignore = true)
    @Mapping(source = "enrollmentDateId", target = "enrollmentDate")
    @Mapping(source = "courseUnitId", target = "courseUnit")
    Course toEntity(CourseDTO courseDTO);

    default Course fromId(Long id) {
        if (id == null) {
            return null;
        }
        Course course = new Course();
        course.setId(id);
        return course;
    }
}
