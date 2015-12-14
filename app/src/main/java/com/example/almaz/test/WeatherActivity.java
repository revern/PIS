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
        getSupportActionBar().setElevation(0);
        mWeatherIV = (ImageView) findViewById(R.id.weather_activity_iv);
        mTemperatureTV = (TextView) findViewById(R.id.weather_temp_view);
        mCityTV = (TextView) findViewById(R.id.weather_city_view);
        mWindTV = (TextView) findViewById(R.id.weather_wind_tv);
        mPressureTV = (TextView) findViewById(R.id.weather_pressure_tv);
        mHumidityTV = (TextView) findViewById(R.id.weather_humidity_tv);
        sPref = getSharedPreferences(MainActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
        mWeatherIV.setImageResource(sPref.getInt(MainActivity.IMAGE, 0));
        mTemperatureTV.setText(sPref.getString(MainActivity.TEMPERATURE, ""));
        mCityTV.setText(getCityName(sPref.getInt(MainActivity.POSITION,0)));
        mWindTV.setText(R.string.wind);
        mWindTV.setText(mWindTV.getText()+ ": " + sPref.getString(MainActivity.WIND, ""));
        mPressureTV.setText(R.string.pressure);
        mPressureTV.setText(mPressureTV.getText()+ ": " + sPref.getString(MainActivity.PRESSURE, ""));
        mHumidityTV.setText(R.string.humidity);
        mHumidityTV.setText(mHumidityTV.getText() + ": " + sPref.getString(MainActivity.HUMIDITY, ""));
    }
    public int getCityName(int i){
        if(i==0){
            i=R.string.kazan;
        }else if(i==1){
            i=R.string.moscow;
        }else if(i==2){
            i=R.string.new_york;
        }else if(i==3){
            i=R.string.london;
        }else if(i==4){
            i=R.string.tokyo;
        }else if(i==5){
            i=R.string.peking;
        }
        return i;
    }

}
