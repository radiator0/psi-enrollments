package com.psi.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.psi.domain.enumeration.SemesterType;

import com.psi.domain.enumeration.StudyType;

import com.psi.domain.enumeration.StudyForm;

/**
 * A StudyProgram.
 */
@Entity
@Table(name = "study_program")
public class StudyProgram implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Min(value = 0)
    @Column(name = "start_year", nullable = false)
    private Integer startYear;

    @NotNull
    @Min(value = 0)
    @Column(name = "end_year", nullable = false)
    private Integer endYear;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "starting_semester_type", nullable = false)
    private SemesterType startingSemesterType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "study_type", nullable = false)
    private StudyType studyType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "study_form", nullable = false)
    private StudyForm studyForm;

    @OneToMany(mappedBy = "studyProgram")
    private Set<FieldOfStudy> fieldOfStudies = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public StudyProgram startYear(Integer startYear) {
        this.startYear = startYear;
        return this;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public StudyProgram endYear(Integer endYear) {
        this.endYear = endYear;
        return this;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public SemesterType getStartingSemesterType() {
        return startingSemesterType;
    }

    public StudyProgram startingSemesterType(SemesterType startingSemesterType) {
        this.startingSemesterType = startingSemesterType;
        return this;
    }

    public void setStartingSemesterType(SemesterType startingSemesterType) {
        this.startingSemesterType = startingSemesterType;
    }

    public StudyType getStudyType() {
        return studyType;
    }

    public StudyProgram studyType(StudyType studyType) {
        this.studyType = studyType;
        return this;
    }

    public void setStudyType(StudyType studyType) {
        this.studyType = studyType;
    }

    public StudyForm getStudyForm() {
        return studyForm;
    }

    public StudyProgram studyForm(StudyForm studyForm) {
        this.studyForm = studyForm;
        return this;
    }

    public void setStudyForm(StudyForm studyForm) {
        this.studyForm = studyForm;
    }

    public Set<FieldOfStudy> getFieldOfStudies() {
        return fieldOfStudies;
    }

    public StudyProgram fieldOfStudies(Set<FieldOfStudy> fieldOfStudies) {
        this.fieldOfStudies = fieldOfStudies;
        return this;
    }

    public StudyProgram addFieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldOfStudies.add(fieldOfStudy);
        fieldOfStudy.setStudyProgram(this);
        return this;
    }

    public StudyProgram removeFieldOfStudy(FieldOfStudy fieldOfStudy) {
        this.fieldOfStudies.remove(fieldOfStudy);
        fieldOfStudy.setStudyProgram(null);
        return this;
    }

    public void setFieldOfStudies(Set<FieldOfStudy> fieldOfStudies) {
        this.fieldOfStudies = fieldOfStudies;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StudyProgram)) {
            return false;
        }
        return id != null && id.equals(((StudyProgram) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StudyProgram{" +
            "id=" + getId() +
            ", startYear=" + getStartYear() +
            ", endYear=" + getEndYear() +
            ", startingSemesterType='" + getStartingSemesterType() + "'" +
            ", studyType='" + getStudyType() + "'" +
            ", studyForm='" + getStudyForm() + "'" +
            "}";
    }
}
