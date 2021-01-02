package com.psi.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.psi.domain.enumeration.DayOfWeek;
import com.psi.domain.enumeration.WeekType;
import com.psi.domain.enumeration.SemesterPeriod;

/**
 * A DTO for the {@link com.psi.domain.ClassSchedule} entity.
 */
public class ClassScheduleDTO implements Serializable {

    private Long id;

    @NotNull
    private DayOfWeek dayOfWeek;

    private WeekType weekType;

    private SemesterPeriod semesterPeriod;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;


    private Long classGroupId;

    private Long roomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public WeekType getWeekType() {
        return weekType;
    }

    public void setWeekType(WeekType weekType) {
        this.weekType = weekType;
    }

    public SemesterPeriod getSemesterPeriod() {
        return semesterPeriod;
    }

    public void setSemesterPeriod(SemesterPeriod semesterPeriod) {
        this.semesterPeriod = semesterPeriod;
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

        ClassScheduleDTO classScheduleDTO = (ClassScheduleDTO) o;
        if (classScheduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classScheduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClassScheduleDTO{" +
            "id=" + getId() +
            ", dayOfWeek='" + getDayOfWeek() + "'" +
            ", weekType='" + getWeekType() + "'" +
            ", semesterPeriod='" + getSemesterPeriod() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", classGroup=" + getClassGroupId() +
            ", room=" + getRoomId() +
            "}";
    }
}
