package com.psi.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @OneToMany(mappedBy = "student")
    private Set<Request> requests = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<EnrollmentRight> enrollmentRights = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<Enrollment> enrollments = new HashSet<>();

    @OneToOne
    @JoinColumn(referencedColumnName = "login", name="login")
    private User internalUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getInternalUser() {
        return internalUser;
    }

    public Student internalUser(User internalUser) {
        this.internalUser = internalUser;
        return this;
    }

    public void setInternalUser(User internalUser) {
        this.internalUser = internalUser;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public Student requests(Set<Request> requests) {
        this.requests = requests;
        return this;
    }

    public Student addRequest(Request request) {
        this.requests.add(request);
        request.setStudent(this);
        return this;
    }

    public Student removeRequest(Request request) {
        this.requests.remove(request);
        request.setStudent(null);
        return this;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    public Set<EnrollmentRight> getEnrollmentRights() {
        return enrollmentRights;
    }

    public Student enrollmentRights(Set<EnrollmentRight> enrollmentRights) {
        this.enrollmentRights = enrollmentRights;
        return this;
    }

    public Student addEnrollmentRight(EnrollmentRight enrollmentRight) {
        this.enrollmentRights.add(enrollmentRight);
        enrollmentRight.setStudent(this);
        return this;
    }

    public Student removeEnrollmentRight(EnrollmentRight enrollmentRight) {
        this.enrollmentRights.remove(enrollmentRight);
        enrollmentRight.setStudent(null);
        return this;
    }

    public void setEnrollmentRights(Set<EnrollmentRight> enrollmentRights) {
        this.enrollmentRights = enrollmentRights;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public Student enrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
        return this;
    }

    public Student addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
        enrollment.setStudent(this);
        return this;
    }

    public Student removeEnrollment(Enrollment enrollment) {
        this.enrollments.remove(enrollment);
        enrollment.setStudent(null);
        return this;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", internalUser='" + getInternalUser() + "'" +
            "}";
    }
}
