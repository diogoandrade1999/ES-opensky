package andr.springframework.opensky.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import andr.springframework.opensky.services.AirportService;

@Controller
@RequestMapping("airports")
public class AirportController {

    private AirportService airportService;

    @Autowired
    public void setAirportService(AirportService airportService) {
        this.airportService = airportService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String airports2(@RequestParam String keyword, Model model) {
        model.addAttribute("airports", airportService.searchAirports(keyword));
        return "fragments/airports :: airports";
    }
}
