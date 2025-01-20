package tech.csm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "tech.csm")
public class CsmestoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsmestoreApplication.class, args);
	}

}
