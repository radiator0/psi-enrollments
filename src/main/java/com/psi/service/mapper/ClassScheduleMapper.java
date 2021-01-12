package com.psi.service.mapper;


import com.psi.domain.*;
import com.psi.service.dto.ClassScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ClassSchedule} and its DTO {@link ClassScheduleDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClassGroupMapper.class, RoomMapper.class})
public interface ClassScheduleMapper extends EntityMapper<ClassScheduleDTO, ClassSchedule> {

    @Mapping(source = "classGroup.id", target = "classGroupId")
    @Mapping(source = "room.id", target = "roomId")
    ClassScheduleDTO toDto(ClassSchedule classSchedule);

    @Mapping(source = "classGroupId", target = "classGroup")
    @Mapping(source = "roomId", target = "room")
    ClassSchedule toEntity(ClassScheduleDTO classScheduleDTO);

    default ClassSchedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setId(id);
        return classSchedule;
    }
}
