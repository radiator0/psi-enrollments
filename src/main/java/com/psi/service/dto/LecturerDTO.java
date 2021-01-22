package com.psi.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.psi.domain.Lecturer} entity.
 */
public class LecturerDTO implements Serializable {

    private Long id;

    private String title;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LecturerDTO)) {
            return false;
        }

        return id != null && id.equals(((LecturerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LecturerDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
