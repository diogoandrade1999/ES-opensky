package andr.springframework.opensky.domains;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Airport {

    @Id
    private String icao;

    private String name;
    private String city;
    private String country;
    private String iata;

    public String getIcao() {
        return this.icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIata() {
        return this.iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }
}
