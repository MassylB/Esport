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

public class AsyncPandaJSONMatch extends AsyncTask<String,Void, JSONArray>{
    private AppCompatActivity myActivity;

    public AsyncPandaJSONMatch(AppCompatActivity mainActivity) {
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
        String nomopposant1 = new String();
        String nomopposant2 = new String();
        try {
            JSONObject json = array.getJSONObject(0);
            JSONArray opponents = json.getJSONArray("opponents");
            JSONObject opponent1 = opponents.getJSONObject(0);
            JSONObject opponent2 = opponents.getJSONObject(1);

            nomopposant1 = opponent1.getString("name");
            nomopposant2 = opponent2.getString("name");

            String time = json.getString("begin_at");
            String date = time.substring(0,10);
            String heure = time.substring(11,15);

            String urlphoto1 = opponent1.getString("image_url");
            String urlphoto2 = opponent2.getString("image_url");

            description = "Début du match le : "+date+" à "+heure;

            TextView infos = (TextView)myActivity.findViewById(R.id.description);

            TextView opposant1 = (TextView) myActivity.findViewById(R.id.opposant1);
            TextView opposant2 = (TextView) myActivity.findViewById(R.id.opposant2);

            opposant1.setText(nomopposant1);
            opposant2.setText(nomopposant2);
            infos.setText(description);
            //Log.i("CIO",date.substring(0,10));

            ImageView image1 = (ImageView)myActivity.findViewById(R.id.imageView);
            ImageView image2 = (ImageView)myActivity.findViewById(R.id.imageView2);

            AsyncPandaJSONDownloadPhoto photo1 = new AsyncPandaJSONDownloadPhoto(image1);
            AsyncPandaJSONDownloadPhoto photo2 = new AsyncPandaJSONDownloadPhoto(image2);

            photo1.execute(urlphoto1);
            photo2.execute(urlphoto2);
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
