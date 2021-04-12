package andr.springframework.opensky.domains;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Manager {

    @Id
    private String airport;

    @ElementCollection
    private List<Long> days = new ArrayList<>();

    public Manager() {
    }

    public Manager(String airport) {
        this.airport = airport;
    }

    public String getAirport() {
        return this.airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public List<Long> getDays() {
        return this.days;
    }

    public void setDays(List<Long> days) {
        this.days = days;
    }

    public void addDay(Long day) {
        this.days.add(day);
    }

}
