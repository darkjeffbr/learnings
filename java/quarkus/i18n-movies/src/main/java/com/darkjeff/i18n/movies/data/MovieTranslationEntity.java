package com.darkjeff.i18n.movies.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "movie_translation")
public class MovieTranslationEntity {

    @Id
    @Column(name = "movie")
    private Long id;

    @OneToOne
    @JoinColumn(name = "language")
    private LanguageEntity language;

    private String title;

    private Double cost;

    @Column(name = "cost_currency")
    private String currency;

    @ManyToOne
    @JoinColumn(name = "movie", insertable = false, updatable = false)
    private MovieEntity movie;

}
