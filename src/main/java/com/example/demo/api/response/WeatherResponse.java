package com.example.demo.api.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// Usage:
// ObjectMapper om = new ObjectMapper();
// WeatherResponse response = om.readValue(myJsonString, WeatherResponse.class);

@Data
public class WeatherResponse {

    private Current current;

    @Data
    public static class Current {

        private int temperature;

        @JsonProperty("weather_descriptions")
        private List<String> weatherDescriptions;

        private int feelslike;
    }

}
