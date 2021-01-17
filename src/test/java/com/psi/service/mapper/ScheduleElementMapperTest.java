package com.psi.service.mapper;

import com.psi.domain.*;
import com.psi.domain.enumeration.ClassType;
import com.psi.service.dto.ScheduleElementDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ScheduleElementMapperTest {

    private ScheduleElementMapper scheduleElementMapper;

    @BeforeEach
    public void setUp() {
        scheduleElementMapper = new ScheduleElementMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(scheduleElementMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(scheduleElementMapper.fromId(null)).isNull();
    }

    @Test
    public void testEntityToDTOMappingWithFullInfo() {
        Building building = new Building();
        building.setId(500L);
        building.setName("building name");

        Room room = new Room();
        room.setId(300L);
        room.setBuilding(building);

        Lecturer lecturer = new Lecturer();
        lecturer.setId(555L);
        lecturer.title("lecturer title");
        lecturer.setFirstName("lecturer first name");
        lecturer.secondName("lecturer second name");
        lecturer.setLastName("lecturer last name");

        Course course = new Course();
        course.setId(222L);
        course.setName("course name");
        course.setShortName("course short name");
        course.setForm(ClassType.Project);

        ClassGroup classGroup = new ClassGroup();
        classGroup.setId(200L);
        classGroup.setLecturer(lecturer);
        classGroup.setCourse(course);

        ClassUnit classUnit = new ClassUnit();
        classUnit.setId(1L);
        classUnit.setDay(LocalDate.of(2000, 10, 5));
        classUnit.setStartTime(Instant.ofEpochSecond(50000));
        classUnit.setEndTime(Instant.ofEpochSecond(100000));
        classUnit.setRoom(room);
        classUnit.setClassGroup(classGroup);

        ScheduleElementDTO scheduleElementDTO = scheduleElementMapper.toDto(classUnit);

        assertThat(scheduleElementDTO.getId()).isEqualTo(classUnit.getId());
        assertThat(scheduleElementDTO.getDay()).isEqualTo(classUnit.getDay());
        assertThat(scheduleElementDTO.getStartTime()).isEqualTo(classUnit.getStartTime());
        assertThat(scheduleElementDTO.getEndTime()).isEqualTo(classUnit.getEndTime());
        assertThat(scheduleElementDTO.getCourseName()).isEqualTo(course.getName());
        assertThat(scheduleElementDTO.getCourseShortName()).isEqualTo(course.getShortName());
        assertThat(scheduleElementDTO.getClassType()).isEqualTo(course.getForm());
        assertThat(scheduleElementDTO.getRoom()).isEqualTo(room.getNumber());
        assertThat(scheduleElementDTO.getBuilding()).isEqualTo(building.getName());
        assertThat(scheduleElementDTO.getLecturerTitle()).isEqualTo(lecturer.getTitle());
        assertThat(scheduleElementDTO.getLecturerFirstName()).isEqualTo(lecturer.getFirstName());
        assertThat(scheduleElementDTO.getLecturerSecondName()).isEqualTo(lecturer.getSecondName());
        assertThat(scheduleElementDTO.getLecturerLastName()).isEqualTo(lecturer.getLastName());
    }

    @Test
    public void testEntityToDTOMappingWithMinimumInfo() {
        Course course = new Course();
        course.setId(222L);
        course.setName("course name");
        course.setForm(ClassType.Laboratory);

        ClassGroup classGroup = new ClassGroup();
        classGroup.setId(200L);
        classGroup.setCourse(course);

        ClassUnit classUnit = new ClassUnit();
        classUnit.setId(1L);
        classUnit.setDay(LocalDate.of(2000, 10, 5));
        classUnit.setStartTime(Instant.ofEpochSecond(50000));
        classUnit.setEndTime(Instant.ofEpochSecond(100000));
        classUnit.setClassGroup(classGroup);

        ScheduleElementDTO scheduleElementDTO = scheduleElementMapper.toDto(classUnit);

        assertThat(scheduleElementDTO.getId()).isEqualTo(classUnit.getId());
        assertThat(scheduleElementDTO.getDay()).isEqualTo(classUnit.getDay());
        assertThat(scheduleElementDTO.getStartTime()).isEqualTo(classUnit.getStartTime());
        assertThat(scheduleElementDTO.getEndTime()).isEqualTo(classUnit.getEndTime());
        assertThat(scheduleElementDTO.getCourseName()).isEqualTo(course.getName());
        assertThat(scheduleElementDTO.getCourseShortName()).isEqualTo(course.getShortName());
        assertThat(scheduleElementDTO.getClassType()).isEqualTo(course.getForm());
        assertThat(scheduleElementDTO.getRoom()).isEqualTo(null);
        assertThat(scheduleElementDTO.getBuilding()).isEqualTo(null);
        assertThat(scheduleElementDTO.getLecturerTitle()).isEqualTo(null);
        assertThat(scheduleElementDTO.getLecturerFirstName()).isEqualTo(null);
        assertThat(scheduleElementDTO.getLecturerSecondName()).isEqualTo(null);
        assertThat(scheduleElementDTO.getLecturerLastName()).isEqualTo(null);
    }
}
