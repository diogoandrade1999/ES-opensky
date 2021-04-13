package andr.springframework.opensky;

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
