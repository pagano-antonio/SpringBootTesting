package com.SpringPerfAdvisor;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/api")
public class CountryController {
	
	 // Inietta l'interfaccia del servizio, Spring troverà l'implementazione CountryServiceImpl
    @Autowired
    private CountryService countryService; // Ora inietta CountryService

    // ------------------- Ricerca Per ID ------------------------------------
    @RequestMapping(value = "/cercaid/{countryId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Optional<Country>> ricercaPerId(@PathVariable("countryId") Integer countryId) {
        // Chiama il metodo del servizio
        Optional<Country> res = countryService.findCountryById(countryId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // ------------------- Ricerca Per Nome ------------------------------------
    @RequestMapping(value = "/cercanome/{countryName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Country>> ricercaPerNome(@PathVariable("countryName") String countryName) {
        System.out.println("****** dentro *******");
        System.out.println("countryName:" + countryName);
        // Chiama il metodo del servizio
        List<Country> res = countryService.findCountriesByName(countryName);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // ------------------- Tutti i country ------------------------------------
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Iterable<Country>> listaCompleta() {
        // Chiama il metodo del servizio
        Iterable<Country> res = countryService.findAllCountries();
        for (Country country : res) {
            System.out.println("country.getCountryName():" + country.getCountryName());
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // ------------------- Country per nome LIKE ------------------------------------
    @RequestMapping(value = "/cercanomelike/{countryName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Country>> cercanomelike(@PathVariable("countryName") String countryName) {
        // Chiama il metodo del servizio
        List<Country> res = countryService.findCountriesByNameLike(countryName);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // ------------------- Country per nome LIKE (JPQL) ------------------------------------
    @RequestMapping(value = "/cercanomelikejpql/{countryName}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Country>> cercanomelikeJPQL(@PathVariable("countryName") String countryName) {
        // Chiama il metodo del servizio
        List<Country> res = countryService.findCountriesByNameLikeJPQL(countryName);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    // ------------------- Elimina per nome ------------------------------------
    @RequestMapping(value = "/delete/{countryName}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<String> deleteNome(@PathVariable("countryName") String countryName) {
        // Chiama il metodo del servizio
        countryService.deleteCountryByName(countryName);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
