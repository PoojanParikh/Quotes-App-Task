package com.poojan.quote;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

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

/**
 * Created by VNurtureTechnologies on 17/02/17.
 */

public class ListActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);

        int position = getIntent().getIntExtra("QuotesList",-1);

        new MyListClass().execute("http://rapidans.esy.es/test/getquotes.php?cat_id=" +position);
    }

    class MyListClass extends AsyncTask<String, Void, String> {

        ProgressDialog dialog;
        ArrayList<QuotesModel> quotesModelArrayList;
        CustomAdapterList customAdapterList;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ListActivity.this);
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
            quotesModelArrayList = new ArrayList<>();

            try {

                JSONObject rootObject = new JSONObject(s);


                JSONArray dataArray = rootObject.getJSONArray("data");
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject jsonObject = dataArray.getJSONObject(i);


                    QuotesModel p = new QuotesModel();

                    p.setId(jsonObject.getInt("id"));
                    p.setCatId(jsonObject.getInt("cat_id"));
                    p.setQuotes(jsonObject.getString("quotes"));

                    quotesModelArrayList.add(p);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            customAdapterList = new CustomAdapterList(ListActivity.this,quotesModelArrayList);
            listView=(ListView) findViewById(R.id.list_view);
            listView.setAdapter(customAdapterList);
        }

    }

}
