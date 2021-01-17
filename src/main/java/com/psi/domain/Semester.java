package com.psi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.psi.domain.enumeration.SemesterType;

/**
 * A Semester.
 */
@Entity
@Table(name = "semester")
public class Semester implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "number", nullable = false)
    private Integer number;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "semester_type", nullable = false)
    private SemesterType semesterType;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @OneToMany(mappedBy = "semester")
    private Set<EnrollmentDate> enrollmentDates = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "semesters", allowSetters = true)
    private FieldOfStudy fieldOfStudy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public Semester number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public SemesterType getSemesterType() {
        return semesterType;
    }

    public Semester semesterType(SemesterType semesterType) {
        this.semesterType = semesterType;
        return this;
    }

    public void setSemesterType(SemesterType semesterType) {
        this.semesterType = semesterType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Semester startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Set<EnrollmentDate> getEnrollmentDates() {
        return enrollmentDates;
    }

    public Semester enrollmentDates(Set<EnrollmentDate> enrollmentDates) {
        this.enrollmentDates = enrollmentDates;
        return this;
    }

    public Semester addEnrollmentDate(EnrollmentDate enrollmentDate) {
        this.enrollmentDates.add(enrollmentDate);
        enrollmentDate.setSemester(this);
        return this;
    }

    public Semester removeEnrollmentDate(EnrollmentDate enrollmentDate) {
        this.enrollmentDates.remove(enrollmentDate);
        enrollmentDate.setSemester(null);
        return this;
    }

    public void setEnrollmentDates(Set<EnrollmentDate> enrollmentDates) {
        this.enrollmentDates = enrollmentDates;
    }

    public FieldOfStudy getFieldOfStudy() {
        return fieldOfStudy;
    }

    public Semester fieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
        return this;
    }

    public void setFieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Semester)) {
            return false;
        }
        return id != null && id.equals(((Semester) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Semester{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", semesterType='" + getSemesterType() + "'" +
            ", startDate='" + getStartDate() + "'" +
            "}";
    }
}
