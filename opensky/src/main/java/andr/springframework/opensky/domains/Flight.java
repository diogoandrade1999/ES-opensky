package andr.springframework.opensky.domains;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Flight {

    @Id
    @GeneratedValue
    private long flightId;

    private long firstSeen;
    private long lastSeen;
    private String estDepartureAirport;
    private String estArrivalAirport;
    private String callsign;

    public Flight(long firstSeen, long lastSeen, String estDepartureAirport, String estArrivalAirport,
            String callsign) {
        this.firstSeen = firstSeen;
        this.lastSeen = lastSeen;
        this.estDepartureAirport = estDepartureAirport;
        this.estArrivalAirport = estArrivalAirport;
        this.callsign = callsign;
    }

    public long getFlightId() {
        return this.flightId;
    }

    public void setFlightId(long flightId) {
        this.flightId = flightId;
    }

    public String getFirstSeen() {
        long unixSeconds = this.firstSeen;
        Date date = new java.util.Date(unixSeconds * 1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public void setFirstSeen(long firstSeen) {
        this.firstSeen = firstSeen;
    }

    public long getLastSeen() {
        return this.lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getEstDepartureAirport() {
        return this.estDepartureAirport;
    }

    public void setEstDepartureAirport(String estDepartureAirport) {
        this.estDepartureAirport = estDepartureAirport;
    }

    public String getEstArrivalAirport() {
        return this.estArrivalAirport;
    }

    public void setEstArrivalAirport(String estArrivalAirport) {
        this.estArrivalAirport = estArrivalAirport;
    }

    public String getCallsign() {
        return this.callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }
}