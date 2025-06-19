package com.SpringPerfAdvisor;


	import static org.assertj.core.api.Assertions.assertThat;
	import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
	import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

	import java.util.Arrays;

	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.test.context.ActiveProfiles;
	import org.springframework.test.web.servlet.MockMvc;

	@SpringBootTest
	@ActiveProfiles("test")
	@AutoConfigureMockMvc
	public class TestWithActiveProfiles {

	    @Autowired
	    private MockMvc mockMvc;

	    @Autowired
	    private CountryRepository countryRepository;

	    @BeforeEach
	    void setUp() {
	        countryRepository.deleteAll();
	        countryRepository.saveAll(Arrays.asList(
	            new Country("Italia", 60000000),
	            new Country("Francia", 67000000)
	        ));
	    }

	    @Test
	    void testListaCompleta() throws Exception {
	        mockMvc.perform(get("/api/all"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[0].countryName").value("Italia"))
	            .andExpect(jsonPath("$[1].countryName").value("Francia"));
	    }

	    @Test
	    void testCercaPerNome() throws Exception {
	        mockMvc.perform(get("/api/cercanome/Italia"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[0].population").value(60000000));
	    }

	    @Test
	    void testCercaLikeJPQL() throws Exception {
	        mockMvc.perform(get("/api/cercanomelikejpql/Fran%"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$[0].countryName").value("Francia"));
	    }
	}
