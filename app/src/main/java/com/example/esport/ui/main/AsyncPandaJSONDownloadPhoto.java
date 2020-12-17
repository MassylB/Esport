package com.example.esport.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncPandaJSONDownloadPhoto extends AsyncTask<String, Void, Bitmap> {
    private ImageView Image;
    public AsyncPandaJSONDownloadPhoto(ImageView imageview){
        this.Image = imageview;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url = null;
        Bitmap bm = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection(); // Open
            InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // Stream
            bm = BitmapFactory.decodeStream(in);
            in.close();

        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Image.setImageBitmap(bitmap);
    }
}
