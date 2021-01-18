package com.psi.service.mapper;


import com.psi.domain.ClassUnit;
import com.psi.service.dto.ClassUnitDTO;
import com.psi.service.dto.ScheduleElementDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ClassUnit} and its DTO {@link ClassUnitDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClassGroupMapper.class, RoomMapper.class})
public interface ScheduleElementMapper extends EntityMapper<ScheduleElementDTO, ClassUnit> {
    @Mapping(source = "startTime", target = "startDate")
    @Mapping(source = "endTime", target = "endDate")
    @Mapping(source = "classGroup.course.name", target = "courseName")
    @Mapping(source = "classGroup.course.shortName", target = "courseShortName")
    @Mapping(source = "classGroup.course.form", target = "classType")
    @Mapping(source = "room.number", target = "room")
    @Mapping(source = "room.building.name", target = "building")
    @Mapping(source = "classGroup.mainLecturer.title", target = "lecturerTitle")
    @Mapping(source = "classGroup.mainLecturer.firstName", target = "lecturerFirstName")
    @Mapping(source = "classGroup.mainLecturer.secondName", target = "lecturerSecondName")
    @Mapping(source = "classGroup.mainLecturer.lastName", target = "lecturerLastName")
    ScheduleElementDTO toDto(ClassUnit classUnit);

    default ClassUnit fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassUnit classUnit = new ClassUnit();
        classUnit.setId(id);
        return classUnit;
    }
}
