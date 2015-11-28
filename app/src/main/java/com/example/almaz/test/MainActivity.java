package com.example.almaz.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.almaz.test.Model.Clothes;
import com.example.almaz.test.Model.ClothesSet;
import com.example.almaz.test.Model.Forecast;
import com.example.almaz.test.openweatherapi.ForecastService;
import com.example.almaz.test.openweatherapi.WeatherRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES = "mySettings";
    public static final String FIRST_SETTINGS = "firstSettings";
    public final String CITY = "CITY";
    public final String TEMPERATURE = "TEMPERATURE";
    public final String NUMBER = "NUMBER";
    public final String MONTH = "MONTH";
    public final String YEAR = "YEAR";
    final Uri CLOTHES_URI = Uri
            .parse("content://almaz.example.com.test/contacts");
    final String CLOTHES_NAME = "name";
    final String CLOTHES_LAYOUT = "layout";
    final String CLOTHES_TEMPERATURE_COEFFICIENT = "temperature_coefficient";
    final String CLOTHES_STYLE_OFFICIAL = "style_official";
    final String CLOTHES_STYLE_REGULAR = "style_regular";
    final String CLOTHES_STYLE_SPORT = "style_sport";
    final String CLOTHES_STYLE_EVENING = "style_evening";

    private Weather weather;
    private String style;
    private Cursor cursor;
    private String sex;

    RecyclerView mRcView_1;
    RecyclerView mRcView_2;
    RecyclerView mRcView_3;
    RecyclerView mRcView_4;
    RecyclerView mRcView_5;
    RecyclerView mRcView_6;

    List<Integer> headResources;
    List<Integer> bodyResources;
    List<Integer> bodyTopResources;
    List<Integer> legsResources;
    List<Integer> footwearResources;
    List<Integer> accessoryResources;

    List<Clothes> headStyledClothes;
    List<Clothes> bodyStyledClothes;
    List<Clothes> bodyTopStyledClothes;
    List<Clothes> legsStyledClothes;
    List<Clothes> footwearStyledClothes;
    List<Clothes> accessoryStyledClothes;

    TextView mCityView;
    TextView mTemperatureView;
    TextView mNumberView;
    TextView mMonthView;
    TextView mYearView;
    ImageView mWeatherView;

    private SpiceManager spiceManager = new SpiceManager(ForecastService.class);

    TextView mOfficialStyleButton;
    TextView mRegularStyleButton;
    TextView mSportStyleButton;
    TextView mEveningStyleButton;

    SharedPreferences sPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sPref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if(!sPref.getBoolean(FIRST_SETTINGS, false)) {
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
        }
        mOfficialStyleButton = (TextView) findViewById(R.id.official_style_button);
        mRegularStyleButton = (TextView) findViewById(R.id.regular_style_button);
        mSportStyleButton = (TextView) findViewById(R.id.sport_style_button);
        mEveningStyleButton = (TextView) findViewById(R.id.evening_style_button);

        sPref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        Calendar calendar = Calendar.getInstance();

        ed.putString(TEMPERATURE, "-5 C");
        ed.commit();

        ed.putInt(NUMBER, calendar.get(Calendar.DAY_OF_MONTH));
        ed.commit();

        ed.putInt(MONTH, calendar.get(Calendar.MONTH));
        ed.commit();

        ed.putInt(YEAR, calendar.get(Calendar.YEAR));
        ed.commit();

        //TODO: add locations
        double currentLatitude = 55.7679;
        double currentLongitude = 49.1631;

        processLocation(currentLatitude, currentLongitude);

        mCityView = (TextView) findViewById(R.id.city_view);
        mTemperatureView = (TextView) findViewById(R.id.temperature_view);
        mNumberView = (TextView) findViewById(R.id.number_view);
        mMonthView = (TextView) findViewById(R.id.month_view);
        mYearView = (TextView) findViewById(R.id.year_view);
        mWeatherView = (ImageView) findViewById(R.id.weather_img_view);

        mRcView_1= (RecyclerView) findViewById(R.id.rcView_1);
        mRcView_2= (RecyclerView) findViewById(R.id.rcView_2);
        mRcView_3= (RecyclerView) findViewById(R.id.rcView_3);
        mRcView_4= (RecyclerView) findViewById(R.id.rcView_4);
        mRcView_5= (RecyclerView) findViewById(R.id.rcView_5);
        mRcView_6= (RecyclerView) findViewById(R.id.rcView_6);

        mCityView.setText(sPref.getString(CITY, ""));
        mTemperatureView.setText(sPref.getString(TEMPERATURE, ""));
        mNumberView.setText(sPref.getInt(NUMBER, 0) + "");
        mMonthView.setText(getMonthName(sPref.getInt(MONTH, 0)));
        mYearView.setText(sPref.getInt(YEAR, 0) + "");

        clearLists();
        style = "regular";
        sex  = sPref.getString("SEX", "male");
        weather = new Weather();
        cursor = getContentResolver().query(CLOTHES_URI, null, null,
                null, null);
        cursor.moveToFirst();
        clothesSet();
        setAdapters();

        mRegularStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style = "regular";
                clothesSet();
                setAdapters();
            }
        });
        mOfficialStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style = "official";
                clothesSet();
                setAdapters();
            }
        });
        mEveningStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style="evening";
                clothesSet();
                setAdapters();
            }
        });
        mSportStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style="sport";
                clothesSet();
                setAdapters();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        spiceManager.start(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        spiceManager.shouldStop();
    }

    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }

    public void processLocation(double currentLatitude, double currentLongitude) {

        WeatherRequest request = new WeatherRequest(currentLongitude, currentLatitude);
        getSpiceManager().execute(request, new RequestListener<Forecast>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(Forecast currentForecast) {
                // TODO: here you can get all your weather parametrs
                Log.d("TAG", currentForecast.name);
            }
        });
    }

    public void setAdapters(){
        setRecyclerAdapter(mRcView_1, headResources);
        setRecyclerAdapter(mRcView_2, bodyResources);
        setRecyclerAdapter(mRcView_3, legsResources);
        setRecyclerAdapter(mRcView_4, accessoryResources);
        setRecyclerAdapter(mRcView_5, bodyTopResources);
        setRecyclerAdapter(mRcView_6, footwearResources);
    }

    public void clearLists(){
        headResources = new ArrayList<>();
        bodyResources = new ArrayList<>();
        bodyTopResources = new ArrayList<>();
        legsResources = new ArrayList<>();
        footwearResources = new ArrayList<>();
        accessoryResources = new ArrayList<>();

        headStyledClothes = new ArrayList<>();
        bodyStyledClothes = new ArrayList<>();
        bodyTopStyledClothes = new ArrayList<>();
        legsStyledClothes = new ArrayList<>();
        footwearStyledClothes = new ArrayList<>();
        accessoryStyledClothes = new ArrayList<>();
    }
    public ClothesSet clothesSet(){
        cursor.moveToFirst();
        clearLists();

        switch (style) {


            case "official":
                for(int i=0;i<cursor.getCount();i++){
                    if(cursor.getString(4).equals("1")){
                        addStyledClothes();
                    }
                    cursor.moveToNext();
                }
                break;
            case "regular":
                for(int i=0;i<cursor.getCount();i++){
                    if(cursor.getString(5).equals("1")){
                        addStyledClothes();
                    }
                    cursor.moveToNext();
                }
                break;
            case "sport":
                for(int i=0;i<cursor.getCount();i++){
                    if(cursor.getString(6).equals("1")){
                        addStyledClothes();
                    }
                    cursor.moveToNext();
                }
                break;
            case "evening":
                for(int i=0;i<cursor.getCount();i++){
                    if(cursor.getString(7).equals("1")){
                        addStyledClothes();
                    }
                    cursor.moveToNext();
                }
                break;
        }
        ClothesSet clothesSet = new ClothesSet();
        return clothesSet;
    }

    public void addStyledClothes(){
        boolean b4=false;
        boolean b5=false;
        boolean b6=false;
        boolean b7=false;
        boolean b8=false;
        boolean b9=false;
        boolean b11=false;
        boolean b12=false;

        if(cursor.getString(4).equals("1")){ b4 = true; }
        if(cursor.getString(5).equals("1")){ b5 = true; }
        if(cursor.getString(6).equals("1")){ b6 = true; }
        if(cursor.getString(7).equals("1")){ b7 = true; }
        if(cursor.getString(8).equals("1")){ b8 = true; }
        if(cursor.getString(9).equals("1")){ b9 = true; }
        if(cursor.getString(11).equals("1")){ b11 = true; }
        if(cursor.getString(12).equals("1")){ b12 = true; }

        switch (cursor.getString(2)) {
            case "head":
                if(sex.equals("male") && b11 || sex.equals("female") && b12) {
                    headResources.add(cursor.getInt(10));
                    headStyledClothes.add(new Clothes(cursor.getString(1), cursor.getString(2), cursor.getInt(3), b4, b5, b6, b7, b8, b9, cursor.getInt(10), b11, b12));
                }
                break;
            case "body":
                if(sex.equals("male") && b11 || sex.equals("female") && b12) {
                    bodyResources.add(cursor.getInt(10));
                    bodyStyledClothes.add(new Clothes(cursor.getString(1), cursor.getString(2), cursor.getInt(3), b4, b5, b6, b7, b8, b9, cursor.getInt(10), b11, b12));
                }
                break;
            case "bodyTop":
                if(sex.equals("male") && b11 || sex.equals("female") && b12) {
                    bodyTopResources.add(cursor.getInt(10));
                    bodyTopStyledClothes.add(new Clothes(cursor.getString(1), cursor.getString(2), cursor.getInt(3), b4, b5, b6, b7, b8, b9, cursor.getInt(10), b11, b12));
                }
                break;
            case "legs":
                if(sex.equals("male") && b11 || sex.equals("female") && b12) {
                    legsResources.add(cursor.getInt(10));
                    legsStyledClothes.add(new Clothes(cursor.getString(1), cursor.getString(2), cursor.getInt(3), b4, b5, b6, b7, b8, b9, cursor.getInt(10), b11, b12));
                }
                break;
            case "footwear":
                if(sex.equals("male") && b11 || sex.equals("female") && b12) {
                    footwearResources.add(cursor.getInt(10));
                    footwearStyledClothes.add(new Clothes(cursor.getString(1), cursor.getString(2), cursor.getInt(3), b4, b5, b6, b7, b8, b9, cursor.getInt(10), b11, b12));
                }
                break;
            case "accessory":
                if(sex.equals("male") && b11 || sex.equals("female") && b12) {
                    accessoryResources.add(cursor.getInt(10));
                    accessoryStyledClothes.add(new Clothes(cursor.getString(1), cursor.getString(2), cursor.getInt(3), b4, b5, b6, b7, b8, b9, cursor.getInt(10), b11, b12));
                }
                break;
        }
    }

    public ClothesSet createClothesSet(){
        ClothesSet clothesSet = new ClothesSet();
        //accessory adding
        if(weather.getWeather().equals("sky is clear") && weather.getTemperature() >= 0){
            //add sunglasses
        }else if(weather.getTemperature()>=0 && weather.getWeather().equals("Rain")){
            //add umbrella
        }else if(weather.getTemperature()<0){
            //add scarf+gloves
        }

        //head adding
        if(weather.getTemperature()<0){
            //add a cap
        }

        //body and bodyTop adding
        int minFault = 99;
        Clothes bestBody = bodyStyledClothes.get(0);
        Clothes bestBodyTop = bodyTopStyledClothes.get(0);
        for(int i = 0; i < bodyStyledClothes.size(); i++){
            for(int j=0; j <bodyTopStyledClothes.size(); j++){
                int fault = bodyStyledClothes.get(i).getTemperatureCoefficient()+bodyTopStyledClothes.get(j).getTemperatureCoefficient()+weather.getTemperature()-25;
                fault = Math.abs(fault);
                if(fault<minFault) {
                    minFault=fault;
                    bestBody=bodyStyledClothes.get(i);
                    bestBodyTop=bodyTopStyledClothes.get(j);
                }
            }
        }
        clothesSet.setBody(bestBody);
        clothesSet.setBodyTop(bestBodyTop);

        //legs adding
        minFault = 99;
        Clothes  bestLegs = legsStyledClothes.get(0);
        for(int i=0;i<legsStyledClothes.size();i++){
            if(legsStyledClothes.get(i).getTemperatureCoefficient()<minFault){
                minFault = legsStyledClothes.get(i).getTemperatureCoefficient();
                bestLegs = legsStyledClothes.get(i);
            }
        }
        clothesSet.setLegs(bestLegs);

        //
        minFault = 99;
        Clothes  bestFootwear = footwearStyledClothes.get(0);
        for(int i=0;i<footwearStyledClothes.size();i++){
            if(footwearStyledClothes.get(i).getTemperatureCoefficient()<minFault){
                minFault = footwearStyledClothes.get(i).getTemperatureCoefficient();
                bestFootwear = footwearStyledClothes.get(i);
            }
        }
        clothesSet.setFootwear(bestFootwear);

        return clothesSet;
    }
    public String getMonthName(int month){
        String monthName="";
        switch (month){
            case 0:
                monthName="January";
                break;
            case 1:
                monthName="February";
                break;
            case 2:
                monthName="March";
                break;
            case 3:
                monthName="April";
                break;
            case 4:
                monthName="May";
                break;
            case 5:
                monthName="June";
                break;
            case 6:
                monthName="July";
                break;
            case 7:
                monthName="August";
                break;
            case 8:
                monthName="September";
                break;
            case 9:
                monthName="October";
                break;
            case 10:
                monthName="November";
                break;
            case 11:
                monthName="December";
                break;
        }
        return monthName;
    }
    public void setRecyclerAdapter(RecyclerView recyclerView, List list){
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(), list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d("SETTINGS", "HELLO");
        Intent i = new Intent(MainActivity.this, SettingsActivity.class);
        startActivityForResult(i, 0);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCityView.setText(sPref.getString(CITY, ""));
        sex=sPref.getString("SEX", "male");
        clothesSet();
        setAdapters();
    }
}
