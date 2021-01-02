package com.psi.service.mapper;

import com.psi.domain.*;
import com.psi.service.dto.RoomDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Room} and its DTO {@link RoomDTO}.
 */
@Mapper(componentModel = "spring", uses = {BuildingMapper.class})
public interface RoomMapper extends EntityMapper<RoomDTO, Room> {

    @Mapping(source = "building.id", target = "buildingId")
    RoomDTO toDto(Room room);

    @Mapping(target = "classSchedules", ignore = true)
    @Mapping(target = "removeClassSchedule", ignore = true)
    @Mapping(target = "classUnits", ignore = true)
    @Mapping(target = "removeClassUnit", ignore = true)
    @Mapping(source = "buildingId", target = "building")
    Room toEntity(RoomDTO roomDTO);

    default Room fromId(Long id) {
        if (id == null) {
            return null;
        }
        Room room = new Room();
        room.setId(id);
        return room;
    }
}
