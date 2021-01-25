package com.psi.service.dto;

import java.io.Serializable;

public class RequestOverLimitDTO implements Serializable {
    private Long id;
    private Boolean isExamined;
    private Boolean isAccepted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getExamined() {
        return isExamined;
    }

    public void setExamined(Boolean examined) {
        isExamined = examined;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    @Override
    public String toString() {
        return "RequestOverLimitDTO{" +
            "id=" + id +
            ", isExamined=" + isExamined +
            ", isAccepted=" + isAccepted +
            '}';
    }
}
