package andr.springframework.opensky.repositories;

import andr.springframework.opensky.domains.Airport;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {

    List<Airport> findByNameContainsAllIgnoreCase(String keyword);
}
