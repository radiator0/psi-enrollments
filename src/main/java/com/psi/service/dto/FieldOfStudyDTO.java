package com.psi.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.psi.domain.FieldOfStudy} entity.
 */
public class FieldOfStudyDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String uniqueName;


    private Long studyProgramId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public Long getStudyProgramId() {
        return studyProgramId;
    }

    public void setStudyProgramId(Long studyProgramId) {
        this.studyProgramId = studyProgramId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldOfStudyDTO)) {
            return false;
        }

        return id != null && id.equals(((FieldOfStudyDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldOfStudyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", uniqueName='" + getUniqueName() + "'" +
            ", studyProgramId=" + getStudyProgramId() +
            "}";
    }
}
