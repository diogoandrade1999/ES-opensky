package andr.springframework.opensky;

import andr.springframework.opensky.controllers.FlightController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OpenskyApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(OpenskyApplication.class, args);
		Timer timer = new Timer();
		timer.log();

	}

}
