package com.psi.service.dto;

import com.psi.domain.enumeration.DayOfWeek;
import com.psi.domain.enumeration.SemesterPeriod;
import com.psi.domain.enumeration.WeekType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.psi.domain.ClassSchedule} entity.
 */
public class RecurringScheduleElementDTO implements Serializable {

    private Long id;

    @NotNull
    private DayOfWeek dayOfWeek;

    private WeekType weekType;

    private SemesterPeriod semesterPeriod;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    private String courseName;

    private String courseShortName;

    private String classType;

    private String room;

    private String building;

    private String lecturerTitle;

    private String lecturerFirstName;

    private String lecturerSecondName;

    private String lecturerLastName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseShortName() {
        return courseShortName;
    }

    public void setCourseShortName(String courseShortName) {
        this.courseShortName = courseShortName;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getLecturerTitle() {
        return lecturerTitle;
    }

    public void setLecturerTitle(String lecturerTitle) {
        this.lecturerTitle = lecturerTitle;
    }

    public String getLecturerFirstName() {
        return lecturerFirstName;
    }

    public void setLecturerFirstName(String lecturerFirstName) {
        this.lecturerFirstName = lecturerFirstName;
    }

    public String getLecturerSecondName() {
        return lecturerSecondName;
    }

    public void setLecturerSecondName(String lecturerSecondName) {
        this.lecturerSecondName = lecturerSecondName;
    }

    public String getLecturerLastName() {
        return lecturerLastName;
    }

    public void setLecturerLastName(String lecturerLastName) {
        this.lecturerLastName = lecturerLastName;
    }

    public RecurringScheduleElementDTO() {
    }

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

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecurringScheduleElementDTO)) {
            return false;
        }

        return id != null && id.equals(((RecurringScheduleElementDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecurringScheduleElementDTO{" +
            "id=" + id +
            ", dayOfWeek=" + dayOfWeek +
            ", weekType=" + weekType +
            ", semesterPeriod=" + semesterPeriod +
            ", startTime=" + startDate +
            ", endTime=" + endDate +
            ", courseName='" + courseName + '\'' +
            ", courseShortName='" + courseShortName + '\'' +
            ", classType='" + classType + '\'' +
            ", room='" + room + '\'' +
            ", building='" + building + '\'' +
            ", lecturerTitle='" + lecturerTitle + '\'' +
            ", lecturerFirstName='" + lecturerFirstName + '\'' +
            ", lecturerSecondName='" + lecturerSecondName + '\'' +
            ", lecturerLastName='" + lecturerLastName + '\'' +
            '}';
    }
}
