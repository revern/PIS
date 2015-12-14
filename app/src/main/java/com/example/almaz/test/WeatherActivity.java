package com.example.almaz.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class WeatherActivity extends AppCompatActivity {

    private ImageView mWeatherIV;
    private TextView mTemperatureTV;
    private TextView mCityTV;
    private TextView mWindTV;
    private TextView mPressureTV;
    private TextView mHumidityTV;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mWeatherIV = (ImageView) findViewById(R.id.weather_activity_iv);
        mTemperatureTV = (TextView) findViewById(R.id.weather_temp_view);
        mCityTV = (TextView) findViewById(R.id.weather_city_view);
        mWindTV = (TextView) findViewById(R.id.weather_wind_tv);
        mPressureTV = (TextView) findViewById(R.id.weather_pressure_tv);
        mHumidityTV = (TextView) findViewById(R.id.weather_humidity_tv);
        sPref = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        mWeatherIV.setImageResource(sPref.getInt(MainActivity.IMAGE, 0));
        mTemperatureTV.setText(sPref.getString(MainActivity.TEMPERATURE, ""));
        mCityTV.setText(sPref.getString(MainActivity.CITY, ""));
        mWindTV.setText(sPref.getString(MainActivity.WIND, ""));
        mPressureTV.setText(sPref.getString(MainActivity.PRESSURE, ""));
        mHumidityTV.setText(sPref.getString(MainActivity.HUMIDITY, ""));
    }

}
