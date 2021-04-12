package andr.springframework.opensky.services;

import andr.springframework.opensky.domains.Manager;

public interface ManagerService {

    Iterable<Manager> listAllManagers();

    Manager getManagerByAirport(String airport);

    Manager saveManager(Manager manager);

    long count();
}
