package com.psi.service.dto;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.psi.domain.CourseUnit} entity.
 */
public class CourseUnitDetailsDTO implements Serializable {

    private Long id;

    private String code;

    @Min(value = 0)
    private Integer ects;

    private Boolean isGroupOfCourses;

    private Boolean isStream;

    private Set<CourseDetailsDTO> courses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean isIsGroupOfCourses() {
        return isGroupOfCourses;
    }

    public void setIsGroupOfCourses(Boolean isGroupOfCourses) {
        this.isGroupOfCourses = isGroupOfCourses;
    }

    public Boolean isIsStream() {
        return isStream;
    }

    public void setIsStream(Boolean isStream) {
        this.isStream = isStream;
    }

    public Set<CourseDetailsDTO> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseDetailsDTO> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseUnitDetailsDTO)) {
            return false;
        }

        return id != null && id.equals(((CourseUnitDetailsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseUnitDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", ects=" + getEcts() +
            ", isGroupOfCourses='" + isIsGroupOfCourses() + "'" +
            ", isStream='" + isIsStream() + "'" +
            ", courses='" + getCourses() + "'" +
            "}";
    }
}
