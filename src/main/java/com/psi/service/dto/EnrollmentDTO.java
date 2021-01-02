package com.psi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.psi.domain.Enrollment} entity.
 */
public class EnrollmentDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    private Boolean isAdministrative;


    private Long studentId;

    private Long classGroupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean isIsAdministrative() {
        return isAdministrative;
    }

    public void setIsAdministrative(Boolean isAdministrative) {
        this.isAdministrative = isAdministrative;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(Long classGroupId) {
        this.classGroupId = classGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EnrollmentDTO enrollmentDTO = (EnrollmentDTO) o;
        if (enrollmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enrollmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EnrollmentDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", isAdministrative='" + isIsAdministrative() + "'" +
            ", student=" + getStudentId() +
            ", classGroup=" + getClassGroupId() +
            "}";
    }
}
