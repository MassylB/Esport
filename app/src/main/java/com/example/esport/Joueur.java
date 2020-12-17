package com.example.esport;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.esport.ui.main.AsyncPandaJSONProfil;

public class Joueur extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_joueur);
        Intent intent = getIntent();
        String nom = intent.getStringExtra(Search.EXTRA_MESSAGE);
        TextView infos = (TextView) findViewById(R.id.informations);
        AsyncPandaJSONProfil profil = new AsyncPandaJSONProfil(Joueur.this);
        profil.execute("https://api.pandascore.co/lol/players?search[name]="+nom+"&token=HkJ-ks2kR-vuOw5vKcush-oWfzU2lmh97cHc5Y0ePCOapO57WcE");


    }
}
