package com.psi.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.psi.domain.Specialty} entity.
 */
public class SpecialtyDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SpecialtyDTO specialtyDTO = (SpecialtyDTO) o;
        if (specialtyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), specialtyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SpecialtyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
