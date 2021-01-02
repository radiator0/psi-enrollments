package com.psi.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Specialty.
 */
@Entity
@Table(name = "specialty")
public class Specialty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "specialty")
    private Set<EnrollmentRight> enrollmentRights = new HashSet<>();

    @ManyToMany(mappedBy = "specialties")
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Specialty name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EnrollmentRight> getEnrollmentRights() {
        return enrollmentRights;
    }

    public Specialty enrollmentRights(Set<EnrollmentRight> enrollmentRights) {
        this.enrollmentRights = enrollmentRights;
        return this;
    }

    public Specialty addEnrollmentRight(EnrollmentRight enrollmentRight) {
        this.enrollmentRights.add(enrollmentRight);
        enrollmentRight.setSpecialty(this);
        return this;
    }

    public Specialty removeEnrollmentRight(EnrollmentRight enrollmentRight) {
        this.enrollmentRights.remove(enrollmentRight);
        enrollmentRight.setSpecialty(null);
        return this;
    }

    public void setEnrollmentRights(Set<EnrollmentRight> enrollmentRights) {
        this.enrollmentRights = enrollmentRights;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public Specialty courses(Set<Course> courses) {
        this.courses = courses;
        return this;
    }

    public Specialty addCourse(Course course) {
        this.courses.add(course);
        course.getSpecialties().add(this);
        return this;
    }

    public Specialty removeCourse(Course course) {
        this.courses.remove(course);
        course.getSpecialties().remove(this);
        return this;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Specialty)) {
            return false;
        }
        return id != null && id.equals(((Specialty) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Specialty{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
