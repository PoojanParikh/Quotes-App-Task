package com.poojan.quote;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GridMainActivity extends AppCompatActivity {

    int[] image = new int[]{R.drawable.ic_life,R.drawable.ic_vue,R.drawable.ic_facebook_love,R.drawable.ic_fedora,R.drawable.ic_pinterest_circle,R.drawable.ic_yaoming_meme,R.drawable.ic_mega_icon};
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        new MyGridClass().execute("http://rapidans.esy.es/test/getallcat.php");
    }

    class MyGridClass extends AsyncTask<String, Void, String> {

        ProgressDialog dialog;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(GridMainActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection;
            try {
                URL url = new URL(params[0]);
                try {
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream stream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    String bufferString = buffer.toString();
                    return bufferString;


                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            ArrayList<QuotesCatagoryModel> quotesCatagoryModelArrayList = new ArrayList<>();

            try {

                JSONObject rootObject = new JSONObject(s);


                JSONArray dataArray = rootObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject jsonObject = dataArray.getJSONObject(i);


                    QuotesCatagoryModel p = new QuotesCatagoryModel();

                    p.setId(jsonObject.getInt("id"));
                    p.setCatagory(jsonObject.getString("name"));

                    quotesCatagoryModelArrayList.add(p);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }


            CustomAdapterGrid customAdapterGrid = new CustomAdapterGrid(GridMainActivity.this,quotesCatagoryModelArrayList,image);
            gridView=(GridView) findViewById(R.id.grid_view);
            gridView.setAdapter(customAdapterGrid);
        }
}

}
