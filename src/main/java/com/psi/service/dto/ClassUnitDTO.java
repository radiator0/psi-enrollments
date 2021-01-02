package com.psi.service.dto;
import java.time.Instant;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.psi.domain.ClassUnit} entity.
 */
public class ClassUnitDTO implements Serializable {

    private Long id;

    private LocalDate day;

    private Instant startTime;

    private Instant endTime;


    private Long classGroupId;

    private Long roomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Long getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(Long classGroupId) {
        this.classGroupId = classGroupId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClassUnitDTO classUnitDTO = (ClassUnitDTO) o;
        if (classUnitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classUnitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassUnitDTO{" +
            "id=" + getId() +
            ", day='" + getDay() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", classGroup=" + getClassGroupId() +
            ", room=" + getRoomId() +
            "}";
    }
}
