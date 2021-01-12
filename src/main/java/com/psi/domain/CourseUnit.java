package com.psi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CourseUnit.
 */
@Entity
@Table(name = "course_unit")
public class CourseUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "code")
    private String code;

    @Min(value = 0)
    @Column(name = "ects")
    private Integer ects;

    @NotNull
    @Column(name = "is_group_of_courses", nullable = false)
    private Boolean isGroupOfCourses;

    @NotNull
    @Column(name = "is_stream", nullable = false)
    private Boolean isStream;

    @NotNull
    @Column(name = "is_selectable", nullable = false)
    private Boolean isSelectable;

    @OneToMany(mappedBy = "courseUnit")
    private Set<Course> courses = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "courseUnits", allowSetters = true)
    private SelectableModule selectableModule;

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

    public CourseUnit code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getEcts() {
        return ects;
    }

    public CourseUnit ects(Integer ects) {
        this.ects = ects;
        return this;
    }

    public void setEcts(Integer ects) {
        this.ects = ects;
    }

    public Boolean isIsGroupOfCourses() {
        return isGroupOfCourses;
    }

    public CourseUnit isGroupOfCourses(Boolean isGroupOfCourses) {
        this.isGroupOfCourses = isGroupOfCourses;
        return this;
    }

    public void setIsGroupOfCourses(Boolean isGroupOfCourses) {
        this.isGroupOfCourses = isGroupOfCourses;
    }

    public Boolean isIsStream() {
        return isStream;
    }

    public CourseUnit isStream(Boolean isStream) {
        this.isStream = isStream;
        return this;
    }

    public void setIsStream(Boolean isStream) {
        this.isStream = isStream;
    }

    public Boolean isIsSelectable() {
        return isSelectable;
    }

    public CourseUnit isSelectable(Boolean isSelectable) {
        this.isSelectable = isSelectable;
        return this;
    }

    public void setIsSelectable(Boolean isSelectable) {
        this.isSelectable = isSelectable;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public CourseUnit courses(Set<Course> courses) {
        this.courses = courses;
        return this;
    }

    public CourseUnit addCourse(Course course) {
        this.courses.add(course);
        course.setCourseUnit(this);
        return this;
    }

    public CourseUnit removeCourse(Course course) {
        this.courses.remove(course);
        course.setCourseUnit(null);
        return this;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public SelectableModule getSelectableModule() {
        return selectableModule;
    }

    public CourseUnit selectableModule(SelectableModule selectableModule) {
        this.selectableModule = selectableModule;
        return this;
    }

    public void setSelectableModule(SelectableModule selectableModule) {
        this.selectableModule = selectableModule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CourseUnit)) {
            return false;
        }
        return id != null && id.equals(((CourseUnit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CourseUnit{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", ects=" + getEcts() +
            ", isGroupOfCourses='" + isIsGroupOfCourses() + "'" +
            ", isStream='" + isIsStream() + "'" +
            ", isSelectable='" + isIsSelectable() + "'" +
            "}";
    }
}
