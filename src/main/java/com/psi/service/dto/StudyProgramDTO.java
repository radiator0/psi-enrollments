package com.psi.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.psi.domain.enumeration.SemesterType;
import com.psi.domain.enumeration.StudyType;
import com.psi.domain.enumeration.StudyForm;

/**
 * A DTO for the {@link com.psi.domain.StudyProgram} entity.
 */
public class StudyProgramDTO implements Serializable {

    private Long id;

    @NotNull
    @Min(value = 0)
    private Integer startYear;

    @NotNull
    @Min(value = 0)
    private Integer endYear;

    @NotNull
    private SemesterType startingSemesterType;

    @NotNull
    private StudyType studyType;

    @NotNull
    private StudyForm studyForm;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public SemesterType getStartingSemesterType() {
        return startingSemesterType;
    }

    public void setStartingSemesterType(SemesterType startingSemesterType) {
        this.startingSemesterType = startingSemesterType;
    }

    public StudyType getStudyType() {
        return studyType;
    }

    public void setStudyType(StudyType studyType) {
        this.studyType = studyType;
    }

    public StudyForm getStudyForm() {
        return studyForm;
    }

    public void setStudyForm(StudyForm studyForm) {
        this.studyForm = studyForm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudyProgramDTO studyProgramDTO = (StudyProgramDTO) o;
        if (studyProgramDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), studyProgramDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StudyProgramDTO{" +
            "id=" + getId() +
            ", startYear=" + getStartYear() +
            ", endYear=" + getEndYear() +
            ", startingSemesterType='" + getStartingSemesterType() + "'" +
            ", studyType='" + getStudyType() + "'" +
            ", studyForm='" + getStudyForm() + "'" +
            "}";
    }
}
