package com.psi.service.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO for the {@link com.psi.domain.EnrollmentRight} entity.
 */
public class EnrollmentRightDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Boolean isPreEnrollment;

    @NotNull
    private Instant enrollmentsStartDate;

    @NotNull
    private Instant enrollmentsEndDate;

    @NotNull
    private Set<EnrollmentUnitDetailsDTO> enrollmentUnits;

    @NotNull
    private String fieldOfStudy;

    @NotNull
    private Integer semester;

    @NotNull
    private Instant rightStartDate;

    private String rightSpecialty;


    public Boolean isIsPreEnrollment() {
        return isPreEnrollment;
    }

    public void setIsPreEnrollment(Boolean isPreEnrollment) {
        this.isPreEnrollment = isPreEnrollment;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Instant getRightStartDate() {
        return rightStartDate;
    }

    public void setRightStartDate(Instant rightStartDate) {
        this.rightStartDate = rightStartDate;
    }

    public String getRightSpecialty() {
        return rightSpecialty;
    }

    public void setRightSpecialty(String rightSpecialty) {
        this.rightSpecialty = rightSpecialty;
    }

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

    public Instant getEnrollmentsStartDate() {
        return enrollmentsStartDate;
    }

    public void setEnrollmentsStartDate(Instant enrollmentsStartDate) {
        this.enrollmentsStartDate = enrollmentsStartDate;
    }

    public Instant getEnrollmentsEndDate() {
        return enrollmentsEndDate;
    }

    public void setEnrollmentsEndDate(Instant enrollmentsEndDate) {
        this.enrollmentsEndDate = enrollmentsEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnrollmentRightDetailsDTO)) {
            return false;
        }

        return id != null && id.equals(((EnrollmentRightDetailsDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnrollmentDateDetailsDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", isPreEnrollment=" + isPreEnrollment +
            ", startDate=" + enrollmentsStartDate +
            ", endDate=" + enrollmentsEndDate +
            ", enrollmentUnits =" + enrollmentUnits +
            ", fieldOfStudy='" + fieldOfStudy + '\'' +
            ", semester=" + semester +
            ", rightStartDate=" + rightStartDate +
            ", rightSpecialty='" + rightSpecialty + '\'' +
            '}';
    }

    public Set<EnrollmentUnitDetailsDTO> getEnrollmentUnits() {
        return enrollmentUnits;
    }

    public void setEnrollmentUnits(Set<EnrollmentUnitDetailsDTO> enrollmentUnits) {
        this.enrollmentUnits = enrollmentUnits;
    }
}
