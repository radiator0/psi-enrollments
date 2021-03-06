package com.psi.service.mapper;


import com.psi.domain.*;
import com.psi.service.dto.EnrollmentDateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EnrollmentDate} and its DTO {@link EnrollmentDateDTO}.
 */
@Mapper(componentModel = "spring", uses = {SemesterMapper.class})
public interface EnrollmentDateMapper extends EntityMapper<EnrollmentDateDTO, EnrollmentDate> {

    @Mapping(source = "semester.id", target = "semesterId")
    EnrollmentDateDTO toDto(EnrollmentDate enrollmentDate);

    @Mapping(target = "enrollmentUnits", ignore = true)
    @Mapping(target = "removeEnrollmentUnit", ignore = true)
    @Mapping(target = "enrollmentRights", ignore = true)
    @Mapping(target = "removeEnrollmentRight", ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "removeCourse", ignore = true)
    @Mapping(source = "semesterId", target = "semester")
    EnrollmentDate toEntity(EnrollmentDateDTO enrollmentDateDTO);

    default EnrollmentDate fromId(Long id) {
        if (id == null) {
            return null;
        }
        EnrollmentDate enrollmentDate = new EnrollmentDate();
        enrollmentDate.setId(id);
        return enrollmentDate;
    }
}
