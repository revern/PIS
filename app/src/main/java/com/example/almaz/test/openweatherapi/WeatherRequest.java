package com.example.almaz.test.openweatherapi;

import com.example.almaz.test.Model.Forecast;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;


/**
 * Created by yasina on 08.11.15.
 */
public class WeatherRequest extends RetrofitSpiceRequest<Forecast, OpenWeatherAPI> {

    private String city;

    public WeatherRequest(String city) {
        super(Forecast.class, OpenWeatherAPI.class);
        this.city=city;
    }

    @Override
    public Forecast loadDataFromNetwork() throws Exception {

        Forecast mWeather = getService().getWeatherByLatLon(city);
        return mWeather;
    }
}
