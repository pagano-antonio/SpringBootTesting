package com.SpringPerfAdvisor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;



// database reale H2 (in memoria)
@SpringBootTest // carica tutto il contesto Spring
@AutoConfigureMockMvc // configura automaticamente MockMvc per simulare richieste HTTP ai controller
public class TestWithSpringBootTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CountryRepository countryRepository;

	@BeforeEach
	public void setup() {
		countryRepository.deleteAll();
		Country country = new Country("Italy", 1212);
		countryRepository.save(country);
	}

	@Test
	public void testGetCountryById() throws Exception {
		mockMvc.perform(get("/api/cercaid/1")).andExpect(status().isOk());
	}

	@Test
	public void testGetCountryByName() throws Exception {
		mockMvc.perform(get("/api/cercanome/Italy")).andExpect(status().isOk());
	}
}
