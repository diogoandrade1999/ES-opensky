package andr.springframework.opensky.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
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

    /*@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String msg) {
        kafkaTemplate.send("baeldung", msg);
    }*/

    @RequestMapping(value = "/{icao}", method = RequestMethod.GET)
    public String cities(@PathVariable String icao, Model model) {
        model = callApi(icao, "departure", model);
        model = callApi(icao, "arrival", model);
        return "fragments/flights :: flights";
    }

    private Model callApi(String icao, String type, Model model) {
        try {
            Date date = new Date();
            long actualTime = date.getTime() / 1000L;
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -7);
            long pastTime = calendar.getTime().getTime() / 1000L;
            String url = "https://opensky-network.org/api/flights/" + type + "?airport=" + icao + "&begin=" + pastTime
                    + "&end=" + actualTime;
            System.out.println(url);
            RestTemplate restTemplate = new RestTemplate();
            FlightSerializer[] flights = restTemplate.getForObject(url, FlightSerializer[].class);

            model.addAttribute("msg", "Not Found Flights!");
            assert flights != null;

            if (flights.length != 0) {
                model.addAttribute("msg", "");
                model.addAttribute(type + "Flights", flights);
            } else {
                List<FlightSerializer> f = new ArrayList<>();
                model.addAttribute(type + "Flights", f);
            }
        } catch (Exception e) {
            model.addAttribute("msg", "Error on load data!");
            System.out.println("Error load data!");
            System.out.println(e);
        }
        return model;
    }
}
