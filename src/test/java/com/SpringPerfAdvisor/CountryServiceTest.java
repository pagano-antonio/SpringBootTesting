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
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

// Use MockitoExtension for JUnit 5 to enable Mockito annotations
@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {
	// InjectMocks automatically injects the mocked dependencies into this instance
    @InjectMocks
    private CountryServiceImpl countryService;

    // Mock the CountryRepository interface
    @Mock
    private CountryRepository countryRepository;

    // A Country object for testing purposes
    private Country country1;
    private Country country2;

    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations if not using @ExtendWith(MockitoExtension.class)
        // MockitoAnnotations.openMocks(this); // Not strictly needed with @ExtendWith

        // Set up common test data before each test method
        country1 = new Country();
        country1.setId(1);
        country1.setCountryName("Italy");

        country2 = new Country();
        country2.setId(2);
        country2.setCountryName("Germany");
    }

    @Test
    @DisplayName("Test findCountryById - Country Found")
    void testFindCountryById_Found() {
        // Mock the behavior of the repository's findById method
        // When findById(1) is called on the mocked repository, return an Optional containing country1
        when(countryRepository.findById(1)).thenReturn(Optional.of(country1));

        // Call the service method
        Optional<Country> foundCountry = countryService.findCountryById(1);

        // Assertions:
        // Verify that the country was found
        assertTrue(foundCountry.isPresent(), "Country should be found");
        // Verify that the found country's name matches
        assertEquals("Italy", foundCountry.get().getCountryName(), "Country name should match 'Italy'");
        // Verify that the repository's findById method was called exactly once with argument 1
        verify(countryRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("Test findCountryById - Country Not Found")
    void testFindCountryById_NotFound() {
        // Mock the behavior for a country not found
        // When findById(99) is called, return an empty Optional
        when(countryRepository.findById(99)).thenReturn(Optional.empty());

        // Call the service method
        Optional<Country> foundCountry = countryService.findCountryById(99);

        // Assertions:
        // Verify that no country was found
        assertFalse(foundCountry.isPresent(), "Country should not be found");
        // Verify that the repository's findById method was called exactly once with argument 99
        verify(countryRepository, times(1)).findById(99);
    }

    @Test
    @DisplayName("Test findCountriesByName")
    void testFindCountriesByName() {
        // Mock the behavior of the repository's findByCountryName method
        // When findByCountryName("Italy") is called, return a list containing country1
        when(countryRepository.findByCountryName("Italy")).thenReturn(Arrays.asList(country1));

        // Call the service method
        List<Country> countries = countryService.findCountriesByName("Italy");

        // Assertions:
        // Verify that the list is not null and contains one element
        assertEquals(1, countries.size(), "Should return one country");
        // Verify that the returned country's name matches
        assertEquals("Italy", countries.get(0).getCountryName(), "Country name should be 'Italy'");
        // Verify that the repository method was called
        verify(countryRepository, times(1)).findByCountryName("Italy");
    }

    @Test
    @DisplayName("Test findAllCountries")
    void testFindAllCountries() {
        // Mock the behavior of the repository's findAll method
        // Return a list containing both country1 and country2
        when(countryRepository.findAll()).thenReturn(Arrays.asList(country1, country2));

        // Call the service method
        Iterable<Country> countries = countryService.findAllCountries();
        List<Country> countryList = (List<Country>) countries; // Cast to List for easy assertion

        // Assertions:
        // Verify that the list contains two elements
        assertEquals(2, countryList.size(), "Should return two countries");
        // Verify that the repository method was called
        verify(countryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test findCountriesByNameLike")
    void testFindCountriesByNameLike() {
        // Mock the behavior for the custom query method
        // When SelByDescrizioneLike("Ita") is called, return a list containing country1
        when(countryRepository.SelByDescrizioneLike("Ita")).thenReturn(Arrays.asList(country1));

        // Call the service method
        List<Country> countries = countryService.findCountriesByNameLike("Ita");

        // Assertions:
        assertEquals(1, countries.size(), "Should return one country matching 'Ita'");
        assertEquals("Italy", countries.get(0).getCountryName(), "Country name should be 'Italy'");
        // Verify that the repository method was called
        verify(countryRepository, times(1)).SelByDescrizioneLike("Ita");
    }

    @Test
    @DisplayName("Test findCountriesByNameLikeJPQL")
    void testFindCountriesByNameLikeJPQL() {
        // As per your service implementation, this method currently calls SelByDescrizioneLike.
        // So, we mock the same repository method.
        when(countryRepository.SelByDescrizioneLike("Germ")).thenReturn(Arrays.asList(country2));

        // Call the service method
        List<Country> countries = countryService.findCountriesByNameLikeJPQL("Germ");

        // Assertions:
        assertEquals(1, countries.size(), "Should return one country matching 'Germ'");
        assertEquals("Germany", countries.get(0).getCountryName(), "Country name should be 'Germany'");
        // Verify that the repository method was called
        verify(countryRepository, times(1)).SelByDescrizioneLike("Germ");
    }

    @Test
    @DisplayName("Test deleteCountryByName")
    void testDeleteCountryByName() {
        String countryToDelete = "France";

        // We don't need to mock a return value for void methods, but we can verify invocation.
        // We ensure that when DelRowCountryName is called, it does nothing or proceeds without error.

        // Call the service method
        countryService.deleteCountryByName(countryToDelete);

        // Assertions:
        // Verify that the repository's DelRowCountryName method was called exactly once with the given argument
        verify(countryRepository, times(1)).DelRowCountryName(countryToDelete);
    }

    @Test
    @DisplayName("Test findCountriesByName - No Match")
    void testFindCountriesByName_NoMatch() {
        // Mock the behavior when no matching countries are found
        when(countryRepository.findByCountryName("NonExistent")).thenReturn(Collections.emptyList());
        System.out.println("prova");
        // Call the service method
        List<Country> countries = countryService.findCountriesByName("NonExistent");

        // Assertions:
        assertTrue(countries.isEmpty(), "Should return an empty list when no matches are found");
        verify(countryRepository, times(1)).findByCountryName("NonExistent");
    }

    @Test
    @DisplayName("Test findCountriesByNameLike - No Match")
    void testFindCountriesByNameLike_NoMatch() {
        // Mock the behavior when no matching countries are found for 'Like' query
        when(countryRepository.SelByDescrizioneLike("xyz")).thenReturn(Collections.emptyList());

        // Call the service method
        List<Country> countries = countryService.findCountriesByNameLike("xyz");

        // Assertions:
        assertTrue(countries.isEmpty(), "Should return an empty list when no 'like' matches are found");
        verify(countryRepository, times(1)).SelByDescrizioneLike("xyz");
    }
}
