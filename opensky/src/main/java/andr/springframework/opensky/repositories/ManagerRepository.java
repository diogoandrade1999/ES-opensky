package andr.springframework.opensky.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import andr.springframework.opensky.domains.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, String> {

}
