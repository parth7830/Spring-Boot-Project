package com.JIIT.journalApp.services;

import com.JIIT.journalApp.api.response.WeatherResponse;
import com.JIIT.journalApp.cache.AppCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apikey;

    public static final String API = "http://api.weatherstack.com/current?access_key=301dc337f4af4604810b981e39ee9779&query=New%20York";

    private static final long WEATHER_CACHE_TTL = 1800L; // 30 minutes in seconds

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getWeather(String city) {
        // Try to get from Redis cache first
        WeatherResponse cachedResponse = redisService.get("weather:" + city, WeatherResponse.class);
        if (cachedResponse != null) {
            log.info("Weather data for '{}' fetched from Redis cache", city);
            return cachedResponse;
        }

        // Cache miss — call the weather API
        log.info("Cache miss for '{}', fetching from weather API", city);
        String finalAPI = appCache.APP_CACHE.get("weather_api").replace("CITY", city).replace("API_KEY", apikey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = response.getBody();

        // Store in Redis cache
        if (body != null) {
            redisService.set("weather:" + city, body, WEATHER_CACHE_TTL);
        }

        return body;
    }
}
