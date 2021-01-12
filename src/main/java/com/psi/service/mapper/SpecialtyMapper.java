package com.psi.service.mapper;


import com.psi.domain.*;
import com.psi.service.dto.SpecialtyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Specialty} and its DTO {@link SpecialtyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SpecialtyMapper extends EntityMapper<SpecialtyDTO, Specialty> {


    @Mapping(target = "enrollmentRights", ignore = true)
    @Mapping(target = "removeEnrollmentRight", ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "removeCourse", ignore = true)
    Specialty toEntity(SpecialtyDTO specialtyDTO);

    default Specialty fromId(Long id) {
        if (id == null) {
            return null;
        }
        Specialty specialty = new Specialty();
        specialty.setId(id);
        return specialty;
    }
}
