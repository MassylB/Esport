package com.example.esport;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.esport.ui.main.AsyncPandaJSONData;
import com.example.esport.ui.main.AsyncPandaJSONSearch;



import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Search extends AppCompatActivity {
    public static String EXTRA_MESSAGE;
    private String Type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        ListView listView = (ListView) findViewById(R.id.listview);
        TextView tv = (TextView)findViewById(R.id.textView);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv.setText(parent.getItemAtPosition(position).toString());
                Type = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SearchView recherche = findViewById(R.id.recherche);
        recherche.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String recherchetype ="players";

               if (Type.equals("Joueur")) {recherchetype = "players";}
               if (Type.equals("Match")) {recherchetype = "matches";}
               if (Type.equals("Ligue")) {recherchetype = "leagues";}
               if (Type.equals("Tournoi")) {recherchetype = "tournaments";}
               if (Type.equals("Équipe")) {recherchetype = "teams";}
               if (Type.equals("Série")) {recherchetype = "series";}

                AsyncPandaJSONSearch search = new AsyncPandaJSONSearch(Search.this);
                search.execute("https://api.pandascore.co/lol/"+recherchetype+"?search[slug]="+query+"&token=HkJ-ks2kR-vuOw5vKcush-oWfzU2lmh97cHc5Y0ePCOapO57WcE");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        recherche.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Test","Le clique marche");
                //Joueur joueur = new Joueur(parent.getItemAtPosition(position).toString());
                Intent intent = new Intent(getApplicationContext(),Joueur.class);
                intent.putExtra(EXTRA_MESSAGE, parent.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });






    }
}



