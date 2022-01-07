package com.darkjeff.crypto.exchanger.currency.exchange.rate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Map;
import lombok.Data;

@Schema
@Data
public class CurrencyRateDTO {

    @Schema(description = "Base currency")
    private String base;
    @Schema(description = "Date of search")
    private LocalDate date;
    @Schema(description = "Currency rate based on base currency")
    private Map<String, Float> rates;

}
