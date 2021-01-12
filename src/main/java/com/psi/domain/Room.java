package com.psi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Room.
 */
@Entity
@Table(name = "room")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false)
    private String number;

    @OneToMany(mappedBy = "room")
    private Set<ClassSchedule> classSchedules = new HashSet<>();

    @OneToMany(mappedBy = "room")
    private Set<ClassUnit> classUnits = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "rooms", allowSetters = true)
    private Building building;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public Room number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Set<ClassSchedule> getClassSchedules() {
        return classSchedules;
    }

    public Room classSchedules(Set<ClassSchedule> classSchedules) {
        this.classSchedules = classSchedules;
        return this;
    }

    public Room addClassSchedule(ClassSchedule classSchedule) {
        this.classSchedules.add(classSchedule);
        classSchedule.setRoom(this);
        return this;
    }

    public Room removeClassSchedule(ClassSchedule classSchedule) {
        this.classSchedules.remove(classSchedule);
        classSchedule.setRoom(null);
        return this;
    }

    public void setClassSchedules(Set<ClassSchedule> classSchedules) {
        this.classSchedules = classSchedules;
    }

    public Set<ClassUnit> getClassUnits() {
        return classUnits;
    }

    public Room classUnits(Set<ClassUnit> classUnits) {
        this.classUnits = classUnits;
        return this;
    }

    public Room addClassUnit(ClassUnit classUnit) {
        this.classUnits.add(classUnit);
        classUnit.setRoom(this);
        return this;
    }

    public Room removeClassUnit(ClassUnit classUnit) {
        this.classUnits.remove(classUnit);
        classUnit.setRoom(null);
        return this;
    }

    public void setClassUnits(Set<ClassUnit> classUnits) {
        this.classUnits = classUnits;
    }

    public Building getBuilding() {
        return building;
    }

    public Room building(Building building) {
        this.building = building;
        return this;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        return id != null && id.equals(((Room) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Room{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            "}";
    }
}
