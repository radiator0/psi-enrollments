package com.psi.service.mapper;


import com.psi.domain.*;
import com.psi.service.dto.CourseUnitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseUnit} and its DTO {@link CourseUnitDTO}.
 */
@Mapper(componentModel = "spring", uses = {SelectableModuleMapper.class})
public interface CourseUnitMapper extends EntityMapper<CourseUnitDTO, CourseUnit> {

    @Mapping(source = "selectableModule.id", target = "selectableModuleId")
    CourseUnitDTO toDto(CourseUnit courseUnit);

    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "removeCourse", ignore = true)
    @Mapping(source = "selectableModuleId", target = "selectableModule")
    CourseUnit toEntity(CourseUnitDTO courseUnitDTO);

    default CourseUnit fromId(Long id) {
        if (id == null) {
            return null;
        }
        CourseUnit courseUnit = new CourseUnit();
        courseUnit.setId(id);
        return courseUnit;
    }
}
