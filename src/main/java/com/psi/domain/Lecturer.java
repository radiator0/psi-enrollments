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

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "mainLecturer")
    private Set<ClassGroup> classGroups = new HashSet<>();

    @OneToMany(mappedBy = "lecturer")
    private Set<ClassSchedule> classSchedules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Lecturer firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public Lecturer secondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public Lecturer lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMail() {
        return mail;
    }

    public Lecturer mail(String mail) {
        this.mail = mail;
        return this;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
            ", firstName='" + getFirstName() + "'" +
            ", secondName='" + getSecondName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", mail='" + getMail() + "'" +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
