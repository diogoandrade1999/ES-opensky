package andr.springframework.opensky.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import andr.springframework.opensky.domains.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

        List<Flight> findByEstDepartureAirportAndFirstSeenBetween(String estDepartureAirport, long startFirstSeen,
                        long endFirstSeen);

        List<Flight> findByEstArrivalAirportAndFirstSeenBetween(String estArrivalAirport, long startFirstSeen,
                        long endFirstSeen);
}
