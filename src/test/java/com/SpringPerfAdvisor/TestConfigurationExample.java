package com.SpringPerfAdvisor;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;


@SpringBootTest
@Import(TestConfigurationExample.CountryTestConfig.class)
public class TestConfigurationExample {

	
	@Autowired
    private CountryService countryService;

    @MockBean
    private CountryRepository countryRepository;

    @Test
    void testFindCountriesByName() {
        List<Country> mockCountries = List.of(
            new Country(1, "Italy", 60000),
            new Country(2, "Italy - South", 80000)
        );

        Mockito.when(countryRepository.findByCountryName("Italy"))
               .thenReturn(mockCountries);

        List<Country> result = countryService.findCountriesByName("Italy");

        assertEquals(2, result.size());
        assertEquals("Italy", result.get(0).getCountryName());
        assertEquals("Italy - South", result.get(1).getCountryName());
    }

    @TestConfiguration
    static class CountryTestConfig {
        @Bean
        public CountryService countryService() {
            return new CountryServiceImpl(); // verrà iniettato con @MockBean
        }
    }
}
