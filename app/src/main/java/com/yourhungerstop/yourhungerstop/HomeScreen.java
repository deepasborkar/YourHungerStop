package com.yourhungerstop.yourhungerstop;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //gets the recent posts on load of the page
        new FetchRecentRecipes().execute(getString(R.string.get_recent_posts));
    }

    private class FetchRecentRecipes extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(params[0]);
            return json;

        }

        @Override
        protected void onPostExecute(JSONObject dataFetched) {
            String title = "", attachment = "";

            ArrayList<MyRecipe> recentRecipesList = new ArrayList<MyRecipe>();

            try {
                //get the JSON array for all posts
                JSONArray recipes = dataFetched.getJSONArray(getString(R.string.posts));

                //iterate through the posts and retrieve recipe details
                for (int i = 0; i < recipes.length(); i++) {
                    JSONObject obj = recipes.getJSONObject(i);
                    title = obj.getString(getString(R.string.title));

                    //get attachments associated with the post and retrieve the first attachment link
                    JSONObject attachmentObject = obj.getJSONArray(getString(R.string.attachments)).getJSONObject(0);
                    attachment = attachmentObject.getString(getString(R.string.url));
                    //create a new MyRecipe object and add it to the recipes list
                    MyRecipe recipe = new MyRecipe();
                    recipe.setRecipeName(title);
                    recipe.setRecipeImage(attachment);
                    recentRecipesList.add(recipe);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            CustomListAdapter recipesAdapter = new CustomListAdapter(HomeScreen.this, R.layout.my_list, recentRecipesList);

            ListView listView = (ListView) findViewById(R.id.listRecentRecipes);
            listView.setAdapter(recipesAdapter);

        }

    }
}





