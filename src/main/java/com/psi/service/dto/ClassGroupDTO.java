package com.psi.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.psi.domain.ClassGroup} entity.
 */
public class ClassGroupDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private Boolean isEnrollmentAboveLimitAllowed;

    @NotNull
    @Min(value = 0)
    private Integer peopleLimit;

    @NotNull
    @Min(value = 0)
    private Integer enrolledCount;

    @NotNull
    private Boolean isFull;


    private Long courseId;

    private Long lecturerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isIsEnrollmentAboveLimitAllowed() {
        return isEnrollmentAboveLimitAllowed;
    }

    public void setIsEnrollmentAboveLimitAllowed(Boolean isEnrollmentAboveLimitAllowed) {
        this.isEnrollmentAboveLimitAllowed = isEnrollmentAboveLimitAllowed;
    }

    public Integer getPeopleLimit() {
        return peopleLimit;
    }

    public void setPeopleLimit(Integer peopleLimit) {
        this.peopleLimit = peopleLimit;
    }

    public Integer getEnrolledCount() {
        return enrolledCount;
    }

    public void setEnrolledCount(Integer enrolledCount) {
        this.enrolledCount = enrolledCount;
    }

    public Boolean isIsFull() {
        return isFull;
    }

    public void setIsFull(Boolean isFull) {
        this.isFull = isFull;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassGroupDTO classGroupDTO = (ClassGroupDTO) o;
        if (classGroupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classGroupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassGroupDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", isEnrollmentAboveLimitAllowed='" + isIsEnrollmentAboveLimitAllowed() + "'" +
            ", peopleLimit=" + getPeopleLimit() +
            ", enrolledCount=" + getEnrolledCount() +
            ", isFull='" + isIsFull() + "'" +
            ", course=" + getCourseId() +
            ", lecturer=" + getLecturerId() +
            "}";
    }
}
