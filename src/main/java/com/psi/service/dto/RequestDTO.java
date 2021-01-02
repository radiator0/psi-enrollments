package com.psi.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.psi.domain.Request} entity.
 */
public class RequestDTO implements Serializable {

    private Long id;

    @NotNull
    private UUID uuid;

    @NotNull
    private LocalDate date;

    @NotNull
    private String text;

    @NotNull
    private Boolean isExamined;


    private Long classGroupId;

    private Long studentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isIsExamined() {
        return isExamined;
    }

    public void setIsExamined(Boolean isExamined) {
        this.isExamined = isExamined;
    }

    public Long getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(Long classGroupId) {
        this.classGroupId = classGroupId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RequestDTO requestDTO = (RequestDTO) o;
        if (requestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), requestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", date='" + getDate() + "'" +
            ", text='" + getText() + "'" +
            ", isExamined='" + isIsExamined() + "'" +
            ", classGroup=" + getClassGroupId() +
            ", student=" + getStudentId() +
            "}";
    }
}
