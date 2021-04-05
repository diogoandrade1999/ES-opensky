package andr.springframework.opensky.services;

import java.util.List;

import andr.springframework.opensky.domains.Flight;

public interface FlightService {

    Iterable<Flight> listAllFlights();

    Flight getFlightById(long id);

    Flight saveFlight(Flight flight);

    List<Flight> getEstDepartureAirport(String estDepartureAirport, long startFirstSeen, long endFirstSeen);

    List<Flight> getEstArrivalAirport(String estArrivalAirport, long startFirstSeen, long endFirstSeen);

    long count();
}
