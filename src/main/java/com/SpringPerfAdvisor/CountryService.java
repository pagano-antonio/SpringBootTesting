package com.SpringPerfAdvisor;
import java.util.List;
import java.util.Optional;

// Interfaccia del servizio per definire i contratti
public interface CountryService {
    Optional<Country> findCountryById(Integer countryId);
    List<Country> findCountriesByName(String countryName);
    Iterable<Country> findAllCountries();
    List<Country> findCountriesByNameLike(String countryName);
    List<Country> findCountriesByNameLikeJPQL(String countryName);
    void deleteCountryByName(String countryName);
}