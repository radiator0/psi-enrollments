package com.psi.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

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
    private Instant startDate;

    @NotNull
    private Instant endDate;


    private Long semesterId;

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

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnrollmentDateDTO)) {
            return false;
        }

        return id != null && id.equals(((EnrollmentDateDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnrollmentDateDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isPreEnrollment='" + isIsPreEnrollment() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", semesterId=" + getSemesterId() +
            "}";
    }
}
