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
import andr.springframework.opensky.domains.Airport;
import andr.springframework.opensky.repositories.AirportRepository;

@Component
public class AirportLoader implements ApplicationListener<ContextRefreshedEvent> {
    private AirportRepository airportRepository;

    private Logger log = LogManager.getLogger(AirportLoader.class);

    @Autowired
    public void setAirportRepository(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Airport>> typeReference = new TypeReference<List<Airport>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/airports.json");
        try {
            List<Airport> airports = mapper.readValue(inputStream, typeReference);
            for (Airport airport : airports) {
                this.airportRepository.save(airport);
            }
            log.info("Airports Saved!");
        } catch (IOException e) {
            log.info("Unable to save airports: " + e.getMessage());
        }
    }
}
