package com.example.esport.ui.main;

import android.os.AsyncTask;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.esport.Fragmentajd;
import com.example.esport.MainActivity;
import com.example.esport.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AsyncPandaJSONData extends AsyncTask<String,Void, JSONArray> {

    private AppCompatActivity myActivity;
    private String ajd;
    private String demain;
    private String hier;
    public AsyncPandaJSONData (AppCompatActivity mainActivity,String ajd,String demain,String hier) {
        myActivity = mainActivity;
        this.ajd = ajd;
        this.demain = demain;
        this.hier = hier;
    }

    @Override
    protected JSONArray doInBackground(String... strings) {

        URL url = null;
        try {
            url = new URL(strings[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        String result = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection(); // Open
            InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // Stream

            result = readStream(in); // Read stream
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        JSONArray json = null;
        try {
            json = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String s = null;
        try {

            JSONObject a = json.getJSONObject(5);
            s = a.getString("name");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.i("CIO",s);
        return json;
    }
    @Override
   protected void onPostExecute(JSONArray array) {
        List<String> listajd = new ArrayList<String>();
        List<String> listdemain = new ArrayList<String>();
        List<String> listhier = new ArrayList<String>();
        for (int i = 0; i < array.length(); i++){
            // Création de la liste des leagues
            try {
                JSONObject json = array.getJSONObject(i);
                String name = json.getString("name");
                String date = json.getString("begin_at");
                JSONArray opposants = json.getJSONArray("opponents");
                JSONObject opposant1 = opposants.getJSONObject(0);
                JSONObject infos1 = opposant1.getJSONObject("opponent");

                JSONObject opposant2 = opposants.getJSONObject(1);
                JSONObject infos2 = opposant2.getJSONObject("opponent");
                String affiche = infos1.getString("acronym") +" vs " + infos2.getString("acronym");

                Log.i("CIO",date.substring(0,10));
                if (date.substring(0, 10).equals(ajd)) {
                    listajd.add(affiche);
                }
                if (date.substring(0, 10).equals(demain)) {
                    listdemain.add(affiche);
                }
                if (date.substring(0, 10).equals(hier)) {
                    listhier.add(affiche);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //String s = list.get(0);
        //Log.i("CIO",s);
       //Création du SectionsPagerAdapter
       SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(myActivity.getApplicationContext(), myActivity.getSupportFragmentManager(),listajd,listhier,listdemain);
       ViewPager viewPager = (ViewPager)myActivity.findViewById(R.id.view_pager);
       viewPager.setAdapter(sectionsPagerAdapter);
       TabLayout tabs = (TabLayout)myActivity.findViewById(R.id.tabs);
       tabs.setupWithViewPager(viewPager);

    }



   private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),200000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        is.close();

        // Extracting the JSON object from the String
        String jsonextracted = sb.toString();
        //Log.i("CIO", jsonextracted);
        return jsonextracted;
    }
}
