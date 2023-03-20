package com.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NutritionalWeek.
 */
@Entity
@Table(name = "nutritional_week")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NutritionalWeek implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "nutritionalWeek")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "nutritionalWeek" }, allowSetters = true)
    private Set<NutritionalData> nutritionalData = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NutritionalWeek id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateFrom() {
        return this.dateFrom;
    }

    public NutritionalWeek dateFrom(LocalDate dateFrom) {
        this.setDateFrom(dateFrom);
        return this;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return this.dateTo;
    }

    public NutritionalWeek dateTo(LocalDate dateTo) {
        this.setDateTo(dateTo);
        return this;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getName() {
        return this.name;
    }

    public NutritionalWeek name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<NutritionalData> getNutritionalData() {
        return this.nutritionalData;
    }

    public void setNutritionalData(Set<NutritionalData> nutritionalData) {
        if (this.nutritionalData != null) {
            this.nutritionalData.forEach(i -> i.setNutritionalWeek(null));
        }
        if (nutritionalData != null) {
            nutritionalData.forEach(i -> i.setNutritionalWeek(this));
        }
        this.nutritionalData = nutritionalData;
    }

    public NutritionalWeek nutritionalData(Set<NutritionalData> nutritionalData) {
        this.setNutritionalData(nutritionalData);
        return this;
    }

    public NutritionalWeek addNutritionalData(NutritionalData nutritionalData) {
        this.nutritionalData.add(nutritionalData);
        nutritionalData.setNutritionalWeek(this);
        return this;
    }

    public NutritionalWeek removeNutritionalData(NutritionalData nutritionalData) {
        this.nutritionalData.remove(nutritionalData);
        nutritionalData.setNutritionalWeek(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NutritionalWeek)) {
            return false;
        }
        return id != null && id.equals(((NutritionalWeek) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NutritionalWeek{" +
            "id=" + getId() +
            ", dateFrom='" + getDateFrom() + "'" +
            ", dateTo='" + getDateTo() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
