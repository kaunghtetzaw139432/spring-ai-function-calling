package dev.danvega.function;

import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClient;

public class WeatherService implements Function<WeatherService.Request, WeatherService.Response> {

    private static final Logger log = LoggerFactory.getLogger(WeatherService.class);
    private final WeatherConfigProperties weatherConfigProperties;
    private final RestClient restClient;

    public WeatherService(WeatherConfigProperties weatherConfigProperties) {
        this.weatherConfigProperties = weatherConfigProperties;
        this.restClient = RestClient.create(weatherConfigProperties.apiUrl());
    }

    public record Request(String city) {
    }

    public record Response(String city, String condition) {
    }

    public record WeatherApiResponse(Location location, Current current) {
    }

    public record Location(String name, String region, String country, Double lat, Double lon) {
    }

    public record Current(Double temp_c, Double temp_f, WeatherCondition condition, Double wind_mph, Integer humidity) {
    }

    public record WeatherCondition(String text) {
    }

    @Override
    public Response apply(Request t) {
        log.info("Weather Request for city: {}", t.city());

        try {
            WeatherApiResponse apiResponse = this.restClient.get()
                    .uri("/current.json?key={apiKey}&q={city}",
                            weatherConfigProperties.apiKey(),
                            t.city())
                    .retrieve()
                    .body(WeatherApiResponse.class);

            log.info("Weather API Raw Response: {}", apiResponse);

            if (apiResponse != null && apiResponse.current() != null) {
                return new Response(
                        apiResponse.location().name(),
                        apiResponse.current().condition().text() + " with " + apiResponse.current().temp_c() + "°C");
            }
        } catch (Exception e) {
            log.error("Error fetching weather data", e);
        }

        return new Response(t.city(), "Unknown weather data due to an error");
    }
}