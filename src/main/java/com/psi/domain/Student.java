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

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "student")
    private Set<Request> requests = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<EnrollmentRight> enrollmentRights = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<Enrollment> enrollments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Student firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public Student secondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public Student lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public Student mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTitle() {
        return title;
    }

    public Student title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
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
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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

    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", secondName='" + getSecondName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", mail='" + getMail() + "'" +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
