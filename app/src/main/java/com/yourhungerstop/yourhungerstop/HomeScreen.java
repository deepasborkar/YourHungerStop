package com.yourhungerstop.yourhungerstop;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //gets the recent posts on load of the page
        new FetchRecentRecipes().execute(getString(R.string.get_recent_posts));
    }

    /**
     * Creates a menu on the toolbar with the menu items defined in categories.xml
     *
     * @param menu
     * @return
     */
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.categories, menu);
        return true;
    }

    @Override
    /**
     * Method called when any menu item is selected
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        String category = (String) item.getTitleCondensed();
        //pass the category name to the CategoryRecipesScreen to retrieve the recipes associated with a category
        Intent intent = new Intent(this, CategoryRecipesScreen.class);
        intent.putExtra(getString(R.string.category_name), category);
        startActivity(intent);
        return true;
    }

    /**
     * Fetches the recent recipes from wordpress using JSON
     */
    private class FetchRecentRecipes extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {

            JSONParser jParser = new JSONParser();
            // Getting JSON from URL
            return jParser.getJSONFromUrl(params[0]);
        }

        @Override
        protected void onPostExecute(JSONObject dataFetched) {
            String title = "", attachment = "";

            ArrayList<MyRecipe> recentRecipesList = new ArrayList<>();

            try {
                //get the JSON array for all posts
                JSONArray recipes = dataFetched.getJSONArray(getString(R.string.posts));

                //iterate through the posts and retrieve recipe details
                for (int i = 0; i < recipes.length(); i++) {
                    JSONObject obj = recipes.getJSONObject(i);
                    title = obj.getString(getString(R.string.title));

                    //get the featured image associated with the post
                    JSONObject imageObject = obj.getJSONObject(getString(R.string.thumbnail_images)).getJSONObject(getString(R.string.medium_large));
                    attachment = imageObject.getString(getString(R.string.url));

                    //create a new MyRecipe object and add it to the recipes list
                    MyRecipe recipe = new MyRecipe();
                    recipe.setRecipeName(title);
                    recipe.setRecipeImage(attachment);
                    recentRecipesList.add(recipe);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //create an ArrayAdapter associated with the listview to display the list of recipes on homescreen
            CustomListAdapter recipesAdapter = new CustomListAdapter(HomeScreen.this, R.layout.my_list, recentRecipesList);

            ListView listView = (ListView) findViewById(R.id.listRecentRecipes);
            listView.setAdapter(recipesAdapter);

        }
    }
}





