package com.psi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.psi.domain.enumeration.ClassType;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "short_name")
    private String shortName;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Min(value = 0)
    @Column(name = "ects")
    private Integer ects;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "form", nullable = false)
    private ClassType form;

    @OneToMany(mappedBy = "course")
    private Set<ClassGroup> classGroups = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "course_specialty",
               joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "specialty_id", referencedColumnName = "id"))
    private Set<Specialty> specialties = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "courses", allowSetters = true)
    private EnrollmentDate enrollmentDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "courses", allowSetters = true)
    private CourseUnit courseUnit;

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

    public Course name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public Course shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCode() {
        return code;
    }

    public Course code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getEcts() {
        return ects;
    }

    public Course ects(Integer ects) {
        this.ects = ects;
        return this;
    }

    public void setEcts(Integer ects) {
        this.ects = ects;
    }

    public ClassType getForm() {
        return form;
    }

    public Course form(ClassType form) {
        this.form = form;
        return this;
    }

    public void setForm(ClassType form) {
        this.form = form;
    }

    public Set<ClassGroup> getClassGroups() {
        return classGroups;
    }

    public Course classGroups(Set<ClassGroup> classGroups) {
        this.classGroups = classGroups;
        return this;
    }

    public Course addClassGroup(ClassGroup classGroup) {
        this.classGroups.add(classGroup);
        classGroup.setCourse(this);
        return this;
    }

    public Course removeClassGroup(ClassGroup classGroup) {
        this.classGroups.remove(classGroup);
        classGroup.setCourse(null);
        return this;
    }

    public void setClassGroups(Set<ClassGroup> classGroups) {
        this.classGroups = classGroups;
    }

    public Set<Specialty> getSpecialties() {
        return specialties;
    }

    public Course specialties(Set<Specialty> specialties) {
        this.specialties = specialties;
        return this;
    }

    public Course addSpecialty(Specialty specialty) {
        this.specialties.add(specialty);
        specialty.getCourses().add(this);
        return this;
    }

    public Course removeSpecialty(Specialty specialty) {
        this.specialties.remove(specialty);
        specialty.getCourses().remove(this);
        return this;
    }

    public void setSpecialties(Set<Specialty> specialties) {
        this.specialties = specialties;
    }

    public EnrollmentDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public Course enrollmentDate(EnrollmentDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
        return this;
    }

    public void setEnrollmentDate(EnrollmentDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public CourseUnit getCourseUnit() {
        return courseUnit;
    }

    public Course courseUnit(CourseUnit courseUnit) {
        this.courseUnit = courseUnit;
        return this;
    }

    public void setCourseUnit(CourseUnit courseUnit) {
        this.courseUnit = courseUnit;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            ", code='" + getCode() + "'" +
            ", ects=" + getEcts() +
            ", form='" + getForm() + "'" +
            "}";
    }
}
