package com.atecubanos.model;

public class Country {

    private String name;
    private Continent continent;

    public Country(String name, Continent continent ){
        this.name = name;
        this.continent = continent;
    }

    public String getName() {
        return name;
    }

    public Continent getContinent() {
        return continent;
    }
}
