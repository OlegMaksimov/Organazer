package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A BalanceWheel.
 */
@Entity
@Table(name = "balance_wheel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BalanceWheel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private ZonedDateTime date;

    @ManyToOne
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public BalanceWheel date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public BalanceWheel category(Category category) {
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
        BalanceWheel balanceWheel = (BalanceWheel) o;
        if (balanceWheel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), balanceWheel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BalanceWheel{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
