package com.psi.service.dto;

import com.psi.domain.enumeration.ClassType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link com.psi.domain.Course} entity.
 */
public class CourseDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String shortName;

    @NotNull
    private String code;

    @NotNull
    @Min(value = 0)
    private Integer ects;

    @NotNull
    private ClassType form;

    private Set<String> specialities = new HashSet<>();

    private Boolean isStudentEnrolled;

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

    public Set<String> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(Set<String> specialities) {
        this.specialities = specialities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseDetailsDTO)) {
            return false;
        }

        return id != null && id.equals(((CourseDetailsDTO) o).id);
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
            ", specialties='" + getSpecialities() + "'" +
            ", isStudentEnrolled='" + getStudentEnrolled() + "'" +
            "}";
    }

    public Boolean getStudentEnrolled() {
        return isStudentEnrolled;
    }

    public void setStudentEnrolled(Boolean studentEnrolled) {
        isStudentEnrolled = studentEnrolled;
    }
}
