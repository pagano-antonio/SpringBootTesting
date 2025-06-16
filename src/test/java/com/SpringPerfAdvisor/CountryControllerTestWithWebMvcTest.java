package com.SpringPerfAdvisor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CountryController.class) // avviare solo il contesto Web (quindi niente DB o
//servizio reali) e caricare solo il controller CountryController.
public class CountryControllerTestWithWebMvcTest {
	
	// MockMvc è un oggetto che simula una chiamata HTTP al controller.
	@Autowired
	private MockMvc mockMvc;

	// @MockBean dice a Spring di creare un "finto" bean per CountryService
	@MockBean
	private CountryService countryService;

	@Test
	public void testRicercaPerId() throws Exception {
		Country mockCountry = new Country("Italy", 1232);

		// Quando qualcuno chiama countryService.findCountryById(1), restituisci un
		// Country finto che ho creato io (mockCountry)”
		when(countryService.findCountryById(1)).thenReturn(Optional.of(mockCountry));

		// Simula una chiamata GET a /api/cercaid/1
		//Verifica che la risposta abbia codice HTTP 200 OK
		mockMvc.perform(get("/api/cercaid/1")).andExpect(status().isOk());
	}
}