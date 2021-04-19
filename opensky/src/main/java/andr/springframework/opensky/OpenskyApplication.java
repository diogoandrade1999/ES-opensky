package andr.springframework.opensky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OpenskyApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(OpenskyApplication.class, args);
		Timer timer = new Timer();
		timer.log();

	}

}
