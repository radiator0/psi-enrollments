package com.psi.service.dto;

import io.micrometer.core.lang.NonNull;

import java.io.Serializable;

public class IdDTO implements Serializable {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IdDTO{" +
            "id=" + id +
            '}';
    }
}
