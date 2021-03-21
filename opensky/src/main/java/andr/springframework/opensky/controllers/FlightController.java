package andr.springframework.opensky.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import andr.springframework.opensky.serializers.FlightSerializer;

@Controller
@RequestMapping("flights")
public class FlightController {

    @RequestMapping(value = "/{icao}", method = RequestMethod.GET)
    public String cities(@PathVariable String icao, Model model) {
        model.addAttribute("msg", "Error load data!");
        // incomplete
        try {
            String url = "https://opensky-network.org/api/flights/departure?airport=" + icao
                    + "&begin=1615382195&end=1615814231";
            RestTemplate restTemplate = new RestTemplate();
            FlightSerializer[] flights = restTemplate.getForObject(url, FlightSerializer[].class);

            model.addAttribute("msg", "Not Found Flights!");
            assert flights != null;

            if (flights.length != 0) {
                model.addAttribute("msg", "");
                model.addAttribute("flights", flights);
            } else {
                List<FlightSerializer> f = new ArrayList<>();
                model.addAttribute("flights", f);
            }
        } catch (Exception e) {
            System.out.println("Error load data!");
            System.out.println(e);
        }
        return "fragments/flights :: flights";
    }
}
