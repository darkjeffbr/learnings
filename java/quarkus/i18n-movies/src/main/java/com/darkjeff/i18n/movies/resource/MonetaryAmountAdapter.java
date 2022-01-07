package com.darkjeff.i18n.movies.resource;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;
import javax.money.MonetaryAmount;
import javax.ws.rs.ext.Provider;

@Provider
public class MonetaryAmountAdapter implements JsonbAdapter<MonetaryAmount, JsonObject> {

    @Override
    public JsonObject adaptToJson(MonetaryAmount monetaryAmount) throws Exception {
        return Json.createObjectBuilder()
            .add("number", String.valueOf(monetaryAmount.getNumber()))
            .add("currency", monetaryAmount.getCurrency().getCurrencyCode())
            .build();
    }

    @Override
    public MonetaryAmount adaptFromJson(JsonObject jsonObject) throws Exception {
        return null;
    }
}
