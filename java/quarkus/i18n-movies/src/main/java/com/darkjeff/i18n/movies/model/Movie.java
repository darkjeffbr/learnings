package com.darkjeff.i18n.movies.model;

import java.time.LocalDate;
import javax.money.MonetaryAmount;
import lombok.Data;

@Data
public class Movie {

    private String title;
    private LocalDate release;
    private Float views;
    private MonetaryAmount productionCost;

}
