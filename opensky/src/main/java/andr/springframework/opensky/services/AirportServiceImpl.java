package andr.springframework.opensky.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import andr.springframework.opensky.domains.Airport;
import andr.springframework.opensky.repositories.AirportRepository;

@Service
public class AirportServiceImpl implements AirportService {

    private AirportRepository airportRepository;

    @Autowired
    public void setAirportRepository(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    @Override
    public Iterable<Airport> listAllAirports() {
        return airportRepository.findAll();
    }

    @Override
    public Airport getAirportByIcao(String icao) {
        return airportRepository.findById(icao).orElse(null);
    }

    @Override
    public List<Airport> searchAirports(String keyword) {
        if (keyword != null) {
            return airportRepository.findByNameContainsAllIgnoreCase(keyword);
        }
        return null;
    }

    @Override
    public Airport saveAirport(Airport airport) {
        return airportRepository.save(airport);
    }

    @Override
    public long count() {
        return airportRepository.count();
    }

}
