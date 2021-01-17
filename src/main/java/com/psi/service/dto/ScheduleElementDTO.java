package com.psi.service.dto;

import com.psi.domain.enumeration.ClassType;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.psi.domain.ClassUnit} entity.
 */
public class ScheduleElementDTO implements Serializable {

    private Long id;

    private LocalDate day;

    private Instant startTime;

    private Instant endTime;

    private String courseName;

    private String courseShortName;

    private ClassType classType;

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

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScheduleElementDTO)) {
            return false;
        }

        return id != null && id.equals(((ScheduleElementDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassUnitDetailsDTO{" +
            "id=" + id +
            ", day=" + day +
            ", startTime=" + startTime +
            ", endTime=" + endTime +
            ", courseName='" + courseName + '\'' +
            ", courseShortName='" + courseShortName + '\'' +
            ", classType=" + classType +
            ", room='" + room + '\'' +
            ", building='" + building + '\'' +
            ", lecturerTitle='" + lecturerTitle + '\'' +
            ", lecturerFirstName='" + lecturerFirstName + '\'' +
            ", lecturerSecondName='" + lecturerSecondName + '\'' +
            ", lecturerLastName='" + lecturerLastName + '\'' +
            '}';
    }
}
