package andr.springframework.opensky.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import andr.springframework.opensky.domains.Airport;
import andr.springframework.opensky.services.AirportService;

@Controller
@RequestMapping("/airports")
public class AirportController {

    @Autowired
    private AirportService airportService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String airportsSearch(@RequestParam String keyword, Model model) {
        model.addAttribute("airports", this.airportService.searchAirports(keyword));
        return "fragments/airports :: airports";
    }

    @RequestMapping(value = "/{icao}", method = RequestMethod.GET)
    public ResponseEntity<Airport> airportByIcao(@PathVariable String icao) {
        Airport airport = this.airportService.getAirportByIcao(icao);
        if (airport == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(airport);
    }
}
