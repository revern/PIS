package com.example.almaz.test.openweatherapi;

import android.net.Uri;

import com.example.almaz.test.BuildConfig;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import org.apache.commons.lang3.NotImplementedException;

import java.util.HashMap;
import java.util.Map;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;



/**
 * Created by yasina on 08.11.15.
 */
public class ForecastService extends RetrofitGsonSpiceService {

    RestAdapter.Builder restAdapterBuilder;
    private Map<Class<?>, Object> retrofitInterfaceToServiceMap = new HashMap<Class<?>, Object>();
    private Uri built;
    final String APPID_PARAM = "APPID";
    private String BACKEND_URL = "http://api.openweathermap.org/data/2.5";
    private Map<Class<?>, String> retrofitInterfaceToUrl = new HashMap<Class<?>, String>() {{
        put(OpenWeatherAPI.class, BACKEND_URL);
    }};

    @Override
    public void onCreate() {
        super.onCreate();
          restAdapterBuilder = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL)
            .setEndpoint(BACKEND_URL).setRequestInterceptor(new RequestInterceptor() {
                      @Override
                      public void intercept(RequestFacade request) {
                          request.addQueryParam("APPID", BuildConfig.OPEN_WEATHER_MAP_API_KEY);
                      }
                  });

    }

    @Override
    protected String getServerUrl() {
        throw new NotImplementedException("Method should not be used");
    }

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {

        return new RestAdapter.Builder().setConverter(getConverter()).setEndpoint("http://google.com");
    }

    @Override
    protected <T> T getRetrofitService(Class<T> serviceClass) {
        T service = (T) retrofitInterfaceToServiceMap.get(serviceClass);
        if (service == null) {
            service = restAdapterBuilder.build().create(serviceClass);
            retrofitInterfaceToServiceMap.put(serviceClass, service);
        }
        return service;
    }
}
