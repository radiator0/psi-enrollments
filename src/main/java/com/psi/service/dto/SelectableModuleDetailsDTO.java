package com.psi.service.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link com.psi.domain.SelectableModule} entity.
 */
public class SelectableModuleDetailsDTO implements Serializable {

    private Long id;

    private String name;

    private Set<CourseUnitDetailsDTO> courseUnits;


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

    public Set<CourseUnitDetailsDTO> getCourseUnits() {
        return courseUnits;
    }

    public void setCourseUnits(Set<CourseUnitDetailsDTO> courseUnits) {
        this.courseUnits = courseUnits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SelectableModuleDetailsDTO)) {
            return false;
        }

        return id != null && id.equals(((SelectableModuleDetailsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SelectableModuleDetailsDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", courseUnits=" + courseUnits +
            '}';
    }
}
