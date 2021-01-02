package com.psi.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.psi.domain.CourseUnit} entity.
 */
public class CourseUnitDTO implements Serializable {

    private Long id;

    private String code;

    @Min(value = 0)
    private Integer ects;

    @NotNull
    private Boolean isGroupOfCourses;

    @NotNull
    private Boolean isStream;

    @NotNull
    private Boolean isSelectable;


    private Long selectableModuleId;

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

    public Boolean isIsSelectable() {
        return isSelectable;
    }

    public void setIsSelectable(Boolean isSelectable) {
        this.isSelectable = isSelectable;
    }

    public Long getSelectableModuleId() {
        return selectableModuleId;
    }

    public void setSelectableModuleId(Long selectableModuleId) {
        this.selectableModuleId = selectableModuleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CourseUnitDTO courseUnitDTO = (CourseUnitDTO) o;
        if (courseUnitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), courseUnitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CourseUnitDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", ects=" + getEcts() +
            ", isGroupOfCourses='" + isIsGroupOfCourses() + "'" +
            ", isStream='" + isIsStream() + "'" +
            ", isSelectable='" + isIsSelectable() + "'" +
            ", selectableModule=" + getSelectableModuleId() +
            "}";
    }
}
