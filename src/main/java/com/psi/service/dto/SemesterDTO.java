package com.psi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.psi.domain.enumeration.SemesterType;

/**
 * A DTO for the {@link com.psi.domain.Semester} entity.
 */
public class SemesterDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer number;

    @NotNull
    private SemesterType semesterType;

    @NotNull
    private LocalDate startDate;


    private Long fieldOfStudyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public SemesterType getSemesterType() {
        return semesterType;
    }

    public void setSemesterType(SemesterType semesterType) {
        this.semesterType = semesterType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Long getFieldOfStudyId() {
        return fieldOfStudyId;
    }

    public void setFieldOfStudyId(Long fieldOfStudyId) {
        this.fieldOfStudyId = fieldOfStudyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SemesterDTO semesterDTO = (SemesterDTO) o;
        if (semesterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), semesterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SemesterDTO{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", semesterType='" + getSemesterType() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", fieldOfStudy=" + getFieldOfStudyId() +
            "}";
    }
}
