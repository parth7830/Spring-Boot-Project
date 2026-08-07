package com.JIIT.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse {

    private Current current;




    @Data
    public static class Current {

        @JsonProperty("observation_time")
        private String observationTime;

        private int temperature;

        @JsonProperty("weather_code")
        private int weatherCode;

        @JsonProperty("weather_icons")
        private List<String> weatherIcons;

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;

        @JsonProperty("air_quality")
        private AirQuality airQuality;


        private int feelslike;
    }



    @Data
    public static class AirQuality {

        private String co;
        private String no2;
        private String o3;
        private String so2;

        @JsonProperty("pm2_5")
        private String pm25;

        private String pm10;

        @JsonProperty("us-epa-index")
        private String usEpaIndex;

        @JsonProperty("gb-defra-index")
        private String gbDefraIndex;
    }
}