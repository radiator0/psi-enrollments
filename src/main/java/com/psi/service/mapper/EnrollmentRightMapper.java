package com.psi.service.mapper;

import com.psi.domain.*;
import com.psi.service.dto.EnrollmentRightDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EnrollmentRight} and its DTO {@link EnrollmentRightDTO}.
 */
@Mapper(componentModel = "spring", uses = {EnrollmentDateMapper.class, SpecialtyMapper.class, StudentMapper.class})
public interface EnrollmentRightMapper extends EntityMapper<EnrollmentRightDTO, EnrollmentRight> {

    @Mapping(source = "enrollmentDate.id", target = "enrollmentDateId")
    @Mapping(source = "specialty.id", target = "specialtyId")
    @Mapping(source = "student.id", target = "studentId")
    EnrollmentRightDTO toDto(EnrollmentRight enrollmentRight);

    @Mapping(source = "enrollmentDateId", target = "enrollmentDate")
    @Mapping(source = "specialtyId", target = "specialty")
    @Mapping(source = "studentId", target = "student")
    EnrollmentRight toEntity(EnrollmentRightDTO enrollmentRightDTO);

    default EnrollmentRight fromId(Long id) {
        if (id == null) {
            return null;
        }
        EnrollmentRight enrollmentRight = new EnrollmentRight();
        enrollmentRight.setId(id);
        return enrollmentRight;
    }
}
