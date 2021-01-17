package com.psi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A ClassGroup.
 */
@Entity
@Table(name = "class_group")
public class ClassGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "is_enrollment_above_limit_allowed", nullable = false)
    private Boolean isEnrollmentAboveLimitAllowed;

    @NotNull
    @Min(value = 0)
    @Column(name = "people_limit", nullable = false)
    private Integer peopleLimit;

    @NotNull
    @Min(value = 0)
    @Column(name = "enrolled_count", nullable = false)
    private Integer enrolledCount;

    @NotNull
    @Column(name = "is_full", nullable = false)
    private Boolean isFull;

    @OneToMany(mappedBy = "classGroup")
    private Set<Request> requests = new HashSet<>();

    @OneToMany(mappedBy = "classGroup")
    private Set<Enrollment> enrollments = new HashSet<>();

    @OneToMany(mappedBy = "classGroup")
    private Set<ClassUnit> classUnits = new HashSet<>();

    @OneToMany(mappedBy = "classGroup")
    private Set<ClassSchedule> classSchedules = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "classGroups", allowSetters = true)
    private Course course;

    @ManyToOne
    @JsonIgnoreProperties(value = "classGroups", allowSetters = true)
    private Lecturer mainLecturer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public ClassGroup code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isIsEnrollmentAboveLimitAllowed() {
        return isEnrollmentAboveLimitAllowed;
    }

    public ClassGroup isEnrollmentAboveLimitAllowed(Boolean isEnrollmentAboveLimitAllowed) {
        this.isEnrollmentAboveLimitAllowed = isEnrollmentAboveLimitAllowed;
        return this;
    }

    public void setIsEnrollmentAboveLimitAllowed(Boolean isEnrollmentAboveLimitAllowed) {
        this.isEnrollmentAboveLimitAllowed = isEnrollmentAboveLimitAllowed;
    }

    public Integer getPeopleLimit() {
        return peopleLimit;
    }

    public ClassGroup peopleLimit(Integer peopleLimit) {
        this.peopleLimit = peopleLimit;
        return this;
    }

    public void setPeopleLimit(Integer peopleLimit) {
        this.peopleLimit = peopleLimit;
    }

    public Integer getEnrolledCount() {
        return enrolledCount;
    }

    public ClassGroup enrolledCount(Integer enrolledCount) {
        this.enrolledCount = enrolledCount;
        return this;
    }

    public void setEnrolledCount(Integer enrolledCount) {
        this.enrolledCount = enrolledCount;
    }

    public Boolean isIsFull() {
        return isFull;
    }

    public ClassGroup isFull(Boolean isFull) {
        this.isFull = isFull;
        return this;
    }

    public void setIsFull(Boolean isFull) {
        this.isFull = isFull;
    }

    public Set<Request> getRequests() {
        return requests;
    }

    public ClassGroup requests(Set<Request> requests) {
        this.requests = requests;
        return this;
    }

    public ClassGroup addRequest(Request request) {
        this.requests.add(request);
        request.setClassGroup(this);
        return this;
    }

    public ClassGroup removeRequest(Request request) {
        this.requests.remove(request);
        request.setClassGroup(null);
        return this;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public ClassGroup enrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
        return this;
    }

    public ClassGroup addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
        enrollment.setClassGroup(this);
        return this;
    }

    public ClassGroup removeEnrollment(Enrollment enrollment) {
        this.enrollments.remove(enrollment);
        enrollment.setClassGroup(null);
        return this;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public Set<ClassUnit> getClassUnits() {
        return classUnits;
    }

    public ClassGroup classUnits(Set<ClassUnit> classUnits) {
        this.classUnits = classUnits;
        return this;
    }

    public ClassGroup addClassUnit(ClassUnit classUnit) {
        this.classUnits.add(classUnit);
        classUnit.setClassGroup(this);
        return this;
    }

    public ClassGroup removeClassUnit(ClassUnit classUnit) {
        this.classUnits.remove(classUnit);
        classUnit.setClassGroup(null);
        return this;
    }

    public void setClassUnits(Set<ClassUnit> classUnits) {
        this.classUnits = classUnits;
    }

    public Set<ClassSchedule> getClassSchedules() {
        return classSchedules;
    }

    public ClassGroup classSchedules(Set<ClassSchedule> classSchedules) {
        this.classSchedules = classSchedules;
        return this;
    }

    public ClassGroup addClassSchedule(ClassSchedule classSchedule) {
        this.classSchedules.add(classSchedule);
        classSchedule.setClassGroup(this);
        return this;
    }

    public ClassGroup removeClassSchedule(ClassSchedule classSchedule) {
        this.classSchedules.remove(classSchedule);
        classSchedule.setClassGroup(null);
        return this;
    }

    public void setClassSchedules(Set<ClassSchedule> classSchedules) {
        this.classSchedules = classSchedules;
    }

    public Course getCourse() {
        return course;
    }

    public ClassGroup course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Lecturer getMainLecturer() {
        return mainLecturer;
    }

    public ClassGroup mainLecturer(Lecturer lecturer) {
        this.mainLecturer = lecturer;
        return this;
    }

    public void setMainLecturer(Lecturer lecturer) {
        this.mainLecturer = lecturer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassGroup)) {
            return false;
        }
        return id != null && id.equals(((ClassGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassGroup{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", isEnrollmentAboveLimitAllowed='" + isIsEnrollmentAboveLimitAllowed() + "'" +
            ", peopleLimit=" + getPeopleLimit() +
            ", enrolledCount=" + getEnrolledCount() +
            ", isFull='" + isIsFull() + "'" +
            "}";
    }
}
