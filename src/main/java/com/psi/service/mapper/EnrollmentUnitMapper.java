package com.psi.service.mapper;


import com.psi.domain.*;
import com.psi.service.dto.EnrollmentUnitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EnrollmentUnit} and its DTO {@link EnrollmentUnitDTO}.
 */
@Mapper(componentModel = "spring", uses = {EnrollmentDateMapper.class})
public interface EnrollmentUnitMapper extends EntityMapper<EnrollmentUnitDTO, EnrollmentUnit> {

    @Mapping(source = "enrollmentDate.id", target = "enrollmentDateId")
    EnrollmentUnitDTO toDto(EnrollmentUnit enrollmentUnit);

    @Mapping(source = "enrollmentDateId", target = "enrollmentDate")
    EnrollmentUnit toEntity(EnrollmentUnitDTO enrollmentUnitDTO);

    default EnrollmentUnit fromId(Long id) {
        if (id == null) {
            return null;
        }
        EnrollmentUnit enrollmentUnit = new EnrollmentUnit();
        enrollmentUnit.setId(id);
        return enrollmentUnit;
    }
}
