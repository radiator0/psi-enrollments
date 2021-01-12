package com.psi.service.mapper;


import com.psi.domain.*;
import com.psi.service.dto.FieldOfStudyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FieldOfStudy} and its DTO {@link FieldOfStudyDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudyProgramMapper.class})
public interface FieldOfStudyMapper extends EntityMapper<FieldOfStudyDTO, FieldOfStudy> {

    @Mapping(source = "studyProgram.id", target = "studyProgramId")
    FieldOfStudyDTO toDto(FieldOfStudy fieldOfStudy);

    @Mapping(target = "semesters", ignore = true)
    @Mapping(target = "removeSemester", ignore = true)
    @Mapping(source = "studyProgramId", target = "studyProgram")
    FieldOfStudy toEntity(FieldOfStudyDTO fieldOfStudyDTO);

    default FieldOfStudy fromId(Long id) {
        if (id == null) {
            return null;
        }
        FieldOfStudy fieldOfStudy = new FieldOfStudy();
        fieldOfStudy.setId(id);
        return fieldOfStudy;
    }
}
