package com.example.esport;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.esport.ui.main.AsyncPandaJSONMatch;
import com.example.esport.ui.main.AsyncPandaJSONProfil;

import static com.example.esport.Fragmentajd.EXTRA_MESSAGE1;
import static com.example.esport.Fragmentdemain.EXTRA_MESSAGE2;
import static com.example.esport.Fragmenthier.EXTRA_MESSAGE3;

public class Match extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_layout);
        Intent intent = getIntent();
        String name = intent.getStringExtra(Fragmentdemain.EXTRA_MESSAGE2);
        Log.i("verif", name);

        AsyncPandaJSONMatch match = new AsyncPandaJSONMatch(Match.this);
        match.execute("https://api.pandascore.co/lol/matches?search[name]="+name+"&token=HkJ-ks2kR-vuOw5vKcush-oWfzU2lmh97cHc5Y0ePCOapO57WcE");


    }


}



