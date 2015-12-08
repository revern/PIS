package com.example.almaz.test;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;


public class SettingsActivity extends AppCompatActivity {

    public final String SEX = "SEX";
    public final String CITY = "CITY";
    public static final String APP_PREFERENCES = "mySettings";
    public static final String FIRST_SETTINGS = "firstSettings";
    private RadioButton mMaleRadioButton;
    private RadioButton mFemaleRadioButton;
    private EditText mCityEditText;
    private Button mAcceptButton;

    SharedPreferences sPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mMaleRadioButton = (RadioButton) findViewById(R.id.rbMale);
        mFemaleRadioButton = (RadioButton) findViewById(R.id.rbFemale);
        mCityEditText = (EditText) findViewById(R.id.city_edit_text);
        mAcceptButton = (Button) findViewById(R.id.settings_accept_btn);
        sPref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        mAcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor ed = sPref.edit();
                setCity();
                setGender();
                setLanguage();
                ed.putBoolean(FIRST_SETTINGS, true);
                ed.commit();
                finish();
            }
        });
    }
    public void setCity(){
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString(CITY, mCityEditText.getText().toString());
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
    public void setLanguage(){
        Log.d("Language", "calling method setLanguage");
    }
    public void giveFeedback(){
        //feedback
    }
}
