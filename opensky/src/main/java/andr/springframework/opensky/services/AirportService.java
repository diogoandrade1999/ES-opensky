package andr.springframework.opensky.services;

import java.util.List;

import andr.springframework.opensky.domains.Airport;

public interface AirportService {

    Iterable<Airport> listAllAirports();

    Airport getAirportByCode(String code);

    List<Airport> seachAirports(String keyword);

    Airport saveAirport(Airport airport);

    long count();
}
