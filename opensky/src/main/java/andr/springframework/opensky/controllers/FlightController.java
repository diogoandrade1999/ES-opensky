package andr.springframework.opensky.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import andr.springframework.opensky.domains.Flight;
import andr.springframework.opensky.domains.Manager;
import andr.springframework.opensky.serializers.FlightSerializer;
import andr.springframework.opensky.services.AirportService;
import andr.springframework.opensky.services.FlightService;
import andr.springframework.opensky.services.ManagerService;

@Controller
@RequestMapping("/flights")
public class FlightController {

    private Logger log = LogManager.getLogger(FlightController.class);
    // ! ---------- trocar para 7 (1 semana) ---------------
    private int daysRange = -50;

    @Autowired
    private FlightService flightService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private AirportService airportService;

    /*
     * @Autowired private KafkaTemplate<String, String> kafkaTemplate;
     * 
     * public void sendMessage(String msg) { kafkaTemplate.send("baeldung", msg); }
     */

    @RequestMapping(value = "/{icao}", method = RequestMethod.GET)
    public String flights(@PathVariable String icao, Model model) {
        // unix time of last daysRange days
        List<Long> lastDaysRange = new ArrayList<>();
        for (int i = this.daysRange; i <= 0; i++) {
            lastDaysRange.add(getTimes(i)[0]);
        }
        Long[] today = getTimes(0);

        Manager manager = this.managerService.getManagerByAirport(icao);
        // check if airport have some data on db
        if (manager == null) {
            manager = new Manager(icao);
            Long[] timeGet = { lastDaysRange.get(0), today[1] };
            model = getDataFromApi(icao, timeGet, model);
            model.addAttribute("msgInfo", "Data from Api!");
        } else {
            List<Long> managerDaysSaved = manager.getDays();
            // ! ---------- trocar i=1 para i=this.daysRange ---------------
            for (int i = 1; i <= 0; i++) {
                Long[] timeGet = getTimes(i);
                if (!managerDaysSaved.contains(timeGet[0])) {
                    model = getDataFromApi(icao, timeGet, model);
                    if (model.getAttribute("msgError1") == null && model.getAttribute("msgError2") == null) {
                        manager.addDay(timeGet[0]);
                    }
                    model.addAttribute("msgInfo", "Data from Api!");
                }
            }
        }
        this.managerService.saveManager(manager);

        model.addAttribute("departureFlights", this.flightService.getEstDepartureAirport(icao, lastDaysRange.get(0),
                lastDaysRange.get(lastDaysRange.size() - 1)));
        model.addAttribute("arrivalFlights", this.flightService.getEstArrivalAirport(icao, lastDaysRange.get(0),
                lastDaysRange.get(lastDaysRange.size() - 1)));

        if (model.getAttribute("msgInfo") == null) {
            model.addAttribute("msgInfo", "Data from database!");
        }
        return "fragments/flights :: flights";
    }

    private Model getDataFromApi(String icao, Long[] times, Model model) {
        FlightSerializer[] flights;
        flights = callApi(icao, "departure", times);
        if (flights == null) {
            model.addAttribute("msgError1", "Error on get departure flights from API!");
        } else {
            saveApiData(flights);
        }
        flights = callApi(icao, "arrival", times);
        if (flights == null) {
            model.addAttribute("msgError2", "Error on get arrival flights from API!");
        } else {
            saveApiData(flights);
        }
        return model;
    }

    private FlightSerializer[] callApi(String icao, String type, Long[] times) {
        FlightSerializer[] flights = null;
        try {
            String url = "https://opensky-network.org/api/flights/" + type + "?airport=" + icao + "&begin=" + times[0]
                    + "&end=" + times[1];
            RestTemplate restTemplate = new RestTemplate();
            flights = restTemplate.getForObject(url, FlightSerializer[].class);
        } catch (Exception e) {
            this.log.info("Error on get data from API!");
        }
        return flights;
    }

    private void saveApiData(FlightSerializer[] flights) {
        if (flights != null) {
            if (flights.length != 0) {
                List<String> listIds = this.airportService.getAllIds();
                for (FlightSerializer f : flights) {
                    if (listIds.contains(f.getEstDepartureAirport()) && listIds.contains(f.getEstArrivalAirport())) {
                        Flight flight = new Flight(f.getFirstSeen(), f.getLastSeen(), f.getEstDepartureAirport(),
                                f.getEstArrivalAirport(), f.getCallsign());
                        this.flightService.saveFlight(flight);
                    }
                }
            }
        }
    }

    public Long[] getTimes(int diff) {
        // today
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, diff);

        long initTime = 0;
        long finishTime = 0;
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        initTime = calendar.getTime().getTime() / 1000L;

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 59);
        finishTime = calendar.getTime().getTime() / 1000L;

        Long[] times = { initTime, finishTime };
        return times;
    }
}
