package com.psi.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A FieldOfStudy.
 */
@Entity
@Table(name = "field_of_study")
public class FieldOfStudy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "unique_name", nullable = false, unique = true)
    private String uniqueName;

    @OneToMany(mappedBy = "fieldOfStudy")
    private Set<Semester> semesters = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("fieldOfStudies")
    private StudyProgram studyProgram;

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

    public FieldOfStudy name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public FieldOfStudy uniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
        return this;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public Set<Semester> getSemesters() {
        return semesters;
    }

    public FieldOfStudy semesters(Set<Semester> semesters) {
        this.semesters = semesters;
        return this;
    }

    public FieldOfStudy addSemester(Semester semester) {
        this.semesters.add(semester);
        semester.setFieldOfStudy(this);
        return this;
    }

    public FieldOfStudy removeSemester(Semester semester) {
        this.semesters.remove(semester);
        semester.setFieldOfStudy(null);
        return this;
    }

    public void setSemesters(Set<Semester> semesters) {
        this.semesters = semesters;
    }

    public StudyProgram getStudyProgram() {
        return studyProgram;
    }

    public FieldOfStudy studyProgram(StudyProgram studyProgram) {
        this.studyProgram = studyProgram;
        return this;
    }

    public void setStudyProgram(StudyProgram studyProgram) {
        this.studyProgram = studyProgram;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldOfStudy)) {
            return false;
        }
        return id != null && id.equals(((FieldOfStudy) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FieldOfStudy{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", uniqueName='" + getUniqueName() + "'" +
            "}";
    }
}
