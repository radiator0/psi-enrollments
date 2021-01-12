package com.psi.service.mapper;


import com.psi.domain.*;
import com.psi.service.dto.BuildingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Building} and its DTO {@link BuildingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BuildingMapper extends EntityMapper<BuildingDTO, Building> {


    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "removeRoom", ignore = true)
    Building toEntity(BuildingDTO buildingDTO);

    default Building fromId(Long id) {
        if (id == null) {
            return null;
        }
        Building building = new Building();
        building.setId(id);
        return building;
    }
}
