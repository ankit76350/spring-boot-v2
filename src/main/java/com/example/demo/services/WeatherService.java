package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.api.response.WeatherResponse;
import com.example.demo.cache.AppCache;
import com.example.demo.constants.Placeholders;




// WRONG: @Service was missing — without it Spring cannot find and inject this class via @Autowired
@Service
public class WeatherService {
    // ! api :
    // https://api.weatherstack.com/current?access_key=88cd0e5d8e54eb993050d152c99f50e3&query=Mumbai

    @Value("${weather.api.key}")
    private String apiKey ;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city) {
        String finalAPI = appCache.getAppCache().get("weather_api").replace(Placeholders.CITY, city).replace(Placeholders.API_KEY, apiKey);

        ResponseEntity<WeatherResponse> resp = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);

        // WRONG: WeatherResponse.getBody() — getBody() was called on the CLASS (WeatherResponse), not the response object (resp)
        WeatherResponse weather = resp.getBody();
        return weather;
    }

}
