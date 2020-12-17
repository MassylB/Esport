package com.example.esport.ui.main;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.esport.R;

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

public class AsyncPandaJSONProfil extends AsyncTask<String,Void, JSONArray> {
    private AppCompatActivity myActivity;

    public AsyncPandaJSONProfil(AppCompatActivity mainActivity) {
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
            String description =new String();
            // Création de la liste
            try {
                JSONObject json = array.getJSONObject(0);

                String pseudo = json.getString("name");
                String prenom = json.getString("first_name");
                String nomdefamille = json.getString("last_name");
                String nationalite = json.getString("hometown");
                String role = json.getString("role");
                String urlphoto = json.getString("image_url");
                JSONObject team = json.getJSONObject("current_team");
                String teamname = team.getString("acronym");
                description = pseudo+"("+prenom+" "+nomdefamille+")\n"+
                        "Équipe : "+teamname+ "\n"+
                        "Pays : "+nationalite+ "\n"+
                        "Role : "+role;
                TextView infos = (TextView)myActivity.findViewById(R.id.informations);
                infos.setText(description);
                //Log.i("CIO",date.substring(0,10));
                ImageView image = (ImageView)myActivity.findViewById(R.id.photo);
                AsyncPandaJSONDownloadPhoto photodeprofil = new AsyncPandaJSONDownloadPhoto(image);
                photodeprofil.execute(urlphoto);
            } catch (JSONException e) {
                e.printStackTrace();
            }


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
