package com.psi.service.mapper;

import com.psi.domain.*;
import com.psi.service.dto.SemesterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Semester} and its DTO {@link SemesterDTO}.
 */
@Mapper(componentModel = "spring", uses = {FieldOfStudyMapper.class})
public interface SemesterMapper extends EntityMapper<SemesterDTO, Semester> {

    @Mapping(source = "fieldOfStudy.id", target = "fieldOfStudyId")
    SemesterDTO toDto(Semester semester);

    @Mapping(target = "enrollmentDates", ignore = true)
    @Mapping(target = "removeEnrollmentDate", ignore = true)
    @Mapping(source = "fieldOfStudyId", target = "fieldOfStudy")
    Semester toEntity(SemesterDTO semesterDTO);

    default Semester fromId(Long id) {
        if (id == null) {
            return null;
        }
        Semester semester = new Semester();
        semester.setId(id);
        return semester;
    }
}
