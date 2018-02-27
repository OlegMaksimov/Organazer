package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "discription")
    private String discription;

    @Column(name = "contentment")
    private Long contentment;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BalanceWheel> categoryBalances = new HashSet<>();

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Task> categoryTasks = new HashSet<>();

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

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDiscription() {
        return discription;
    }

    public Category discription(String discription) {
        this.discription = discription;
        return this;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Long getContentment() {
        return contentment;
    }

    public Category contentment(Long contentment) {
        this.contentment = contentment;
        return this;
    }

    public void setContentment(Long contentment) {
        this.contentment = contentment;
    }

    public Set<BalanceWheel> getCategoryBalances() {
        return categoryBalances;
    }

    public Category categoryBalances(Set<BalanceWheel> balanceWheels) {
        this.categoryBalances = balanceWheels;
        return this;
    }

    public Category addCategoryBalance(BalanceWheel balanceWheel) {
        this.categoryBalances.add(balanceWheel);
        balanceWheel.setCategory(this);
        return this;
    }

    public Category removeCategoryBalance(BalanceWheel balanceWheel) {
        this.categoryBalances.remove(balanceWheel);
        balanceWheel.setCategory(null);
        return this;
    }

    public void setCategoryBalances(Set<BalanceWheel> balanceWheels) {
        this.categoryBalances = balanceWheels;
    }

    public Set<Task> getCategoryTasks() {
        return categoryTasks;
    }

    public Category categoryTasks(Set<Task> tasks) {
        this.categoryTasks = tasks;
        return this;
    }

    public Category addCategoryTask(Task task) {
        this.categoryTasks.add(task);
        task.setCategory(this);
        return this;
    }

    public Category removeCategoryTask(Task task) {
        this.categoryTasks.remove(task);
        task.setCategory(null);
        return this;
    }

    public void setCategoryTasks(Set<Task> tasks) {
        this.categoryTasks = tasks;
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
        Category category = (Category) o;
        if (category.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", discription='" + getDiscription() + "'" +
            ", contentment=" + getContentment() +
            "}";
    }
}
