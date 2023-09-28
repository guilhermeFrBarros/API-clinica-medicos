package med.prometheus.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ApiApplication { // se ter erro de inicialização  -> extends SpringBootServletInitializer

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
