package com.psi.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Lecturer.
 */
@Entity
@Table(name = "lecturer")
public class Lecturer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "mainLecturer")
    private Set<ClassGroup> classGroups = new HashSet<>();

    @OneToMany(mappedBy = "lecturer")
    private Set<ClassSchedule> classSchedules = new HashSet<>();

    @OneToOne
    @JoinColumn(referencedColumnName = "login", name="login")
    private User internalUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Lecturer title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getInternalUser() {
        return internalUser;
    }

    public Lecturer internalUser(User internalUser) {
        this.internalUser = internalUser;
        return this;
    }

    public void setInternalUser(User internalUser) {
        this.internalUser = internalUser;
    }

    public Set<ClassGroup> getClassGroups() {
        return classGroups;
    }

    public Lecturer classGroups(Set<ClassGroup> classGroups) {
        this.classGroups = classGroups;
        return this;
    }

    public Lecturer addClassGroup(ClassGroup classGroup) {
        this.classGroups.add(classGroup);
        classGroup.setMainLecturer(this);
        return this;
    }

    public Lecturer removeClassGroup(ClassGroup classGroup) {
        this.classGroups.remove(classGroup);
        classGroup.setMainLecturer(null);
        return this;
    }

    public void setClassGroups(Set<ClassGroup> classGroups) {
        this.classGroups = classGroups;
    }

    public Set<ClassSchedule> getClassSchedules() {
        return classSchedules;
    }

    public Lecturer classSchedules(Set<ClassSchedule> classSchedules) {
        this.classSchedules = classSchedules;
        return this;
    }

    public Lecturer addClassSchedule(ClassSchedule classSchedule) {
        this.classSchedules.add(classSchedule);
        classSchedule.setLecturer(this);
        return this;
    }

    public Lecturer removeClassSchedule(ClassSchedule classSchedule) {
        this.classSchedules.remove(classSchedule);
        classSchedule.setLecturer(null);
        return this;
    }

    public void setClassSchedules(Set<ClassSchedule> classSchedules) {
        this.classSchedules = classSchedules;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lecturer)) {
            return false;
        }
        return id != null && id.equals(((Lecturer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Lecturer{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }

    public String getName() {
        return getTitle() + " " +
            internalUser.getFirstName() + " " +
            internalUser.getSecondName() + " " +
            internalUser.getLastName();
    }
}
