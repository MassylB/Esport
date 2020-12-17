package com.example.esport;

import android.content.Intent;
import android.os.Bundle;

import com.example.esport.ui.main.AsyncPandaJSONData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.esport.ui.main.SectionsPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        //Récupération des trois dates
        Calendar rightNow = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        Calendar tommorow = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -7);
        tommorow.add(Calendar.DATE, -5);
        Date hier = yesterday.getTime();
        Date date = rightNow.getTime();
        Date demain = tommorow.getTime();
        String yes = formater.format(hier);
        String tomo = formater.format(demain);
        String today = formater.format(date);


        AsyncPandaJSONData task = new AsyncPandaJSONData(MainActivity.this,today,tomo,yes);
        task.execute("https://api.pandascore.co/lol/matches?range[begin_at]="+yes+"T00:00:00Z,"+tomo+"T23:59:59&token=HkJ-ks2kR-vuOw5vKcush-oWfzU2lmh97cHc5Y0ePCOapO57WcE");
        //SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        //ViewPager viewPager = findViewById(R.id.view_pager);
        //viewPager.setAdapter(sectionsPagerAdapter);
        //TabLayout tabs = findViewById(R.id.tabs);
        //tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ajouté aux favoris", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton search = findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent = new Intent(getApplicationContext(), Search.class);
                 startActivity(intent);
            }


        });

    }
}