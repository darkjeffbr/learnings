package com.atecubanos.service;

import com.atecubanos.model.Continent;
import com.atecubanos.model.Country;
import com.atecubanos.model.CountryDTO;

import static com.atecubanos.model.Continent.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CountryService {

    List<Country> countries = new LinkedList<>();

    public CountryService(){
        countries.add(new Country("Afghanistan", ASIA));
        countries.add(new Country("Albania", EUROPE));
        countries.add(new Country("Algeria", AFRICA));
        countries.add(new Country("American Samoa", OCEANIA));
        countries.add(new Country("Andorra", EUROPE));
        countries.add(new Country("Angola", AFRICA));
        countries.add(new Country("Anguilla", NORTH_AMERICA));
        countries.add(new Country("Antarctica", ANTARCTICA));
        countries.add(new Country("Antigua and Barbuda", NORTH_AMERICA));
        countries.add(new Country("Argentina", SOUTH_AMERICA));
        countries.add(new Country("Armenia", ASIA));
        countries.add(new Country("Aruba", NORTH_AMERICA));
        countries.add(new Country("Australia", OCEANIA));
        countries.add(new Country("Austria", EUROPE));
        countries.add(new Country("Azerbaijan", ASIA));
        countries.add(new Country("Bahamas", NORTH_AMERICA));
        countries.add(new Country("Bahrain", ASIA));
        countries.add(new Country("Bangladesh", ASIA));
        countries.add(new Country("Barbados", NORTH_AMERICA));
        countries.add(new Country("Belarus", EUROPE));
        countries.add(new Country("Belgium", EUROPE));
        countries.add(new Country("Belize", NORTH_AMERICA));
        countries.add(new Country("Benin", AFRICA));
        countries.add(new Country("Bermuda", NORTH_AMERICA));
        countries.add(new Country("Bhutan", ASIA));
        countries.add(new Country("Bolivia", SOUTH_AMERICA));
        countries.add(new Country("Bosnia and Herzegovina", EUROPE));
        countries.add(new Country("Botswana", AFRICA));
        countries.add(new Country("Bouvet Island", ANTARCTICA));
        countries.add(new Country("Brazil", SOUTH_AMERICA));
        countries.add(new Country("British Indian Ocean Territory", AFRICA));
        countries.add(new Country("Brunei", ASIA));
        countries.add(new Country("Bulgaria", EUROPE));
        countries.add(new Country("Burkina Faso", AFRICA));
        countries.add(new Country("Burundi", AFRICA));
        countries.add(new Country("Cambodia", ASIA));
        countries.add(new Country("Cameroon", AFRICA));
        countries.add(new Country("Canada", NORTH_AMERICA));
        countries.add(new Country("Cape Verde", AFRICA));
        countries.add(new Country("Cayman Islands", NORTH_AMERICA));
        countries.add(new Country("Central African Republic", AFRICA));
        countries.add(new Country("Chad", AFRICA));
        countries.add(new Country("Chile", SOUTH_AMERICA));
        countries.add(new Country("China", ASIA));
        countries.add(new Country("Christmas Island", OCEANIA));
        countries.add(new Country("Cocos (Keeling) Islands", OCEANIA));
        countries.add(new Country("Colombia", SOUTH_AMERICA));
        countries.add(new Country("Comoros", AFRICA));
        countries.add(new Country("Congo", AFRICA));
        countries.add(new Country("Cook Islands", OCEANIA));
        countries.add(new Country("Costa Rica", NORTH_AMERICA));
        countries.add(new Country("Croatia", EUROPE));
        countries.add(new Country("Cuba", NORTH_AMERICA));
        countries.add(new Country("Cyprus", ASIA));
        countries.add(new Country("Czech Republic", EUROPE));
        countries.add(new Country("Denmark", EUROPE));
        countries.add(new Country("Djibouti", AFRICA));
        countries.add(new Country("Dominica", NORTH_AMERICA));
        countries.add(new Country("Dominican Republic", NORTH_AMERICA));
        countries.add(new Country("East Timor", ASIA));
        countries.add(new Country("Ecuador", SOUTH_AMERICA));
        countries.add(new Country("Egypt", AFRICA));
        countries.add(new Country("El Salvador", NORTH_AMERICA));
        countries.add(new Country("England", EUROPE));
        countries.add(new Country("Equatorial Guinea", AFRICA));
        countries.add(new Country("Eritrea", AFRICA));
        countries.add(new Country("Estonia", EUROPE));
        countries.add(new Country("Ethiopia", AFRICA));
        countries.add(new Country("Falkland Islands", SOUTH_AMERICA));
        countries.add(new Country("Faroe Islands", EUROPE));
        countries.add(new Country("Fiji Islands", OCEANIA));
        countries.add(new Country("Finland", EUROPE));
        countries.add(new Country("France", EUROPE));
        countries.add(new Country("French Guiana", SOUTH_AMERICA));
        countries.add(new Country("French Polynesia", OCEANIA));
        countries.add(new Country("French Southern territories", ANTARCTICA));
        countries.add(new Country("Gabon", AFRICA));
        countries.add(new Country("Gambia", AFRICA));
        countries.add(new Country("Georgia", ASIA));
        countries.add(new Country("Germany", EUROPE));
        countries.add(new Country("Ghana", AFRICA));
        countries.add(new Country("Gibraltar", EUROPE));
        countries.add(new Country("Greece", EUROPE));
        countries.add(new Country("Greenland", NORTH_AMERICA));
        countries.add(new Country("Grenada", NORTH_AMERICA));
        countries.add(new Country("Guadeloupe", NORTH_AMERICA));
        countries.add(new Country("Guam", OCEANIA));
        countries.add(new Country("Guatemala", NORTH_AMERICA));
        countries.add(new Country("Guinea", AFRICA));
        countries.add(new Country("Guinea-Bissau", AFRICA));
        countries.add(new Country("Guyana", SOUTH_AMERICA));
        countries.add(new Country("Haiti", NORTH_AMERICA));
        countries.add(new Country("Heard Island and McDonald Islands", ANTARCTICA));
        countries.add(new Country("Holy See (Vatican City State)", EUROPE));
        countries.add(new Country("Honduras", NORTH_AMERICA));
        countries.add(new Country("Hong Kong", ASIA));
        countries.add(new Country("Hungary", EUROPE));
        countries.add(new Country("Iceland", EUROPE));
        countries.add(new Country("India", ASIA));
        countries.add(new Country("Indonesia", ASIA));
        countries.add(new Country("Iran", ASIA));
        countries.add(new Country("Iraq", ASIA));
        countries.add(new Country("Ireland", EUROPE));
        countries.add(new Country("Israel", ASIA));
        countries.add(new Country("Italy", EUROPE));
        countries.add(new Country("Ivory Coast", AFRICA));
        countries.add(new Country("Jamaica", NORTH_AMERICA));
        countries.add(new Country("Japan", ASIA));
        countries.add(new Country("Jordan", ASIA));
        countries.add(new Country("Kazakhstan", ASIA));
        countries.add(new Country("Kenya", AFRICA));
        countries.add(new Country("Kiribati", OCEANIA));
        countries.add(new Country("Kuwait", ASIA));
        countries.add(new Country("Kyrgyzstan", ASIA));
        countries.add(new Country("Laos", ASIA));
        countries.add(new Country("Latvia", EUROPE));
        countries.add(new Country("Lebanon", ASIA));
        countries.add(new Country("Lesotho", AFRICA));
        countries.add(new Country("Liberia", AFRICA));
        countries.add(new Country("Libyan Arab Jamahiriya", AFRICA));
        countries.add(new Country("Liechtenstein", EUROPE));
        countries.add(new Country("Lithuania", EUROPE));
        countries.add(new Country("Luxembourg", EUROPE));
        countries.add(new Country("Macao", ASIA));
        countries.add(new Country("North Macedonia", EUROPE));
        countries.add(new Country("Madagascar", AFRICA));
        countries.add(new Country("Malawi", AFRICA));
        countries.add(new Country("Malaysia", ASIA));
        countries.add(new Country("Maldives", ASIA));
        countries.add(new Country("Mali", AFRICA));
        countries.add(new Country("Malta", EUROPE));
        countries.add(new Country("Marshall Islands", OCEANIA));
        countries.add(new Country("Martinique", NORTH_AMERICA));
        countries.add(new Country("Mauritania", AFRICA));
        countries.add(new Country("Mauritius", AFRICA));
        countries.add(new Country("Mayotte", AFRICA));
        countries.add(new Country("Mexico", NORTH_AMERICA));
        countries.add(new Country("Micronesia, Federated States of", OCEANIA));
        countries.add(new Country("Moldova", EUROPE));
        countries.add(new Country("Monaco", EUROPE));
        countries.add(new Country("Mongolia", ASIA));
        countries.add(new Country("Montenegro", EUROPE));
        countries.add(new Country("Montserrat", NORTH_AMERICA));
        countries.add(new Country("Morocco", AFRICA));
        countries.add(new Country("Mozambique", AFRICA));
        countries.add(new Country("Myanmar", ASIA));
        countries.add(new Country("Namibia", AFRICA));
        countries.add(new Country("Nauru", OCEANIA));
        countries.add(new Country("Nepal", ASIA));
        countries.add(new Country("Netherlands", EUROPE));
        countries.add(new Country("Netherlands Antilles", NORTH_AMERICA));
        countries.add(new Country("New Caledonia", OCEANIA));
        countries.add(new Country("New Zealand", OCEANIA));
        countries.add(new Country("Nicaragua", NORTH_AMERICA));
        countries.add(new Country("Niger", AFRICA));
        countries.add(new Country("Nigeria", AFRICA));
        countries.add(new Country("Niue", OCEANIA));
        countries.add(new Country("Norfolk Island", OCEANIA));
        countries.add(new Country("North Korea", ASIA));
        countries.add(new Country("Northern Ireland", EUROPE));
        countries.add(new Country("Northern Mariana Islands", OCEANIA));
        countries.add(new Country("Norway", EUROPE));
        countries.add(new Country("Oman", ASIA));
        countries.add(new Country("Pakistan", ASIA));
        countries.add(new Country("Palau", OCEANIA));
        countries.add(new Country("Palestine", ASIA));
        countries.add(new Country("Panama", NORTH_AMERICA));
        countries.add(new Country("Papua New Guinea", OCEANIA));
        countries.add(new Country("Paraguay", SOUTH_AMERICA));
        countries.add(new Country("Peru", SOUTH_AMERICA));
        countries.add(new Country("Philippines", ASIA));
        countries.add(new Country("Pitcairn", OCEANIA));
        countries.add(new Country("Poland", EUROPE));
        countries.add(new Country("Portugal", EUROPE));
        countries.add(new Country("Puerto Rico", NORTH_AMERICA));
        countries.add(new Country("Qatar", ASIA));
        countries.add(new Country("Reunion", AFRICA));
        countries.add(new Country("Romania", EUROPE));
        countries.add(new Country("Russian Federation", EUROPE));
        countries.add(new Country("Rwanda", AFRICA));
        countries.add(new Country("Saint Helena", AFRICA));
        countries.add(new Country("Saint Kitts and Nevis", NORTH_AMERICA));
        countries.add(new Country("Saint Lucia", NORTH_AMERICA));
        countries.add(new Country("Saint Pierre and Miquelon", NORTH_AMERICA));
        countries.add(new Country("Saint Vincent and the Grenadines", NORTH_AMERICA));
        countries.add(new Country("Samoa", OCEANIA));
        countries.add(new Country("San Marino", EUROPE));
        countries.add(new Country("Sao Tome and Principe", AFRICA));
        countries.add(new Country("Saudi Arabia", ASIA));
        countries.add(new Country("Scotland", EUROPE));
        countries.add(new Country("Senegal", AFRICA));
        countries.add(new Country("Serbia", EUROPE));
        countries.add(new Country("Seychelles", AFRICA));
        countries.add(new Country("Sierra Leone", AFRICA));
        countries.add(new Country("Singapore", ASIA));
        countries.add(new Country("Slovakia", EUROPE));
        countries.add(new Country("Slovenia", EUROPE));
        countries.add(new Country("Solomon Islands", OCEANIA));
        countries.add(new Country("Somalia", AFRICA));
        countries.add(new Country("South Africa", AFRICA));
        countries.add(new Country("South Georgia and the South Sandwich Islands", ANTARCTICA));
        countries.add(new Country("South Korea", ASIA));
        countries.add(new Country("South Sudan", AFRICA));
        countries.add(new Country("Spain", EUROPE));
        countries.add(new Country("Sri Lanka", ASIA));
        countries.add(new Country("Sudan", AFRICA));
        countries.add(new Country("Suriname", SOUTH_AMERICA));
        countries.add(new Country("Svalbard and Jan Mayen", EUROPE));
        countries.add(new Country("Swaziland", AFRICA));
        countries.add(new Country("Sweden", EUROPE));
        countries.add(new Country("Switzerland", EUROPE));
        countries.add(new Country("Syria", ASIA));
        countries.add(new Country("Tajikistan", ASIA));
        countries.add(new Country("Tanzania", AFRICA));
        countries.add(new Country("Thailand", ASIA));
        countries.add(new Country("The Democratic Republic of Congo", AFRICA));
        countries.add(new Country("Togo", AFRICA));
        countries.add(new Country("Tokelau", OCEANIA));
        countries.add(new Country("Tonga", OCEANIA));
        countries.add(new Country("Trinidad and Tobago", NORTH_AMERICA));
        countries.add(new Country("Tunisia", AFRICA));
        countries.add(new Country("Turkey", ASIA));
        countries.add(new Country("Turkmenistan", ASIA));
        countries.add(new Country("Turks and Caicos Islands", NORTH_AMERICA));
        countries.add(new Country("Tuvalu", OCEANIA));
        countries.add(new Country("Uganda", AFRICA));
        countries.add(new Country("Ukraine", EUROPE));
        countries.add(new Country("United Arab Emirates", ASIA));
        countries.add(new Country("United Kingdom", EUROPE));
        countries.add(new Country("United States", NORTH_AMERICA));
        countries.add(new Country("United States Minor Outlying Islands", OCEANIA));
        countries.add(new Country("Uruguay", SOUTH_AMERICA));
        countries.add(new Country("Uzbekistan", ASIA));
        countries.add(new Country("Vanuatu", OCEANIA));
        countries.add(new Country("Venezuela", SOUTH_AMERICA));
        countries.add(new Country("Vietnam", ASIA));
        countries.add(new Country("Virgin Islands, British", NORTH_AMERICA));
        countries.add(new Country("Virgin Islands, U.S.", NORTH_AMERICA));
        countries.add(new Country("Wales", EUROPE));
        countries.add(new Country("Wallis and Futuna", OCEANIA));
        countries.add(new Country("Western Sahara", AFRICA));
        countries.add(new Country("Yemen", ASIA));
        countries.add(new Country("Zambia", AFRICA));
        countries.add(new Country("Zimbabwe", AFRICA));
    }

    /**
     * Get first three countries in upper case
     *
     * @return
     */
    public List<String> getCountryInUpperCase(){
        return null;
    }

    /**
     * Skip the first three countries, and get the next three countries as DTO
     *
     * @return
     */
    public List<CountryDTO> getCountriesAsDTO(){
        return null;
    }

    /**
     * Get country starting by the given letter
     *
     * @param firstLetter
     * @return
     */
    public List<String> getCountryStartingBy(Character firstLetter){
        return null;
    }

    /**
     * Count country starting by the given letter
     *
     * @param firstLetter
     * @return
     */
    public long countCountryStartingBy(Character firstLetter){
        return 0;
    }

    /**
     * Get continents that contains countries starting by the given letter
     *
     * @param firstLetter
     * @return
     */
    public List<String> getContinentWhereCountryStartsBy(Character firstLetter){
        return null;
    }

    /**
     * Search for a country with the given name, in case it is found, return it.
     * In case it is not found than return CountryNotFoundException
     *
     * @param country
     * @return
     */
    public CountryDTO getCountryByName(String country){
        return null;
    }

    /**
     * Count the number of countries in each continent.
     * Return a map with the relation CONTINENT -> SUM OF COUNTRIES
     * @return
     */
    public Map<Continent, Long> countCountryPerContinent(){
        return null;
    }

    /**
     * Return a comma-separated list of all countries that starts with the given letter
     * @return
     */
    public String getCountryStartingByAsString(Character firstLetter){
        return null;
    }

}
