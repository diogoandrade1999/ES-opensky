package andr.springframework.opensky.loaders;

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

        Airport sma = new Airport();
        sma.setCode("LPAZ");
        sma.setName("Santa Maria");
        sma.setCountry("Portugal");
        airportRepository.save(sma);
        log.info("Saved Airport - id: " + sma.getCode());

        Airport sjz = new Airport();
        sjz.setCode("LPSJ");
        sjz.setName("São Jorge");
        sjz.setCountry("Portugal");
        airportRepository.save(sjz);
        log.info("Saved Airport - id: " + sjz.getCode());

        Airport flw = new Airport();
        flw.setCode("LPFL");
        flw.setName("Flores");
        flw.setCountry("Portugal");
        airportRepository.save(flw);
        log.info("Saved Airport - id: " + flw.getCode());

        Airport cvu = new Airport();
        cvu.setCode("LPCR");
        cvu.setName("Corvo");
        cvu.setCountry("Portugal");
        airportRepository.save(cvu);
        log.info("Saved Airport - id: " + cvu.getCode());
    }
}
