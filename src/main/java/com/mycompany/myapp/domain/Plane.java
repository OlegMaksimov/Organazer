package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Plane.
 */
@Entity
@Table(name = "plane")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Plane implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_start")
    private ZonedDateTime dateStart;

    @Column(name = "date_end")
    private ZonedDateTime dateEnd;

    @Column(name = "discription")
    private String discription;

    @ManyToOne
    private Task task;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateStart() {
        return dateStart;
    }

    public Plane dateStart(ZonedDateTime dateStart) {
        this.dateStart = dateStart;
        return this;
    }

    public void setDateStart(ZonedDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public ZonedDateTime getDateEnd() {
        return dateEnd;
    }

    public Plane dateEnd(ZonedDateTime dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public void setDateEnd(ZonedDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDiscription() {
        return discription;
    }

    public Plane discription(String discription) {
        this.discription = discription;
        return this;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Task getTask() {
        return task;
    }

    public Plane task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Plane plane = (Plane) o;
        if (plane.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), plane.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Plane{" +
            "id=" + getId() +
            ", dateStart='" + getDateStart() + "'" +
            ", dateEnd='" + getDateEnd() + "'" +
            ", discription='" + getDiscription() + "'" +
            "}";
    }
}
