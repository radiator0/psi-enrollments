package com.psi.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.psi.domain.SelectableModule} entity.
 */
public class SelectableModuleDTO implements Serializable {

    private Long id;

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

        SelectableModuleDTO selectableModuleDTO = (SelectableModuleDTO) o;
        if (selectableModuleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), selectableModuleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SelectableModuleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
