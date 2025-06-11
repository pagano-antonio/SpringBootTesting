package com.SpringPerfAdvisor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import necessario per @Transactional

// Implementazione del servizio
@Service // Indica che questa è una classe di servizio Spring
public class CountryServiceImpl implements CountryService {

    @Autowired // Inietta il repository
    private CountryRepository repository;

    @Override
    public Optional<Country> findCountryById(Integer countryId) {
        return repository.findById(countryId);
    }

    @Override
    public List<Country> findCountriesByName(String countryName) {
        return repository.findByCountryName(countryName);
    }

    @Override
    public Iterable<Country> findAllCountries() {
        return repository.findAll();
    }

    @Override
    public List<Country> findCountriesByNameLike(String countryName) {
        return repository.SelByDescrizioneLike(countryName);
    }

    @Override
    public List<Country> findCountriesByNameLikeJPQL(String countryName) {
        // Questa chiamata era SelByDescrizioneLike nel tuo codice originale,
        // che è lo stesso del metodo precedente. Se JPQL implica una query diversa,
        // dovresti definirla nel repository. Per ora, uso lo stesso metodo.
        return repository.SelByDescrizioneLike(countryName);
    }

    @Override
    @Transactional // Necessario per operazioni di modifica (DELETE)
    public void deleteCountryByName(String countryName) {
        repository.DelRowCountryName(countryName);
    }
}
