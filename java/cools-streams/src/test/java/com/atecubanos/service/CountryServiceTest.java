package com.atecubanos.service;

import com.atecubanos.exception.CountryNotFoundException;
import com.atecubanos.model.Continent;
import com.atecubanos.model.CountryDTO;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.atecubanos.model.Continent.AFRICA;
import static com.atecubanos.model.Continent.ANTARCTICA;
import static com.atecubanos.model.Continent.ASIA;
import static com.atecubanos.model.Continent.EUROPE;
import static com.atecubanos.model.Continent.NORTH_AMERICA;
import static com.atecubanos.model.Continent.OCEANIA;
import static com.atecubanos.model.Continent.SOUTH_AMERICA;
import static org.junit.jupiter.api.Assertions.*;

class CountryServiceTest {

    private CountryService countryService = new CountryService();

    @Test
    void getCountryInUpperCase() {

        List<String> result = countryService.getCountryInUpperCase();

        assertEquals(3, result.size());
        assertEquals("AFGHANISTAN", result.get(0));
        assertEquals("ALBANIA", result.get(1));
        assertEquals("ALGERIA", result.get(2));
    }

    @Test
    void getCountriesAsDTO() {
        List<CountryDTO> result = countryService.getCountriesAsDTO();

        assertEquals(3, result.size());
        assertEquals("American Samoa", result.get(0).getName());
        assertEquals("OCEANIA", result.get(0).getContinent());

        assertEquals("Andorra", result.get(1).getName());
        assertEquals("EUROPE", result.get(1).getContinent());

        assertEquals("Angola", result.get(2).getName());
        assertEquals("AFRICA", result.get(2).getContinent());

    }

    @Test
    void getCountryStartingBy() {

        List<String> result = countryService.getCountryStartingBy('Z');
        assertEquals(2, result.size());
        assertEquals("Zambia", result.get(0));
        assertEquals("Zimbabwe", result.get(1));


        result = countryService.getCountryStartingBy('X');
        assertTrue(result.isEmpty());

    }

    @Test
    void countCountryStartingBy() {

        long result = countryService.countCountryStartingBy('Z');
        assertEquals(2, result);

        result = countryService.countCountryStartingBy('X');
        assertEquals(0, result);

    }

    @Test
    void getContinentWhereCountryStartsBy() {

        List<String> result = countryService.getContinentWhereCountryStartsBy('Z');
        assertEquals(1, result.size());
        assertEquals("AFRICA", result.get(0));

        result = countryService.getContinentWhereCountryStartsBy('J');
        assertEquals(2, result.size());
        assertTrue(result.contains("NORTH_AMERICA"));
        assertTrue(result.contains("ASIA"));

        result = countryService.getContinentWhereCountryStartsBy('X');
        assertTrue(result.isEmpty());

    }

    @Test
    void getCountryByName() {

        CountryDTO result = countryService.getCountryByName("Spain");
        assertEquals("Spain", result.getName());
        assertEquals("EUROPE", result.getContinent());

        assertThrows(CountryNotFoundException.class, () -> {
            countryService.getCountryByName("ABC");
        });

    }

    @Test
    void countCountryPerContinent(){

        Map<Continent, Long> result = countryService.countCountryPerContinent();

        assertEquals(7, result.size());
        assertEquals(14, result.get(SOUTH_AMERICA));
        assertEquals(37, result.get(NORTH_AMERICA));
        assertEquals(5, result.get(ANTARCTICA));
        assertEquals(28, result.get(OCEANIA));
        assertEquals(59, result.get(AFRICA));
        assertEquals(50, result.get(ASIA));
        assertEquals(51, result.get(EUROPE));

    }

    @Test
    void getCountryStartingByAsString(){

        String result = countryService.getCountryStartingByAsString('R');
        assertFalse(result.isEmpty());
        assertEquals("Reunion, Romania, Russian Federation, Rwanda", result);

        result = countryService.getCountryStartingByAsString('X');
        assertTrue(result.isEmpty());

    }
}

