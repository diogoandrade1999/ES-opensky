package andr.springframework.opensky;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class Scheduler {
    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private int nrFlights = 0;


     @Autowired
     private KafkaTemplate<String, String> kafkaTemplate;

     public void sendMessage(String msg) {
         kafkaTemplate.send("flights", msg);
     }


    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        int newNrFlights = callApi();
        if(newNrFlights > nrFlights) {
            log.info("Entrou aviao");
            sendMessage("Entrou aviao");
        } else if (newNrFlights < nrFlights) {
            log.info("Saiu aviao");
            sendMessage("Saiu aviao");
        }
        nrFlights = newNrFlights;
    }

    private int callApi() {
        int nrFlights = 0 ;
        try {
            String url = "https://opensky-network.org/api/states/all?lamin=36.8382&lomin=-9.5265&lamax=42.2804&lomax=-6.3890";
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JsonNode root = objectMapper.readTree(response.getBody());
            nrFlights = root.path("states").size();
        } catch (Exception e) {
            this.log.info("Error on get data from API!");
        }
        return nrFlights;
    }
}
