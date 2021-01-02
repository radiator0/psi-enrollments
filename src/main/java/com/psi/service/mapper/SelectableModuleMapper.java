package com.psi.service.mapper;

import com.psi.domain.*;
import com.psi.service.dto.SelectableModuleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SelectableModule} and its DTO {@link SelectableModuleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SelectableModuleMapper extends EntityMapper<SelectableModuleDTO, SelectableModule> {


    @Mapping(target = "courseUnits", ignore = true)
    @Mapping(target = "removeCourseUnit", ignore = true)
    SelectableModule toEntity(SelectableModuleDTO selectableModuleDTO);

    default SelectableModule fromId(Long id) {
        if (id == null) {
            return null;
        }
        SelectableModule selectableModule = new SelectableModule();
        selectableModule.setId(id);
        return selectableModule;
    }
}
