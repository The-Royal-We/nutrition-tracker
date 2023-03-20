package com.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A NutritionalData.
 */
@Entity
@Table(name = "nutritional_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NutritionalData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "steps")
    private Integer steps;

    @Column(name = "sleep")
    private Double sleep;

    @Column(name = "water")
    private Double water;

    @Column(name = "protein")
    private Double protein;

    @Column(name = "carbs")
    private Double carbs;

    @Column(name = "fat")
    private Double fat;

    @Column(name = "calories")
    private Integer calories;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    @JsonIgnoreProperties(value = { "nutritionalData" }, allowSetters = true)
    private NutritionalWeek nutritionalWeek;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public NutritionalData id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWeight() {
        return this.weight;
    }

    public NutritionalData weight(Double weight) {
        this.setWeight(weight);
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getSteps() {
        return this.steps;
    }

    public NutritionalData steps(Integer steps) {
        this.setSteps(steps);
        return this;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }

    public Double getSleep() {
        return this.sleep;
    }

    public NutritionalData sleep(Double sleep) {
        this.setSleep(sleep);
        return this;
    }

    public void setSleep(Double sleep) {
        this.sleep = sleep;
    }

    public Double getWater() {
        return this.water;
    }

    public NutritionalData water(Double water) {
        this.setWater(water);
        return this;
    }

    public void setWater(Double water) {
        this.water = water;
    }

    public Double getProtein() {
        return this.protein;
    }

    public NutritionalData protein(Double protein) {
        this.setProtein(protein);
        return this;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getCarbs() {
        return this.carbs;
    }

    public NutritionalData carbs(Double carbs) {
        this.setCarbs(carbs);
        return this;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public Double getFat() {
        return this.fat;
    }

    public NutritionalData fat(Double fat) {
        this.setFat(fat);
        return this;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Integer getCalories() {
        return this.calories;
    }

    public NutritionalData calories(Integer calories) {
        this.setCalories(calories);
        return this;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public NutritionalData date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public NutritionalWeek getNutritionalWeek() {
        return this.nutritionalWeek;
    }

    public void setNutritionalWeek(NutritionalWeek nutritionalWeek) {
        this.nutritionalWeek = nutritionalWeek;
    }

    public NutritionalData nutritionalWeek(NutritionalWeek nutritionalWeek) {
        this.setNutritionalWeek(nutritionalWeek);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NutritionalData)) {
            return false;
        }
        return id != null && id.equals(((NutritionalData) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NutritionalData{" +
            "id=" + getId() +
            ", weight=" + getWeight() +
            ", steps=" + getSteps() +
            ", sleep=" + getSleep() +
            ", water=" + getWater() +
            ", protein=" + getProtein() +
            ", carbs=" + getCarbs() +
            ", fat=" + getFat() +
            ", calories=" + getCalories() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
