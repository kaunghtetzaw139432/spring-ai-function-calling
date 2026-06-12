package dev.danvega.function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import io.github.cdimascio.dotenv.Dotenv;
@EnableConfigurationProperties(WeatherConfigProperties.class)
@SpringBootApplication
public class FunctionApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		SpringApplication.run(FunctionApplication.class, args);
	}

}
