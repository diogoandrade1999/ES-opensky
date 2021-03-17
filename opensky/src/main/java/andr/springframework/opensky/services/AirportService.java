package andr.springframework.opensky.services;

import java.util.List;

import andr.springframework.opensky.domains.Airport;

public interface AirportService {

    Iterable<Airport> listAllAirports();

    Airport getAirportByCode(String code);

    List<Airport> searchAirports(String keyword);

    Airport saveAirport(Airport airport);

    long count();
}
