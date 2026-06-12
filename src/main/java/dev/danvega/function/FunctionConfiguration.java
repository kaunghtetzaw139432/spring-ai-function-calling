package dev.danvega.function;

import java.util.function.Function;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;


@Configuration
public class FunctionConfiguration {

    private final WeatherConfigProperties weatherConfigProperties;

    public FunctionConfiguration(WeatherConfigProperties weatherConfigProperties) {
        this.weatherConfigProperties = weatherConfigProperties;
    }
    @Bean
    @Description("Get the current weather conditions for the given city")
    public Function<WeatherService.Request,WeatherService.Response>currentWeatherFunction(){
        return new WeatherService(weatherConfigProperties);
    }

   
}