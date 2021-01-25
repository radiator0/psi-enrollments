package com.psi.service.mapper;


import com.psi.domain.*;
import com.psi.service.dto.CourseDetailsDTO;
import com.psi.service.dto.CourseUnitDetailsDTO;
import com.psi.service.dto.SelectableModuleDetailsDTO;
import org.mapstruct.Mapper;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link Course} and its DTO {@link SelectableModuleDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SelectableModuleDetailsMapper extends EntityMapper<SelectableModuleDetailsDTO, Course> {


    default List<SelectableModuleDetailsDTO> toDtos(List<Course> courses, Student student) {
        Function<Course, CourseDetailsDTO> courseToDto = (course) -> {
            CourseDetailsDTO courseDetailsDTO = new CourseDetailsDTO();
            courseDetailsDTO.setId(course.getId());
            courseDetailsDTO.setName(course.getName());
            courseDetailsDTO.setShortName(course.getShortName());
            courseDetailsDTO.setCode(course.getCode());
            courseDetailsDTO.setEcts(course.getEcts());
            courseDetailsDTO.setForm(course.getForm());
            courseDetailsDTO.setSpecialities(course.getSpecialties() == null ? null :
                course.getSpecialties().stream().map(Specialty::getName).collect(Collectors.toSet()));
            courseDetailsDTO.setStudentEnrolled(course.getClassGroups().stream()
                .anyMatch(cg -> cg.getEnrollments().stream().anyMatch(e -> e.getStudent().equals(student)))
            );
            return courseDetailsDTO;
        };

        Function<CourseUnit, CourseUnitDetailsDTO> courseUnitToDto = (courseUnit) -> {
            CourseUnitDetailsDTO courseUnitDetailsDTO = new CourseUnitDetailsDTO();
            courseUnitDetailsDTO.setId(courseUnit.getId());
            courseUnitDetailsDTO.setCode(courseUnit.getCode());
            courseUnitDetailsDTO.setEcts(courseUnit.getEcts());
            courseUnitDetailsDTO.setIsGroupOfCourses(courseUnit.isIsGroupOfCourses());
            courseUnitDetailsDTO.setIsStream(courseUnit.isIsStream());
            courseUnitDetailsDTO.setCourses(courseUnit.getCourses().stream()
                .map(courseToDto).collect(Collectors.toSet()));
            return courseUnitDetailsDTO;
        };

        Function<SelectableModule, SelectableModuleDetailsDTO> selectableModuleToDto = (selectableModule) -> {
            SelectableModuleDetailsDTO selectableModuleDetailsDTO = new SelectableModuleDetailsDTO();
            selectableModuleDetailsDTO.setId(selectableModule.getId());
            selectableModuleDetailsDTO.setName(selectableModule.getName());
            selectableModuleDetailsDTO.setCourseUnits(selectableModule.getCourseUnits().stream()
                .map(courseUnitToDto).collect(Collectors.toSet()));
            return selectableModuleDetailsDTO;
        };

        Map<Optional<CourseUnit>, List<Course>> courseUnitCourseMap = courses.stream()
            .collect(Collectors.groupingBy(c -> Optional.ofNullable(c.getCourseUnit())));

        Map<Optional<SelectableModule>, List<CourseUnit>> selectableModuleListMap = courseUnitCourseMap.keySet().stream()
            .filter(Optional::isPresent).map(Optional::get)
            .collect(Collectors.groupingBy(cu -> Optional.ofNullable(cu.getSelectableModule())));


        List<SelectableModuleDetailsDTO> selectableModuleDetailsDTOS = new ArrayList<>();

        selectableModuleDetailsDTOS.addAll(
            selectableModuleListMap.keySet().stream().filter(Optional::isPresent)
                .map(sm -> selectableModuleToDto.apply(sm.get())).collect(Collectors.toList())
        );

        selectableModuleDetailsDTOS.addAll(
            selectableModuleListMap.getOrDefault(Optional.ofNullable(null), new ArrayList<>()).stream()
            .map(cu -> {
                SelectableModuleDetailsDTO selectableModuleDetailsDTO = new SelectableModuleDetailsDTO();
                selectableModuleDetailsDTO.setCourseUnits(new HashSet<>(Collections.singletonList(courseUnitToDto.apply(cu))));
                return selectableModuleDetailsDTO;
            }).collect(Collectors.toList())
        );

        selectableModuleDetailsDTOS.addAll(
            courseUnitCourseMap.getOrDefault(Optional.ofNullable(null), new ArrayList<>()).stream()
            .map(c -> {
                CourseUnitDetailsDTO courseUnitDetailsDTO = new CourseUnitDetailsDTO();
                courseUnitDetailsDTO.setCourses(new HashSet<>(Collections.singletonList(courseToDto.apply(c))));

                SelectableModuleDetailsDTO selectableModuleDetailsDTO = new SelectableModuleDetailsDTO();
                selectableModuleDetailsDTO.setCourseUnits(new HashSet<>(Collections.singletonList(courseUnitDetailsDTO)));
                return selectableModuleDetailsDTO;
            }).collect(Collectors.toList())
        );

        return selectableModuleDetailsDTOS;
    }

    default Course fromId(Long id) {
        if (id == null) {
            return null;
        }
        Course course = new Course();
        course.setId(id);
        return course;
    }
}
