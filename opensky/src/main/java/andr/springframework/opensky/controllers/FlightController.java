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
public class FlightController {

    @RequestMapping(value = "flights/{code}", method = RequestMethod.GET)
    public String cities(@PathVariable String code, Model model) {
        model.addAttribute("msg", "Error load data!");
        // incomplete
        try {
            String url = "https://opensky-network.org/api/flights/departure?airport=" + code
                    + "&begin=1615382195&end=1615814231";
            RestTemplate restTemplate = new RestTemplate();
            FlightSerializer flights = restTemplate.getForObject(url, FlightSerializer.class);

            model.addAttribute("msg", "Not Found Flights!");
            assert flights != null;

            if (flights.getStatus().equals("ok")) {
                model.addAttribute("flights", flights.getData());
                if (flights.getData().size() != 0)
                    model.addAttribute("msg", "");
            } else {
                List<FlightSerializer> f = new ArrayList<>();
                model.addAttribute("flights", f);
            }
        } catch (Exception e) {
            System.out.println("Error load data!");
        }
        return "flights";
    }
}
