package com.example.almaz.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.example.almaz.test.Model.ClothesSet;
import com.example.almaz.test.Model.Weather;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES = "mySettings";
    public static final String FIRST_SETTINGS = "firstSettings";

    Button mOfficialStyleButton;
    Button mRegularStyleButton;
    Button mSportStyleButton;
    Button mEveningStyleButton;

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
        mOfficialStyleButton = (Button) findViewById(R.id.official_style_button);
        mRegularStyleButton = (Button) findViewById(R.id.regular_style_button);
        mSportStyleButton = (Button) findViewById(R.id.sport_style_button);
        mEveningStyleButton = (Button) findViewById(R.id.evening_style_button);
        mOfficialStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ClothesShowActivity.class);
                i.putExtra(ClothesShowActivity.EXTRA_STYLE, "official");
                startActivity(i);
            }
        });
        mRegularStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ClothesShowActivity.class);
                i.putExtra(ClothesShowActivity.EXTRA_STYLE, "regular");
                startActivity(i);
            }
        });
        mSportStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ClothesShowActivity.class);
                i.putExtra(ClothesShowActivity.EXTRA_STYLE, "sport");
                startActivity(i);
            }
        });
        mEveningStyleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ClothesShowActivity.class);
                i.putExtra(ClothesShowActivity.EXTRA_STYLE, "evening");
                startActivity(i);
            }
        });

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
