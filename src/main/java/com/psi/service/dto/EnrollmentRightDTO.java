package com.psi.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.psi.domain.EnrollmentRight} entity.
 */
public class EnrollmentRightDTO implements Serializable {
    
    private Long id;

    @NotNull
    private LocalDate startDate;


    private Long enrollmentDateId;

    private Long specialtyId;

    private Long studentId;
    
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

    public Long getEnrollmentDateId() {
        return enrollmentDateId;
    }

    public void setEnrollmentDateId(Long enrollmentDateId) {
        this.enrollmentDateId = enrollmentDateId;
    }

    public Long getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Long specialtyId) {
        this.specialtyId = specialtyId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnrollmentRightDTO)) {
            return false;
        }

        return id != null && id.equals(((EnrollmentRightDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnrollmentRightDTO{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", enrollmentDateId=" + getEnrollmentDateId() +
            ", specialtyId=" + getSpecialtyId() +
            ", studentId=" + getStudentId() +
            "}";
    }
}
