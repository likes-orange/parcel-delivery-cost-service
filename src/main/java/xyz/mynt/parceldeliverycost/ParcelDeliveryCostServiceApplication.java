package xyz.mynt.parceldeliverycost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableFeignClients
@EnableJpaRepositories
@SpringBootApplication
public class ParcelDeliveryCostServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParcelDeliveryCostServiceApplication.class, args);
	}

}
