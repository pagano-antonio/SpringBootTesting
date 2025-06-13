package com.SpringPerfAdvisor;




import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // Carica l'intero contesto dell'applicazione Spring
@ActiveProfiles("test") // Attiva il profilo di test che userà H2
@Transactional // Ogni test viene eseguito in una transazione e poi fa rollback
class CountryServiceTest2 {

    @Autowired
    private CountryService countryService; // Inietta l'implementazione reale del servizio

    @Autowired
    private CountryRepository countryRepository; // Inietta il repository per preparare i dati

    // Dati di test comuni
    private Country italy;
    private Country france;
    private Country germany;

    @BeforeEach
    void setUp() {
        // Pulisci il database prima di ogni test.
        // @Transactional con rollback di solito è sufficiente, ma una pulizia esplicita
        // garantisce uno stato iniziale pulito anche se il rollback non fosse perfetto
        // o per debug.
        countryRepository.deleteAll();
        countryRepository.flush(); // Assicura che le operazioni precedenti siano scritte

        italy = new Country("Italy", 1212);
        france = new Country("France", 423432);
        germany = new Country("Germany", 234234);

        // Salva i dati iniziali nel database
        countryRepository.saveAll(List.of(italy, france, germany));
    }

    @Test
    @DisplayName("Dovrebbe trovare un paese per ID")
    void givenCountryInDb_whenFindCountryById_thenReturnCountry() {
        // When
        Optional<Country> foundCountry = countryService.findCountryById(italy.getId());

        // Then
        assertThat(foundCountry).isPresent();
        assertThat(foundCountry.get().getCountryName()).isEqualTo("Italy");
    }

    @Test
    @DisplayName("Dovrebbe ritornare vuoto se il paese per ID non esiste")
    void givenNonExistingId_whenFindCountryById_thenReturnEmptyOptional() {
        // When
        Optional<Country> foundCountry = countryService.findCountryById(999); // ID che non esiste

        // Then
        assertThat(foundCountry).isEmpty();
    }

    @Test
    @DisplayName("Dovrebbe trovare paesi per nome esatto")
    void givenCountriesInDb_whenFindCountriesByName_thenReturnMatchingCountries() {
        // Given: Aggiungi un altro paese con lo stesso nome per testare la lista
        Country anotherItaly = new Country("Italy", 23323);
        countryRepository.save(anotherItaly);

        // When
        List<Country> countries = countryService.findCountriesByName("Italy");

        // Then
        assertThat(countries).isNotNull();
        assertThat(countries.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Dovrebbe ritornare una lista vuota se nessun paese corrisponde al nome esatto")
    void givenNoMatchingName_whenFindCountriesByName_thenReturnEmptyList() {
        // When
        List<Country> countries = countryService.findCountriesByName("Spain");

        // Then
        assertThat(countries).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("Dovrebbe trovare tutti i paesi")
    void givenCountriesInDb_whenFindAllCountries_thenReturnAllCountries() {
        // When
        Iterable<Country> allCountries = countryService.findAllCountries();

        // Then
        assertThat(allCountries).isNotNull();
  }




    @Test
    @DisplayName("Dovrebbe eliminare un paese per nome")
    void givenCountryInDb_whenDeleteCountryByName_thenCountryIsDeleted() {
        // Given
        // "italy" è già salvato in @BeforeEach

        // When
        countryService.deleteCountryByName("Italy");

        // Then
        List<Country> remainingCountries = countryRepository.findAll();
        assertThat(remainingCountries.size()).isEqualTo(2);
 
        // Verifica che "Italy" non esista più
        assertThat(countryRepository.findByCountryName("Italy")).isEmpty();
    }

    @Test
    @DisplayName("Dovrebbe non fare nulla se si tenta di eliminare un paese per nome che non esiste")
    void givenNonExistingCountryName_whenDeleteCountryByName_thenNoCountryIsDeleted() {
        // Given
        long initialCount = countryRepository.count();

        // When
        countryService.deleteCountryByName("NonExistentCountry");

        // Then
        long finalCount = countryRepository.count();
        assertThat(finalCount).isEqualTo(initialCount); // Il numero di paesi non è cambiato
    }
}
