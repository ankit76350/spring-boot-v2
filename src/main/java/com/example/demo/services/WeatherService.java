package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.api.response.WeatherResponse;




// WRONG: @Service was missing — without it Spring cannot find and inject this class via @Autowired
@Service
public class WeatherService {
    // ! api :
    // https://api.weatherstack.com/current?access_key=88cd0e5d8e54eb993050d152c99f50e3&query=Mumbai
    private static final String apiKey = "88cd0e5d8e54eb993050d152c99f50e3";
    private static final String API = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String finalAPI = API.replace("CITY", city).replace("API_KEY", apiKey);

        ResponseEntity<WeatherResponse> resp = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);

        // WRONG: WeatherResponse.getBody() — getBody() was called on the CLASS (WeatherResponse), not the response object (resp)
        WeatherResponse weather = resp.getBody();
        return weather;
    }

}
