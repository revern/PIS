package com.example.almaz.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.almaz.test.Model.City;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SettingsActivity extends AppCompatActivity {

    public final String SEX = "SEX";
    public final String CITY = "CITY";
    public final String POSITION = "POSITION";
    public final String ID = "ID";
    public final String LANGUAGE = "LANGUAGE";
    public static final String APP_PREFERENCES = "mySettings";
    public static final String FIRST_SETTINGS = "firstSettings";
    private RadioButton mMaleRadioButton;
    private RadioButton mFemaleRadioButton;
    private Spinner mSpinner;
    private Spinner mLanSpinner;
    private FloatingActionButton mFeedbackFab;
    private FloatingActionButton mSaveFab;
    private Button mFirstSave;
    private TextView mWelcomeTV;
    private TextView mAuthorView;
    SharedPreferences sPref;
    List<City> cities;
    List<String> cityNames;
    List<String> languages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);


        mMaleRadioButton = (RadioButton) findViewById(R.id.rbMale);
        mFemaleRadioButton = (RadioButton) findViewById(R.id.rbFemale);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mLanSpinner = (Spinner) findViewById(R.id.lan_spinner);
        mFeedbackFab = (FloatingActionButton) findViewById(R.id.feedback_fab);
        mSaveFab = (FloatingActionButton) findViewById(R.id.save_fab);
        mFirstSave = (Button) findViewById(R.id.first_save_btn);
        mWelcomeTV = (TextView) findViewById(R.id.welcome_tv);
        mAuthorView = (TextView) findViewById(R.id.developer_view);

        sPref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        mSaveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor ed = sPref.edit();
                setGender();
                setCity(sPref.getInt(POSITION, 0));
                ed.putBoolean(FIRST_SETTINGS, true);
                ed.commit();
                finish();
            }
        });
        mFirstSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor ed = sPref.edit();
                setCity(sPref.getInt(POSITION, 0));
                setGender();
                ed.putBoolean(FIRST_SETTINGS, true);
                ed.commit();
                finish();
            }
        });
        mFeedbackFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                giveFeedback();
            }
        });
        cities=setNewCities();
        cityNames=setCityNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row, R.id.city_row, cityNames);
        mSpinner.setAdapter(adapter);
        mSpinner.setSelection(sPref.getInt(POSITION, 0));
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor ed = sPref.edit();
                ed.putInt(POSITION, position);
                ed.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        languages=setLanguages();
        ArrayAdapter<String> lanAdapter = new ArrayAdapter<String>(this, R.layout.row, R.id.city_row, languages);
        mLanSpinner.setAdapter(lanAdapter);
        mLanSpinner.setSelection(sPref.getInt(LANGUAGE, 0));
        mLanSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setLanguage(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(sPref.getBoolean(FIRST_SETTINGS,false)){
            mWelcomeTV.setText(R.string.action_settings);
            mFirstSave.setVisibility(View.INVISIBLE);
        } else {
            mWelcomeTV.setText(R.string.welcome);
            getSupportActionBar().setTitle("");
            mSaveFab.hide();
            mFeedbackFab.hide();
            mLanSpinner.setVisibility(View.INVISIBLE);
            mAuthorView.setVisibility(View.INVISIBLE);
        }
    }

    public void setCity(int position){
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(ID, cities.get(position).getId());
        ed.commit();
        ed.putString(CITY, cities.get(position).getName());
        ed.commit();
    }

    public void setGender(){
        SharedPreferences.Editor ed = sPref.edit();
        if(mMaleRadioButton.isChecked()){
            ed.putString(SEX, "male");
        }else if(mFemaleRadioButton.isChecked()){
            ed.putString(SEX, "female");
        }
        ed.commit();
    }

    public void setLanguage(int position){
        if(position==0){
            Resources res = getApplicationContext().getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("ru");
            res.updateConfiguration(conf, dm);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putInt(LANGUAGE, position);
            ed.commit();

        }else{
            Resources res = getApplicationContext().getResources();
            DisplayMetrics dm = res.getDisplayMetrics();
            android.content.res.Configuration conf = res.getConfiguration();
            conf.locale = new Locale("en");
            res.updateConfiguration(conf, dm);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putInt(LANGUAGE, position);
            ed.commit();
        }
    }

    public void giveFeedback(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=ru.alexanderklimov.crib"));
        startActivity(intent);;
    }

    public List<City> setNewCities(){
        List list = new ArrayList();
        mFirstSave.setText(R.string.kazan);
        City city=new City(551487, mFirstSave.getText().toString());
        list.add(city);
        mFirstSave.setText(R.string.moscow);
        city=new City(524901, mFirstSave.getText().toString());
        list.add(city);
        mFirstSave.setText(R.string.new_york);
        city=new City(5128581, mFirstSave.getText().toString());
        list.add(city);
        mFirstSave.setText(R.string.london);
        city=new City(2643743, mFirstSave.getText().toString());
        list.add(city);
        mFirstSave.setText(R.string.tokyo);
        city=new City(1850147, mFirstSave.getText().toString());
        list.add(city);
        mFirstSave.setText(R.string.peking);
        city=new City(2855016, mFirstSave.getText().toString());
        list.add(city);
        mFirstSave.setText(R.string.save);
        return list;
    }
    public List<String> setCityNames(){
        List<String> list = new ArrayList<>();
        for(int i=0;i<cities.size();i++){
            list.add(cities.get(i).getName());
        }
        return list;
    }
    public List<String> setLanguages(){
        List<String> list = new ArrayList<>();
        list.add("Русский");
        list.add("English");
        return list;
    }
}
