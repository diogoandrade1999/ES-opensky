package andr.springframework.opensky.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import andr.springframework.opensky.domains.Flight;
import andr.springframework.opensky.repositories.FlightRepository;
import andr.springframework.opensky.serializers.FlightSerializer;
import andr.springframework.opensky.services.AirportService;

@Component
public class FlightLoader implements ApplicationListener<ContextRefreshedEvent> {

    private Logger log = LogManager.getLogger(FlightLoader.class);

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private AirportService airportService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<FlightSerializer>> typeReference = new TypeReference<List<FlightSerializer>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/test_departure.json");
        try {
            List<FlightSerializer> flights = mapper.readValue(inputStream, typeReference);
            List<String> listIds = this.airportService.getAllIds();
            for (FlightSerializer f : flights) {
                if (listIds.contains(f.getEstDepartureAirport()) && listIds.contains(f.getEstArrivalAirport())) {
                    Flight flight = new Flight(f.getFirstSeen(), f.getLastSeen(), f.getEstDepartureAirport(),
                            f.getEstArrivalAirport(), f.getCallsign());
                    this.flightRepository.save(flight);
                }
            }
            this.log.info("Departure Flights Saved!");
        } catch (IOException e) {
            this.log.info("Unable to save departure flights: " + e.getMessage());
        }
        inputStream = TypeReference.class.getResourceAsStream("/json/test_arrival.json");
        try {
            List<FlightSerializer> flights = mapper.readValue(inputStream, typeReference);
            List<String> listIds = this.airportService.getAllIds();
            for (FlightSerializer f : flights) {
                if (listIds.contains(f.getEstDepartureAirport()) && listIds.contains(f.getEstArrivalAirport())) {
                    Flight flight = new Flight(f.getFirstSeen(), f.getLastSeen(), f.getEstDepartureAirport(),
                            f.getEstArrivalAirport(), f.getCallsign());
                    this.flightRepository.save(flight);
                }
            }
            this.log.info("Arrival Flights Saved!");
        } catch (IOException e) {
            this.log.info("Unable to save arrival flights: " + e.getMessage());
        }
    }
}
