package com.example.almaz.test.Model;

/**
 * Created by almaz on 14.11.2015.
 */
public class Weather {
    private int temperature;
    private String weather;
    private boolean wind;

    public Weather(){

    }

    public Weather(int temperature, String weather, boolean wind){
        this.temperature=temperature;
        this.weather=weather;
        this.wind=wind;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String wheather) {
        this.weather = wheather;
    }

    public boolean isWind() {
        return wind;
    }

    public void setWind(boolean wind) {
        this.wind = wind;
    }
}
