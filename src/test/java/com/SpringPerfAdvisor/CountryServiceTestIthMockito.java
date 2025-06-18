package com.SpringPerfAdvisor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Estendiamo con MockitoExtension per abilitare le annotazioni di Mockito con JUnit 5
@ExtendWith(MockitoExtension.class)
public class CountryServiceTestIthMockito {

    // InjectMocks inserisce automaticamente i mock nei campi della classe da testare
    @InjectMocks
    private CountryServiceImpl countryService;

    // Creiamo un mock per l'interfaccia CountryRepository
    @Mock
    private CountryRepository countryRepository;

    // Oggetti Country da usare nei test
    private Country country1;
    private Country country2;

    @BeforeEach
    void setUp() {
        // Creazione oggetti di test prima di ogni metodo
        country1 = new Country();
        country1.setId(1);
        country1.setCountryName("Italy");

        country2 = new Country();
        country2.setId(2);
        country2.setCountryName("Germany");
    }

    @Test
    @DisplayName("Test findCountryById - Country trovato")
    void testFindCountryById_Found() {
        // Simuliamo il comportamento del repository quando viene cercato l'ID 1
        when(countryRepository.findById(1)).thenReturn(Optional.of(country1));

        // Chiamata al metodo del service
        Optional<Country> foundCountry = countryService.findCountryById(1);

        // Verifiche
        assertTrue(foundCountry.isPresent(), "Il paese  trovato");
        assertEquals("Italy", foundCountry.get().getCountryName(), "Il nome del paese è 'Italy'");
        verify(countryRepository, times(1)).findById(1);//Verifica che il metodo findById(1) sia stato chiamato esattamente una volta sul mock countryRepository.
        
    }

    @Test
    @DisplayName("Test findCountryById - Country non trovato")
    void testFindCountryById_NotFound() {
        // Simuliamo il comportamento quando il paese non esiste
        when(countryRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Country> foundCountry = countryService.findCountryById(99);

        assertFalse(foundCountry.isPresent(), "Il paese non dovrebbe essere trovato");
        verify(countryRepository, times(1)).findById(99);
    }

    @Test
    @DisplayName("Test findCountriesByName")
    void testFindCountriesByName() {
        // Simuliamo il comportamento per il nome "Italy"
        when(countryRepository.findByCountryName("Italy")).thenReturn(Arrays.asList(country1));

        List<Country> countries = countryService.findCountriesByName("Italy");

        assertEquals(1, countries.size(), "Dovrebbe restituire un solo paese");
        assertEquals("Italy", countries.get(0).getCountryName(), "Il nome dovrebbe essere 'Italy'");
        verify(countryRepository, times(1)).findByCountryName("Italy");
    }

    @Test
    @DisplayName("Test findAllCountries")
    void testFindAllCountries() {
        // Simuliamo il comportamento di findAll()
        when(countryRepository.findAll()).thenReturn(Arrays.asList(country1, country2));

        Iterable<Country> countries = countryService.findAllCountries();
        List<Country> countryList = (List<Country>) countries;

        assertEquals(2, countryList.size(), "Dovrebbe restituire due paesi");
        verify(countryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test findCountriesByNameLike")
    void testFindCountriesByNameLike() {
        // Simuliamo il comportamento del metodo personalizzato "like"
        when(countryRepository.SelByDescrizioneLike("Ita")).thenReturn(Arrays.asList(country1));

        List<Country> countries = countryService.findCountriesByNameLike("Ita");

        assertEquals(1, countries.size(), "Dovrebbe trovare un solo paese che corrisponde");
        assertEquals("Italy", countries.get(0).getCountryName(), "Il nome del paese dovrebbe essere 'Italy'");
        verify(countryRepository, times(1)).SelByDescrizioneLike("Ita");
    }

    @Test
    @DisplayName("Test findCountriesByNameLikeJPQL")
    void testFindCountriesByNameLikeJPQL() {
        // Simuliamo il comportamento JPQL che reindirizza alla stessa funzione
        when(countryRepository.SelByDescrizioneLike("Germ")).thenReturn(Arrays.asList(country2));

        List<Country> countries = countryService.findCountriesByNameLikeJPQL("Germ");

        assertEquals(1, countries.size(), "Dovrebbe trovare un solo paese");
        assertEquals("Germany", countries.get(0).getCountryName(), "Il nome dovrebbe essere 'Germany'");
        verify(countryRepository, times(1)).SelByDescrizioneLike("Germ");
    }

    @Test
    @DisplayName("Test deleteCountryByName")
    void testDeleteCountryByName() {
        String countryToDelete = "France";

        // Chiamata al metodo di eliminazione
        countryService.deleteCountryByName(countryToDelete);

        // Verifica che il metodo DelRowCountryName sia stato invocato correttamente
        verify(countryRepository, times(1)).DelRowCountryName(countryToDelete);
    }

    @Test
    @DisplayName("Test findCountriesByName - Nessuna corrispondenza")
    void testFindCountriesByName_NoMatch() {
        // Simuliamo il caso in cui non ci siano paesi con quel nome
        when(countryRepository.findByCountryName("NonExistent")).thenReturn(Collections.emptyList());

        List<Country> countries = countryService.findCountriesByName("NonExistent");

        assertTrue(countries.isEmpty(), "Dovrebbe restituire una lista vuota");
        verify(countryRepository, times(1)).findByCountryName("NonExistent");
    }

    @Test
    @DisplayName("Test findCountriesByNameLike - Nessuna corrispondenza")
    void testFindCountriesByNameLike_NoMatch() {
        // Simuliamo il caso in cui non ci siano corrispondenze con il like
        when(countryRepository.SelByDescrizioneLike("xyz")).thenReturn(Collections.emptyList());

        List<Country> countries = countryService.findCountriesByNameLike("xyz");

        assertTrue(countries.isEmpty(), "Dovrebbe restituire una lista vuota se non ci sono corrispondenze");
        verify(countryRepository, times(1)).SelByDescrizioneLike("xyz");
    }
}
