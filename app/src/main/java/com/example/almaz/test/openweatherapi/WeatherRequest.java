package com.example.almaz.test.openweatherapi;

import com.example.almaz.test.Model.Forecast;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;


/**
 * Created by yasina on 08.11.15.
 */
public class WeatherRequest extends RetrofitSpiceRequest<Forecast, OpenWeatherAPI> {

    private int id;

    public WeatherRequest(int id) {
        super(Forecast.class, OpenWeatherAPI.class);
        this.id=id;
    }

    @Override
    public Forecast loadDataFromNetwork() throws Exception {

        Forecast mWeather = getService().getWeatherByLatLon(id);
        return mWeather;
    }
}
