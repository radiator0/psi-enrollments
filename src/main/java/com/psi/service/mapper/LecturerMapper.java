package com.psi.service.mapper;

import com.psi.domain.*;
import com.psi.service.dto.LecturerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lecturer} and its DTO {@link LecturerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LecturerMapper extends EntityMapper<LecturerDTO, Lecturer> {


    @Mapping(target = "classGroups", ignore = true)
    @Mapping(target = "removeClassGroup", ignore = true)
    Lecturer toEntity(LecturerDTO lecturerDTO);

    default Lecturer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lecturer lecturer = new Lecturer();
        lecturer.setId(id);
        return lecturer;
    }
}
