package sn.esmt.microservices.agrosofteurekanamingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class AgrosoftEurekaNamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgrosoftEurekaNamingServerApplication.class, args);
	}

}
