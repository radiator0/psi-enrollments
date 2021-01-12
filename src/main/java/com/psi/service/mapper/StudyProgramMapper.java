package com.psi.service.mapper;


import com.psi.domain.*;
import com.psi.service.dto.StudyProgramDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StudyProgram} and its DTO {@link StudyProgramDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StudyProgramMapper extends EntityMapper<StudyProgramDTO, StudyProgram> {


    @Mapping(target = "fieldOfStudies", ignore = true)
    @Mapping(target = "removeFieldOfStudy", ignore = true)
    StudyProgram toEntity(StudyProgramDTO studyProgramDTO);

    default StudyProgram fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudyProgram studyProgram = new StudyProgram();
        studyProgram.setId(id);
        return studyProgram;
    }
}
