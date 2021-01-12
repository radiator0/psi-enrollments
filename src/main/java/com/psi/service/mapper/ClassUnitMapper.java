package com.psi.service.mapper;


import com.psi.domain.*;
import com.psi.service.dto.ClassUnitDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClassUnit} and its DTO {@link ClassUnitDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClassGroupMapper.class, RoomMapper.class})
public interface ClassUnitMapper extends EntityMapper<ClassUnitDTO, ClassUnit> {

    @Mapping(source = "classGroup.id", target = "classGroupId")
    @Mapping(source = "room.id", target = "roomId")
    ClassUnitDTO toDto(ClassUnit classUnit);

    @Mapping(source = "classGroupId", target = "classGroup")
    @Mapping(source = "roomId", target = "room")
    ClassUnit toEntity(ClassUnitDTO classUnitDTO);

    default ClassUnit fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassUnit classUnit = new ClassUnit();
        classUnit.setId(id);
        return classUnit;
    }
}
