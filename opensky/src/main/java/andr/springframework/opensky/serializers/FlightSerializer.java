package andr.springframework.opensky.serializers;

import java.util.List;

public class FlightSerializer {

    private String status = null;
    private List<Object> data = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{status=" + status + ", data=" + data + '}';
    }
}
