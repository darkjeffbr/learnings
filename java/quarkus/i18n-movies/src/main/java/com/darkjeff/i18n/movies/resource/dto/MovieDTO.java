package com.darkjeff.i18n.movies.resource.dto;

import com.darkjeff.i18n.movies.resource.MonetaryAmountAdapter;
import java.time.LocalDate;
import javax.json.bind.annotation.JsonbTypeAdapter;
import javax.money.MonetaryAmount;
import lombok.Data;

@Data
public class MovieDTO {

    private String title;
    private LocalDate release;
    private Float views;
    @JsonbTypeAdapter(MonetaryAmountAdapter.class)
    private MonetaryAmount productionCost;

}
