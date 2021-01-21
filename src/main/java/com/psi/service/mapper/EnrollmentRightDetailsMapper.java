package com.psi.service.mapper;


import com.psi.domain.EnrollmentRight;
import com.psi.service.dto.EnrollmentRightDetailsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link EnrollmentRight} and its DTO {@link EnrollmentRightDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EnrollmentRightDetailsMapper extends EntityMapper<EnrollmentRightDetailsDTO, EnrollmentRight> {

    @Mapping(source = "enrollmentDate.name", target = "name")
    @Mapping(source = "enrollmentDate.isPreEnrollment", target = "isPreEnrollment")
    @Mapping(source = "enrollmentDate.startDate", target = "enrollmentsStartDate")
    @Mapping(source = "enrollmentDate.endDate", target = "enrollmentsEndDate")
    @Mapping(source = "enrollmentDate.enrollmentUnits", target = "enrollmentUnits")
    @Mapping(source = "enrollmentDate.semester.fieldOfStudy.name", target = "fieldOfStudy")
    @Mapping(source = "enrollmentDate.semester.number", target = "semester")
    @Mapping(source = "startDate", target = "rightStartDate")
    @Mapping(source = "specialty.name", target = "rightSpecialty")
    EnrollmentRightDetailsDTO toDto(EnrollmentRight enrollmentRight);

    default EnrollmentRight fromId(Long id) {
        if (id == null) {
            return null;
        }
        EnrollmentRight enrollmentright = new EnrollmentRight();
        enrollmentright.setId(id);
        return enrollmentright;
    }
}
