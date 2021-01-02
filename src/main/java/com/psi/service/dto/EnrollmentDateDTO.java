package com.psi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.psi.domain.EnrollmentDate} entity.
 */
public class EnrollmentDateDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Boolean isPreEnrollment;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;


    private Long semesterId;

    private Long courseId;

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

    public Boolean isIsPreEnrollment() {
        return isPreEnrollment;
    }

    public void setIsPreEnrollment(Boolean isPreEnrollment) {
        this.isPreEnrollment = isPreEnrollment;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnrollmentDateDTO enrollmentDateDTO = (EnrollmentDateDTO) o;
        if (enrollmentDateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enrollmentDateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnrollmentDateDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isPreEnrollment='" + isIsPreEnrollment() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", semester=" + getSemesterId() +
            ", course=" + getCourseId() +
            "}";
    }
}
