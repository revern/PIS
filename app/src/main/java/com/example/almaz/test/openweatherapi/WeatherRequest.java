package com.example.almaz.test.openweatherapi;

import com.example.almaz.test.Model.Forecast;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;


/**
 * Created by yasina on 08.11.15.
 */
public class WeatherRequest extends RetrofitSpiceRequest<Forecast, OpenWeatherAPI> {

    private double lat, lon;

    public WeatherRequest(double longitude, double latitude) {
        super(Forecast.class, OpenWeatherAPI.class);
        this.lat = latitude;
        this.lon = longitude;
    }

    @Override
    public Forecast loadDataFromNetwork() throws Exception {

        Forecast mWeather = getService().getWeatherByLatLon(lat, lon);
        return mWeather;
    }
}
