package com.psi.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SelectableModule.
 */
@Entity
@Table(name = "selectable_module")
public class SelectableModule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "selectableModule")
    private Set<CourseUnit> courseUnits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SelectableModule name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CourseUnit> getCourseUnits() {
        return courseUnits;
    }

    public SelectableModule courseUnits(Set<CourseUnit> courseUnits) {
        this.courseUnits = courseUnits;
        return this;
    }

    public SelectableModule addCourseUnit(CourseUnit courseUnit) {
        this.courseUnits.add(courseUnit);
        courseUnit.setSelectableModule(this);
        return this;
    }

    public SelectableModule removeCourseUnit(CourseUnit courseUnit) {
        this.courseUnits.remove(courseUnit);
        courseUnit.setSelectableModule(null);
        return this;
    }

    public void setCourseUnits(Set<CourseUnit> courseUnits) {
        this.courseUnits = courseUnits;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SelectableModule)) {
            return false;
        }
        return id != null && id.equals(((SelectableModule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SelectableModule{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
