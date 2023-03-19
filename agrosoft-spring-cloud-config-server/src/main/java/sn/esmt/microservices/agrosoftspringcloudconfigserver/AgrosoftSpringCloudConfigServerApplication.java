package sn.esmt.microservices.agrosoftspringcloudconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class AgrosoftSpringCloudConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgrosoftSpringCloudConfigServerApplication.class, args);
	}

}
