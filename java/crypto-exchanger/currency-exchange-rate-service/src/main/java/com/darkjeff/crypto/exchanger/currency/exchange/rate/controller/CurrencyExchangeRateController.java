package com.darkjeff.crypto.exchanger.currency.exchange.rate.controller;

import com.darkjeff.crypto.exchanger.currency.exchange.rate.client.CurrencyExchangeClient;
import com.darkjeff.crypto.exchanger.currency.exchange.rate.dto.CurrencyRateDTO;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
@OpenAPIDefinition(
    tags = @Tag(
        description = "Currency Exchange Rate Controller",
        name = "Currency Exchange Rate Controller")
)
public class CurrencyExchangeRateController {

    private final CurrencyExchangeClient currencyExchangeClient;

    @Operation(
        method = "GET",
        description = "Get currency rates for current date",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(
                        implementation = CurrencyRateDTO.class
                    )
                )
            )
        }
    )
    @GetMapping
    @Cacheable(cacheNames = "currency-exchange-rates")
    public ResponseEntity<CurrencyRateDTO> getExchangeRate() {
        return ResponseEntity.ok(currencyExchangeClient.getRates("USD").getBody());
    }

}
