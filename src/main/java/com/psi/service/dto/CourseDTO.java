package com.psi.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.psi.domain.enumeration.ClassType;

/**
 * A DTO for the {@link com.psi.domain.Course} entity.
 */
public class CourseDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String shortName;

    @NotNull
    private String code;

    @Min(value = 0)
    private Integer ects;

    @NotNull
    private ClassType form;

    private Set<SpecialtyDTO> specialties = new HashSet<>();

    private Long enrollmentDateId;

    private Long courseUnitId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getEcts() {
        return ects;
    }

    public void setEcts(Integer ects) {
        this.ects = ects;
    }

    public ClassType getForm() {
        return form;
    }

    public void setForm(ClassType form) {
        this.form = form;
    }

    public Set<SpecialtyDTO> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Set<SpecialtyDTO> specialties) {
        this.specialties = specialties;
    }

    public Long getEnrollmentDateId() {
        return enrollmentDateId;
    }

    public void setEnrollmentDateId(Long enrollmentDateId) {
        this.enrollmentDateId = enrollmentDateId;
    }

    public Long getCourseUnitId() {
        return courseUnitId;
    }

    public void setCourseUnitId(Long courseUnitId) {
        this.courseUnitId = courseUnitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseDTO)) {
            return false;
        }

        return id != null && id.equals(((CourseDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", code='" + getCode() + "'" +
            ", ects=" + getEcts() +
            ", form='" + getForm() + "'" +
            ", specialties='" + getSpecialties() + "'" +
            ", enrollmentDateId=" + getEnrollmentDateId() +
            ", courseUnitId=" + getCourseUnitId() +
            "}";
    }
}
