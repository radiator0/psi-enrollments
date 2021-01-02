package com.psi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.psi.domain.EnrollmentUnit} entity.
 */
public class EnrollmentUnitDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;


    private Long enrollmentDateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getEnrollmentDateId() {
        return enrollmentDateId;
    }

    public void setEnrollmentDateId(Long enrollmentDateId) {
        this.enrollmentDateId = enrollmentDateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnrollmentUnitDTO enrollmentUnitDTO = (EnrollmentUnitDTO) o;
        if (enrollmentUnitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enrollmentUnitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnrollmentUnitDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", enrollmentDate=" + getEnrollmentDateId() +
            "}";
    }
}
