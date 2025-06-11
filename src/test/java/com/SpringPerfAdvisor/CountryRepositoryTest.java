package com.SpringPerfAdvisor;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/*
 * ho usato il database H2 per poter testare velocemente
 * il repository, senza creare un db mysql specifico per
 * i test
 */
@DataJpaTest
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryRepository;

    @Test
    public void testSaveAndFindByCountryName() {
        // Arrange
        Country italy = new Country();
        italy.setCountryName("Italy");
        countryRepository.save(italy);

        // Act
        List<Country> results = countryRepository.findByCountryName("Italy");

        // Assert
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getCountryName()).isEqualTo("Italy");
    }

    @Test
    public void testSelByDescrizioneLikeJPQL() {
        // Arrange
        Country france = new Country();
        france.setCountryName("France");
        countryRepository.save(france);

        // Act
        List<Country> results = countryRepository.SelByDescrizioneLikeJPQL("%Fran%");

        // Assert
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getCountryName()).contains("Fran");
    }

    @Test
    public void testDeleteByCountryName() {
        // Arrange
        Country germany = new Country();
        germany.setCountryName("Germany");
        countryRepository.save(germany);

        // Act
        countryRepository.DelRowCountryName("Germany");
        List<Country> results = countryRepository.findByCountryName("Germany");

        // Assert
        assertThat(results).isEmpty();
    }
}


