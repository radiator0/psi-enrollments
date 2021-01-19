package com.psi.service.mapper;


import com.psi.domain.ClassSchedule;
import com.psi.service.dto.RecurringScheduleElementDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ClassSchedule} and its DTO {@link RecurringScheduleElementDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClassScheduleMapper.class, RoomMapper.class})
public interface RecurringScheduleElementMapper extends EntityMapper<RecurringScheduleElementDTO, ClassSchedule> {
    @Mapping(source = "startTime", target = "startDate")
    @Mapping(source = "endTime", target = "endDate")
    @Mapping(source = "classGroup.course.name", target = "courseName")
    @Mapping(source = "classGroup.course.shortName", target = "courseShortName")
    @Mapping(source = "classGroup.course.form", target = "classType")
    @Mapping(source = "room.number", target = "room")
    @Mapping(source = "room.building.name", target = "building")
    @Mapping(source = "lecturer.title", target = "lecturerTitle")
    @Mapping(source = "lecturer.firstName", target = "lecturerFirstName")
    @Mapping(source = "lecturer.secondName", target = "lecturerSecondName")
    @Mapping(source = "lecturer.lastName", target = "lecturerLastName")
    RecurringScheduleElementDTO toDto(ClassSchedule classSchedule);

    default ClassSchedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setId(id);
        return classSchedule;
    }
}
