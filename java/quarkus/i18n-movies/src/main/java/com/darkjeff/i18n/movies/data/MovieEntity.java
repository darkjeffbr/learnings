package com.darkjeff.i18n.movies.data;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "movie")
public class MovieEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "release_date")
    private LocalDate release;

    private Float views;

}
