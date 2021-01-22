package com.psi.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.psi.domain.EnrollmentUnit} entity.
 */
public class EnrollmentUnitDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;


    private Long enrollmentDateId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
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
        if (!(o instanceof EnrollmentUnitDTO)) {
            return false;
        }

        return id != null && id.equals(((EnrollmentUnitDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnrollmentUnitDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", enrollmentDateId=" + getEnrollmentDateId() +
            "}";
    }
}
