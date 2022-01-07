package com.darkjeff.i18n.movies.model;

import java.time.LocalDate;
import javax.money.MonetaryAmount;

public class Movie {

    private String title;
    private LocalDate release;
    private Float views;
    private MonetaryAmount productionCost;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getRelease() {
        return release;
    }

    public void setRelease(LocalDate release) {
        this.release = release;
    }

    public Float getViews() {
        return views;
    }

    public void setViews(Float views) {
        this.views = views;
    }

    public MonetaryAmount getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(MonetaryAmount productionCost) {
        this.productionCost = productionCost;
    }
}
