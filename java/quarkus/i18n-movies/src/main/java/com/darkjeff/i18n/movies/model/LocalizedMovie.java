package com.darkjeff.i18n.movies.model;

import lombok.Data;

@Data
public class LocalizedMovie {

    private String title;
    private String release;
    private String utcRelease;
    private String views;
    private String productionCost;

}
