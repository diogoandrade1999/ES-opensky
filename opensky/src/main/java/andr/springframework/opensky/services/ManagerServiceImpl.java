package andr.springframework.opensky.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import andr.springframework.opensky.domains.Manager;
import andr.springframework.opensky.repositories.ManagerRepository;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public Iterable<Manager> listAllManagers() {
        return this.managerRepository.findAll();
    }

    @Override
    public Manager getManagerByAirport(String airport) {
        return this.managerRepository.findById(airport).orElse(null);
    }

    @Override
    public Manager saveManager(Manager manager) {
        return this.managerRepository.save(manager);
    }

    @Override
    public long count() {
        return this.managerRepository.count();
    }

}
