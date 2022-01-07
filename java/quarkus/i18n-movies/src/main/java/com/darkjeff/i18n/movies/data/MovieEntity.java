package com.darkjeff.i18n.movies.data;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movie")
public class MovieEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "release_date")
    private LocalDate release;

    private Float views;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}
