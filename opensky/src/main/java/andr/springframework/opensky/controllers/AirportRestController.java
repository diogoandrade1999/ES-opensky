package andr.springframework.opensky.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import andr.springframework.opensky.domains.Airport;
import andr.springframework.opensky.services.AirportService;

@RestController
@RequestMapping("airports")
public class AirportRestController {

    private AirportService airportService;

    @Autowired
    public void setAirportService(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostMapping
    @ResponseBody
    public List<Airport> airports(@RequestParam(value = "", required = true) String keyword) {
        return airportService.seachAirports(keyword);
    }

}
