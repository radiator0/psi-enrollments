package com.psi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A EnrollmentRight.
 */
@Entity
@Table(name = "enrollment_right")
public class EnrollmentRight implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "enrollmentRights", allowSetters = true)
    private EnrollmentDate enrollmentDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "enrollmentRights", allowSetters = true)
    private Specialty specialty;

    @ManyToOne
    @JsonIgnoreProperties(value = "enrollmentRights", allowSetters = true)
    private Student student;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public EnrollmentRight startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public EnrollmentDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public EnrollmentRight enrollmentDate(EnrollmentDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
        return this;
    }

    public void setEnrollmentDate(EnrollmentDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public EnrollmentRight specialty(Specialty specialty) {
        this.specialty = specialty;
        return this;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public Student getStudent() {
        return student;
    }

    public EnrollmentRight student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnrollmentRight)) {
            return false;
        }
        return id != null && id.equals(((EnrollmentRight) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnrollmentRight{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            "}";
    }
}
