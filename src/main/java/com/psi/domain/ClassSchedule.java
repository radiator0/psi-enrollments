package com.psi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.psi.domain.enumeration.DayOfWeek;

import com.psi.domain.enumeration.WeekType;

import com.psi.domain.enumeration.SemesterPeriod;

/**
 * A ClassSchedule.
 */
@Entity
@Table(name = "class_schedule")
public class ClassSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Enumerated(EnumType.STRING)
    @Column(name = "week_type")
    private WeekType weekType;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester_period")
    private SemesterPeriod semesterPeriod;

    @NotNull
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @NotNull
    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "classSchedules", allowSetters = true)
    private Lecturer lecturer;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "classSchedules", allowSetters = true)
    private ClassGroup classGroup;

    @ManyToOne
    @JsonIgnoreProperties(value = "classSchedules", allowSetters = true)
    private Room room;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public ClassSchedule dayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public WeekType getWeekType() {
        return weekType;
    }

    public ClassSchedule weekType(WeekType weekType) {
        this.weekType = weekType;
        return this;
    }

    public void setWeekType(WeekType weekType) {
        this.weekType = weekType;
    }

    public SemesterPeriod getSemesterPeriod() {
        return semesterPeriod;
    }

    public ClassSchedule semesterPeriod(SemesterPeriod semesterPeriod) {
        this.semesterPeriod = semesterPeriod;
        return this;
    }

    public void setSemesterPeriod(SemesterPeriod semesterPeriod) {
        this.semesterPeriod = semesterPeriod;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public ClassSchedule startTime(Instant startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public ClassSchedule endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public ClassSchedule lecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
        return this;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public ClassGroup getClassGroup() {
        return classGroup;
    }

    public ClassSchedule classGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
        return this;
    }

    public void setClassGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
    }

    public Room getRoom() {
        return room;
    }

    public ClassSchedule room(Room room) {
        this.room = room;
        return this;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassSchedule)) {
            return false;
        }
        return id != null && id.equals(((ClassSchedule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassSchedule{" +
            "id=" + getId() +
            ", dayOfWeek='" + getDayOfWeek() + "'" +
            ", weekType='" + getWeekType() + "'" +
            ", semesterPeriod='" + getSemesterPeriod() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
