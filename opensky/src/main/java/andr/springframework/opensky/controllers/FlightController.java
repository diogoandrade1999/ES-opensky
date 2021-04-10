package andr.springframework.opensky.controllers;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import andr.springframework.opensky.domains.Flight;
import andr.springframework.opensky.serializers.FlightSerializer;
import andr.springframework.opensky.services.AirportService;
import andr.springframework.opensky.services.FlightService;

@Controller
@RequestMapping("flights")
public class FlightController {

    @Autowired
    private FlightService flightService;

    @Autowired
    private AirportService airportService;

    /*
     * @Autowired private KafkaTemplate<String, String> kafkaTemplate;
     * 
     * public void sendMessage(String msg) { kafkaTemplate.send("baeldung", msg); }
     */

    @RequestMapping(value = "/{icao}", method = RequestMethod.GET)
    public String flights(@PathVariable String icao, Model model) {
        Long[] times = getTimes(0);

        List<Flight> flights = flightService.getEstDepartureAirport(icao, times[0], times[1]);
        if (flights.size() != 0) {
            model.addAttribute("departureFlights", flights);
            model.addAttribute("msgInfo", "Data from database!");
        } else {
            model = callApi(icao, "departure", times, model);
            if (model.getAttribute("msgError") == "") {
                flights = flightService.getEstDepartureAirport(icao, times[0], times[1]);
                model.addAttribute("departureFlights", flights);
            }
            model.addAttribute("msgInfo", "Data from API!");
        }

        flights = flightService.getEstArrivalAirport(icao, times[0], times[1]);
        if (flights.size() != 0) {
            model.addAttribute("arrivalFlights", flights);
            model.addAttribute("msgInfo", "Data from database!");
        } else {
            model = callApi(icao, "arrival", times, model);
            if (model.getAttribute("msgError") == "") {
                flights = flightService.getEstArrivalAirport(icao, times[0], times[1]);
                model.addAttribute("arrivalFlights", flights);
            }
            model.addAttribute("msgInfo", "Data from API!");
        }
        return "fragments/flights :: flights";
    }

    private Model callApi(String icao, String type, Long[] times, Model model) {
        try {
            String url = "https://opensky-network.org/api/flights/" + type + "?airport=" + icao + "&begin=" + times[0]
                    + "&end=" + times[1];
            RestTemplate restTemplate = new RestTemplate();
            FlightSerializer[] flights = restTemplate.getForObject(url, FlightSerializer[].class);

            model.addAttribute("msgError", "Not Found Flights!");

            if (flights.length != 0) {
                model.addAttribute("msgError", "");
                List<String> listIds = airportService.getAllIds();
                for (FlightSerializer f : flights) {
                    if (listIds.contains(f.getEstDepartureAirport()) && listIds.contains(f.getEstArrivalAirport())) {
                        Flight flight = new Flight(f.getFirstSeen(), f.getLastSeen(), f.getEstDepartureAirport(),
                                f.getEstArrivalAirport(), f.getCallsign());
                        flightService.saveFlight(flight);
                    }
                }
            }
        } catch (Exception e) {
            model.addAttribute("msgError", "Error on load data!");
        }
        return model;
    }

    public Long[] getTimes(int diff) {
        // today
        Calendar calendar = Calendar.getInstance();

        long initTime = 0;
        long finishTime = 0;
        if (diff >= 0) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            initTime = calendar.getTime().getTime() / 1000L;

            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 59);
            calendar.add(Calendar.DAY_OF_MONTH, diff);
            finishTime = calendar.getTime().getTime() / 1000L;
        } else {
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 59);
            finishTime = calendar.getTime().getTime() / 1000L;

            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.add(Calendar.DAY_OF_MONTH, diff);
            initTime = calendar.getTime().getTime() / 1000L;
        }

        Long[] times = { initTime, finishTime };
        return times;
    }
}
