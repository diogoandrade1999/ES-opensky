package andr.springframework.opensky.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import andr.springframework.opensky.domains.Airport;
import andr.springframework.opensky.repositories.AirportRepository;

@Service
public class AirportServiceImpl implements AirportService {

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public Iterable<Airport> listAllAirports() {
        return this.airportRepository.findAll();
    }

    @Override
    public Airport getAirportByIcao(String icao) {
        return this.airportRepository.findById(icao).orElse(null);
    }

    @Override
    public List<Airport> searchAirports(String keyword) {
        if (keyword != null) {
            return this.airportRepository.findByNameContainsAllIgnoreCase(keyword);
        }
        return null;
    }

    @Override
    public Airport saveAirport(Airport airport) {
        return this.airportRepository.save(airport);
    }

    @Override
    public long count() {
        return this.airportRepository.count();
    }

    @Override
    public List<String> getAllIds() {
        Iterable<Airport> airports = this.airportRepository.findAll();
        List<String> listIds = new ArrayList<>();
        for (Airport airport : airports) {
            listIds.add(airport.getIcao());
        }
        return listIds;
    }

}
