package andr.springframework.opensky.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import andr.springframework.opensky.domains.Flight;
import andr.springframework.opensky.repositories.FlightRepository;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public Iterable<Flight> listAllFlights() {
        return this.flightRepository.findAll();
    }

    @Override
    public Flight saveFlight(Flight flight) {
        return this.flightRepository.save(flight);
    }

    @Override
    public long count() {
        return this.flightRepository.count();
    }

    @Override
    public Flight getFlightById(long id) {
        return this.flightRepository.findById(id).orElse(null);
    }

    @Override
    public List<Flight> getEstDepartureAirport(String estDepartureAirport, long startFirstSeen, long endFirstSeen) {
        return this.flightRepository.findByEstDepartureAirportAndFirstSeenBetween(estDepartureAirport, startFirstSeen,
                endFirstSeen);
    }

    @Override
    public List<Flight> getEstArrivalAirport(String estArrivalAirport, long startFirstSeen, long endFirstSeen) {
        return this.flightRepository.findByEstArrivalAirportAndFirstSeenBetween(estArrivalAirport, startFirstSeen,
                endFirstSeen);
    }

}
