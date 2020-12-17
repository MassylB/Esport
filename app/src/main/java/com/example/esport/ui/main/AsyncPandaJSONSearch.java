package com.example.esport.ui.main;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.esport.R;
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

import static java.lang.String.valueOf;

public class AsyncPandaJSONSearch extends AsyncTask<String,Void, JSONArray> {
    private AppCompatActivity myActivity;

    public AsyncPandaJSONSearch(AppCompatActivity mainActivity) {
        myActivity = mainActivity;
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

            JSONObject a = json.getJSONObject(0);
            s = a.getString("slug");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("CIO", s + json.length());
        return json;
    }
    @Override
    protected void onPostExecute(JSONArray array) {
        List<String> listeresult = new ArrayList<String>();
        for (int i = 0; i < array.length(); i++){
            // Création de la liste
            try {
                JSONObject json = array.getJSONObject(i);
                String name = json.getString("name");
                //JSONObject team = json.getJSONObject("current_team");
                //String teamname = team.getString("acronym");
                listeresult.add(name);



                //Log.i("CIO",date.substring(0,10));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String[] listefinale = new String[listeresult.size()];

        for (int i = 0; i < listeresult.size(); i++ ){
            listefinale[i] = listeresult.get(i);
        }
        ListView listView = (ListView)myActivity.findViewById(R.id.listview);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(myActivity, android.R.layout.simple_list_item_1, listefinale);

        listView.setAdapter(listViewAdapter);

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
        Log.i("CIO", jsonextracted);
        return jsonextracted;
    }
}

