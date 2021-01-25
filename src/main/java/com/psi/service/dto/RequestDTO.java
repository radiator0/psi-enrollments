package com.psi.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * A DTO for the {@link com.psi.domain.Request} entity.
 */
public class RequestDTO implements Serializable {

    private Long id;

    @NotNull
    private UUID uuid;

    @NotNull
    private Instant date;

    @NotNull
    private String text;

    private Boolean isExamined;

    private Boolean isAccepted;

    private Long classGroupId;

    private Long studentId;

    private String classGroupCode;

    private String studentName;

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

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
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

    public Boolean isIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
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

    public String getClassGroupCode() {
        return classGroupCode;
    }

    public void setClassGroupCode(String classGroupCode) {
        this.classGroupCode = classGroupCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestDTO)) {
            return false;
        }

        return id != null && id.equals(((RequestDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", date='" + getDate() + "'" +
            ", text='" + getText() + "'" +
            ", isExamined='" + isIsExamined() + "'" +
            ", isAccepted='" + isIsAccepted() + "'" +
            ", classGroupId=" + getClassGroupId() +
            ", studentId=" + getStudentId() +
            "}";
    }
}
