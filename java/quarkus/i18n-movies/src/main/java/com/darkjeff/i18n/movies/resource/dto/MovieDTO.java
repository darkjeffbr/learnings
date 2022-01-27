package com.darkjeff.i18n.movies.resource.dto;

import javax.json.bind.annotation.JsonbProperty;
import lombok.Data;

@Data
public class MovieDTO {

    private String title;
    private String release;
    @JsonbProperty("utc_release")
    private String utcRelease;
    private String views;
    private String productionCost;
    //@JsonbTypeAdapter(MonetaryAmountAdapter.class)
    ///private MonetaryAmount productionCost;
}
