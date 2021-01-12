package com.psi.service.mapper;


import com.psi.domain.*;
import com.psi.service.dto.RequestDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Request} and its DTO {@link RequestDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClassGroupMapper.class, StudentMapper.class})
public interface RequestMapper extends EntityMapper<RequestDTO, Request> {

    @Mapping(source = "classGroup.id", target = "classGroupId")
    @Mapping(source = "student.id", target = "studentId")
    RequestDTO toDto(Request request);

    @Mapping(source = "classGroupId", target = "classGroup")
    @Mapping(source = "studentId", target = "student")
    Request toEntity(RequestDTO requestDTO);

    default Request fromId(Long id) {
        if (id == null) {
            return null;
        }
        Request request = new Request();
        request.setId(id);
        return request;
    }
}
