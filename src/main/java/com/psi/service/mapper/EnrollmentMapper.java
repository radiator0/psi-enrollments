package com.psi.service.mapper;

import com.psi.domain.*;
import com.psi.service.dto.EnrollmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Enrollment} and its DTO {@link EnrollmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudentMapper.class, ClassGroupMapper.class})
public interface EnrollmentMapper extends EntityMapper<EnrollmentDTO, Enrollment> {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "classGroup.id", target = "classGroupId")
    EnrollmentDTO toDto(Enrollment enrollment);

    @Mapping(source = "studentId", target = "student")
    @Mapping(source = "classGroupId", target = "classGroup")
    Enrollment toEntity(EnrollmentDTO enrollmentDTO);

    default Enrollment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setId(id);
        return enrollment;
    }
}
