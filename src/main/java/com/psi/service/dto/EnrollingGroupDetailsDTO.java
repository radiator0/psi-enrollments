package com.psi.service.dto;

import java.io.Serializable;
import java.util.Set;

public class EnrollingGroupDetailsDTO implements Serializable {
    private Long id;
    private String groupCode;
    private Integer enrolledCount;
    private Integer limit;
    private Boolean canEnrollOverLimit;
    private Boolean isStudentEnrolled;
    private RequestOverLimitDTO requestOverLimitDTO;
    private Set<RecurringScheduleElementDTO> schedules;
    private String lecturerTitle;
    private String lecturerFirstName;
    private String lecturerSecondName;
    private String lecturerLastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Integer getEnrolledCount() {
        return enrolledCount;
    }

    public void setEnrolledCount(Integer enrolledCount) {
        this.enrolledCount = enrolledCount;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Boolean getCanEnrollOverLimit() {
        return canEnrollOverLimit;
    }

    public void setCanEnrollOverLimit(Boolean canEnrollOverLimit) {
        this.canEnrollOverLimit = canEnrollOverLimit;
    }

    public RequestOverLimitDTO getRequestOverLimitDTO() {
        return requestOverLimitDTO;
    }

    public void setRequestOverLimitDTO(RequestOverLimitDTO requestOverLimitDTO) {
        this.requestOverLimitDTO = requestOverLimitDTO;
    }

    public Boolean getStudentEnrolled() {
        return isStudentEnrolled;
    }

    public void setStudentEnrolled(Boolean studentEnrolled) {
        isStudentEnrolled = studentEnrolled;
    }

    public Set<RecurringScheduleElementDTO> getSchedules() {
        return schedules;
    }

    public void setSchedules(Set<RecurringScheduleElementDTO> schedules) {
        this.schedules = schedules;
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

    @Override
    public String toString() {
        return "EnrollingGroupDetailsDTO{" +
            "groupId=" + id +
            ", groupCode='" + groupCode + '\'' +
            ", enrolledCount=" + enrolledCount +
            ", limit=" + limit +
            ", canEnrollOverLimit=" + canEnrollOverLimit +
            ", isStudentEnrolled=" + isStudentEnrolled +
            ", requestOverLimitDTO=" + requestOverLimitDTO +
            ", schedules=" + schedules +
            ", lecturerTitle='" + lecturerTitle + '\'' +
            ", lecturerFirstName='" + lecturerFirstName + '\'' +
            ", lecturerSecondName='" + lecturerSecondName + '\'' +
            ", lecturerLastName='" + lecturerLastName + '\'' +
            '}';
    }
}
