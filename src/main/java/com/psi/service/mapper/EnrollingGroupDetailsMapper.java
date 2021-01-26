package com.psi.service.mapper;


import com.psi.domain.*;
import com.psi.service.dto.EnrollingGroupDetailsDTO;
import com.psi.service.dto.RequestOverLimitDTO;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link ClassGroup} and its DTO {@link EnrollingGroupDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {RecurringScheduleElementMapper.class})
public interface EnrollingGroupDetailsMapper extends EntityMapper<EnrollingGroupDetailsDTO, ClassGroup> {


    default EnrollingGroupDetailsDTO toDto(ClassGroup group, Student student) {
        EnrollingGroupDetailsDTO enrollingGroupDetailsDTO = new EnrollingGroupDetailsDTO();
        enrollingGroupDetailsDTO.setId(group.getId());
        enrollingGroupDetailsDTO.setGroupCode(group.getCode());
        enrollingGroupDetailsDTO.setEnrolledCount(group.getEnrollments().size());
        enrollingGroupDetailsDTO.setLimit(group.getPeopleLimit());
        enrollingGroupDetailsDTO.setCanEnrollOverLimit(group.isIsEnrollmentAboveLimitAllowed());
        enrollingGroupDetailsDTO.setStudentEnrolled(group.getEnrollments().stream().anyMatch(e -> e.getStudent().equals(student)));

        Request request = group.getRequests().stream().filter(r -> r.getStudent().equals(student)).findFirst().orElse(null);
        if(request != null) {
            RequestOverLimitDTO requestOverLimitDTO = new RequestOverLimitDTO();
            requestOverLimitDTO.setId(request.getId());
            requestOverLimitDTO.setExamined(request.isIsExamined());
            requestOverLimitDTO.setAccepted(request.isIsExamined());    // TODO XD

            enrollingGroupDetailsDTO.setRequestOverLimitDTO(requestOverLimitDTO);
        }

        RecurringScheduleElementMapper recurringScheduleElementMapper = new RecurringScheduleElementMapperImpl();
        enrollingGroupDetailsDTO.setSchedules(group.getClassSchedules().stream()
            .map(recurringScheduleElementMapper::toDto)
            .collect(Collectors.toSet())
        );

        if(group.getMainLecturer() != null) {
            enrollingGroupDetailsDTO.setLecturerTitle(group.getMainLecturer().getTitle());
            enrollingGroupDetailsDTO.setLecturerFirstName(group.getMainLecturer().getInternalUser().getFirstName());
            enrollingGroupDetailsDTO.setLecturerSecondName(group.getMainLecturer().getInternalUser().getSecondName());
            enrollingGroupDetailsDTO.setLecturerLastName(group.getMainLecturer().getInternalUser().getLastName());
        }

        return enrollingGroupDetailsDTO;
    }

    default ClassGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        ClassGroup classGroup = new ClassGroup();
        classGroup.setId(id);
        return classGroup;
    }
}
