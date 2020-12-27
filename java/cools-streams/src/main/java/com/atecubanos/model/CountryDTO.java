package com.atecubanos.model;

public class CountryDTO {

    public String name;
    public String continent;

    public CountryDTO(String name, String continent){
        this.name = name;
        this.continent = continent;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return continent;
    }
}
