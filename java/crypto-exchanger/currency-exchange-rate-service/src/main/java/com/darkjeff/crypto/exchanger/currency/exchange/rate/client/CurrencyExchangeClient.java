package com.darkjeff.crypto.exchanger.currency.exchange.rate.client;

import com.darkjeff.crypto.exchanger.currency.exchange.rate.dto.CurrencyRateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "CurrencyExchangeClient",
    url = "https://api.frankfurter.app/latest"
)
public interface CurrencyExchangeClient {

    @GetMapping
    ResponseEntity<CurrencyRateDTO> getRates(@RequestParam(value = "from") String from);

}
