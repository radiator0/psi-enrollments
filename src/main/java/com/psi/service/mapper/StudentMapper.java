package com.psi.service.mapper;


import com.psi.domain.*;
import com.psi.service.dto.StudentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Student} and its DTO {@link StudentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {


    @Mapping(target = "requests", ignore = true)
    @Mapping(target = "removeRequest", ignore = true)
    @Mapping(target = "enrollmentRights", ignore = true)
    @Mapping(target = "removeEnrollmentRight", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    @Mapping(target = "removeEnrollment", ignore = true)
    Student toEntity(StudentDTO studentDTO);

    default Student fromId(Long id) {
        if (id == null) {
            return null;
        }
        Student student = new Student();
        student.setId(id);
        return student;
    }
}
