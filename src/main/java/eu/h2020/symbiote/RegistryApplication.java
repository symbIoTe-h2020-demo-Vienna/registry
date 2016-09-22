package eu.h2020.symbiote;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by mateuszl on 22.09.2016.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class RegistryApplication {


	public static Log log = LogFactory.getLog(RegistryApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RegistryApplication.class, args);
	}
}
