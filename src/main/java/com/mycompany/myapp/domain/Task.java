package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Status;

import com.mycompany.myapp.domain.enumeration.Repeat;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "discription")
    private String discription;

    @Column(name = "date_start")
    private ZonedDateTime dateStart;

    @Column(name = "date_end")
    private ZonedDateTime dateEnd;

    @Column(name = "prioritet")
    private Long prioritet;

    @Column(name = "jhi_time")
    private Double time;

    @Column(name = "progres")
    private Double progres;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "impotment")
    private Boolean impotment;

    @Column(name = "quick")
    private Boolean quick;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_repeat")
    private Repeat repeat;

    @OneToMany(mappedBy = "task")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Plane> tasks = new HashSet<>();

    @ManyToOne
    private Task task;

    @OneToMany(mappedBy = "task")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Task> subTasks = new HashSet<>();

    @ManyToOne
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Task name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public Task discription(String discription) {
        this.discription = discription;
        return this;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public ZonedDateTime getDateStart() {
        return dateStart;
    }

    public Task dateStart(ZonedDateTime dateStart) {
        this.dateStart = dateStart;
        return this;
    }

    public void setDateStart(ZonedDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public ZonedDateTime getDateEnd() {
        return dateEnd;
    }

    public Task dateEnd(ZonedDateTime dateEnd) {
        this.dateEnd = dateEnd;
        return this;
    }

    public void setDateEnd(ZonedDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Long getPrioritet() {
        return prioritet;
    }

    public Task prioritet(Long prioritet) {
        this.prioritet = prioritet;
        return this;
    }

    public void setPrioritet(Long prioritet) {
        this.prioritet = prioritet;
    }

    public Double getTime() {
        return time;
    }

    public Task time(Double time) {
        this.time = time;
        return this;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Double getProgres() {
        return progres;
    }

    public Task progres(Double progres) {
        this.progres = progres;
        return this;
    }

    public void setProgres(Double progres) {
        this.progres = progres;
    }

    public Status getStatus() {
        return status;
    }

    public Task status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean isImpotment() {
        return impotment;
    }

    public Task impotment(Boolean impotment) {
        this.impotment = impotment;
        return this;
    }

    public void setImpotment(Boolean impotment) {
        this.impotment = impotment;
    }

    public Boolean isQuick() {
        return quick;
    }

    public Task quick(Boolean quick) {
        this.quick = quick;
        return this;
    }

    public void setQuick(Boolean quick) {
        this.quick = quick;
    }

    public Repeat getRepeat() {
        return repeat;
    }

    public Task repeat(Repeat repeat) {
        this.repeat = repeat;
        return this;
    }

    public void setRepeat(Repeat repeat) {
        this.repeat = repeat;
    }

    public Set<Plane> getTasks() {
        return tasks;
    }

    public Task tasks(Set<Plane> planes) {
        this.tasks = planes;
        return this;
    }

    public Task addTask(Plane plane) {
        this.tasks.add(plane);
        plane.setTask(this);
        return this;
    }

    public Task removeTask(Plane plane) {
        this.tasks.remove(plane);
        plane.setTask(null);
        return this;
    }

    public void setTasks(Set<Plane> planes) {
        this.tasks = planes;
    }

    public Task getTask() {
        return task;
    }

    public Task task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Set<Task> getSubTasks() {
        return subTasks;
    }

    public Task subTasks(Set<Task> tasks) {
        this.subTasks = tasks;
        return this;
    }

    public Task addSubTask(Task task) {
        this.subTasks.add(task);
        task.setTask(this);
        return this;
    }

    public Task removeSubTask(Task task) {
        this.subTasks.remove(task);
        task.setTask(null);
        return this;
    }

    public void setSubTasks(Set<Task> tasks) {
        this.subTasks = tasks;
    }

    public Category getCategory() {
        return category;
    }

    public Task category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        Task task = (Task) o;
        if (task.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", discription='" + getDiscription() + "'" +
            ", dateStart='" + getDateStart() + "'" +
            ", dateEnd='" + getDateEnd() + "'" +
            ", prioritet=" + getPrioritet() +
            ", time=" + getTime() +
            ", progres=" + getProgres() +
            ", status='" + getStatus() + "'" +
            ", impotment='" + isImpotment() + "'" +
            ", quick='" + isQuick() + "'" +
            ", repeat='" + getRepeat() + "'" +
            "}";
    }
}
