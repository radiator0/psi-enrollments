package com.psi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A EnrollmentDate.
 */
@Entity
@Table(name = "enrollment_date")
public class EnrollmentDate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "is_pre_enrollment", nullable = false)
    private Boolean isPreEnrollment;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @OneToMany(mappedBy = "enrollmentDate")
    private Set<EnrollmentUnit> enrollmentUnits = new HashSet<>();

    @OneToMany(mappedBy = "enrollmentDate")
    private Set<EnrollmentRight> enrollmentRights = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "enrollmentDates", allowSetters = true)
    private Semester semester;

    @ManyToOne
    @JsonIgnoreProperties(value = "enrollmentDates", allowSetters = true)
    private Course course;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public EnrollmentDate name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsPreEnrollment() {
        return isPreEnrollment;
    }

    public EnrollmentDate isPreEnrollment(Boolean isPreEnrollment) {
        this.isPreEnrollment = isPreEnrollment;
        return this;
    }

    public void setIsPreEnrollment(Boolean isPreEnrollment) {
        this.isPreEnrollment = isPreEnrollment;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public EnrollmentDate startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public EnrollmentDate endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<EnrollmentUnit> getEnrollmentUnits() {
        return enrollmentUnits;
    }

    public EnrollmentDate enrollmentUnits(Set<EnrollmentUnit> enrollmentUnits) {
        this.enrollmentUnits = enrollmentUnits;
        return this;
    }

    public EnrollmentDate addEnrollmentUnit(EnrollmentUnit enrollmentUnit) {
        this.enrollmentUnits.add(enrollmentUnit);
        enrollmentUnit.setEnrollmentDate(this);
        return this;
    }

    public EnrollmentDate removeEnrollmentUnit(EnrollmentUnit enrollmentUnit) {
        this.enrollmentUnits.remove(enrollmentUnit);
        enrollmentUnit.setEnrollmentDate(null);
        return this;
    }

    public void setEnrollmentUnits(Set<EnrollmentUnit> enrollmentUnits) {
        this.enrollmentUnits = enrollmentUnits;
    }

    public Set<EnrollmentRight> getEnrollmentRights() {
        return enrollmentRights;
    }

    public EnrollmentDate enrollmentRights(Set<EnrollmentRight> enrollmentRights) {
        this.enrollmentRights = enrollmentRights;
        return this;
    }

    public EnrollmentDate addEnrollmentRight(EnrollmentRight enrollmentRight) {
        this.enrollmentRights.add(enrollmentRight);
        enrollmentRight.setEnrollmentDate(this);
        return this;
    }

    public EnrollmentDate removeEnrollmentRight(EnrollmentRight enrollmentRight) {
        this.enrollmentRights.remove(enrollmentRight);
        enrollmentRight.setEnrollmentDate(null);
        return this;
    }

    public void setEnrollmentRights(Set<EnrollmentRight> enrollmentRights) {
        this.enrollmentRights = enrollmentRights;
    }

    public Semester getSemester() {
        return semester;
    }

    public EnrollmentDate semester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Course getCourse() {
        return course;
    }

    public EnrollmentDate course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EnrollmentDate)) {
            return false;
        }
        return id != null && id.equals(((EnrollmentDate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnrollmentDate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", isPreEnrollment='" + isIsPreEnrollment() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
