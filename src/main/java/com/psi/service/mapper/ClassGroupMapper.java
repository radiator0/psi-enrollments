package com.psi.service.mapper;

import com.psi.domain.*;
import com.psi.service.dto.ClassGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClassGroup} and its DTO {@link ClassGroupDTO}.
 */
@Mapper(componentModel = "spring", uses = {CourseMapper.class, LecturerMapper.class})
public interface ClassGroupMapper extends EntityMapper<ClassGroupDTO, ClassGroup> {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "lecturer.id", target = "lecturerId")
    ClassGroupDTO toDto(ClassGroup classGroup);

    @Mapping(target = "requests", ignore = true)
    @Mapping(target = "removeRequest", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "removeEnrollment", ignore = true)
    @Mapping(target = "classUnits", ignore = true)
    @Mapping(target = "removeClassUnit", ignore = true)
    @Mapping(target = "classSchedules", ignore = true)
    @Mapping(target = "removeClassSchedule", ignore = true)
    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "lecturerId", target = "lecturer")
    ClassGroup toEntity(ClassGroupDTO classGroupDTO);

    default ClassGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassGroup classGroup = new ClassGroup();
        classGroup.setId(id);
        return classGroup;
    }
}
