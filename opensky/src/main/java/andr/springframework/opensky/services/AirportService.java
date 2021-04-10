package andr.springframework.opensky.services;

import java.util.List;

import andr.springframework.opensky.domains.Airport;

public interface AirportService {

    Iterable<Airport> listAllAirports();

    List<String> getAllIds();

    Airport getAirportByIcao(String icao);

    List<Airport> searchAirports(String keyword);

    Airport saveAirport(Airport airport);

    long count();
}
