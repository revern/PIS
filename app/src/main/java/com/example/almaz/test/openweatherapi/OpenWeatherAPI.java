package com.example.almaz.test.openweatherapi;

import com.example.almaz.test.Model.Forecast;

import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Created by yasina on 08.11.15.
 */
public interface OpenWeatherAPI {

    @GET("/weather")
    Forecast getWeatherByLatLon(@Query("q") String city);

}
