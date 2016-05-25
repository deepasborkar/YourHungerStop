package com.yourhungerstop.yourhungerstop;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

public class HomeScreen extends AppCompatActivity {

    private final static String GET_RECENT_POSTS="http://www.yourhungerstop.com/api/get_recent_posts/";
    private Button btnSubmit;
    private TextView tvWordpressData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchDataTask().execute(GET_RECENT_POSTS);
            }
        });


    }
    private class FetchDataTask extends AsyncTask<String, Void, JSONObject> {



        @Override
        protected JSONObject doInBackground(String... params) {

            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getRecentPostsFromUrl(params[0]);
            return json;

        }

        @Override
        protected void onPostExecute(JSONObject dataFetched) {
            //parse the JSON data and then display
            //parseJSON(dataFetched);
        }

    }
    }





