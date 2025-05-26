package com.example.weather_api.service;

import com.example.weather_api.model.WeatherResponse;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Cacheable(value = "weather",key="#city")
    public WeatherResponse getWeather(String city){
        String url = String.format("%s?q=%s&appid=%s&units=metric",apiUrl,city,apiKey);

        // Call OpenWeatherMap and map result
        System.out.println("Calling External API");
        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode body = response.getBody();

        assert body != null;
        return new WeatherResponse(
                body.get("name").asText(),
                body.get("weather").get(0).get("description").asText(),
                body.get("main").get("temp").asDouble()
                );
    }
}
