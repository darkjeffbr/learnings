package com.darkjeff.i18n.movies.model;

import java.time.ZonedDateTime;
import javax.money.MonetaryAmount;
import lombok.Data;

@Data
public class Movie {

    private String title;
    private ZonedDateTime release;
    private Float views;
    private MonetaryAmount productionCost;

    private String timezone;

}
