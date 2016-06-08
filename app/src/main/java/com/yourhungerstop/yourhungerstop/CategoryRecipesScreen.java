package com.yourhungerstop.yourhungerstop;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.json.JSONObject;

public class CategoryRecipesScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_recipes);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //retrieve the category name passed after clicking menu item
        Intent intent = getIntent();
        String categoryName = intent.getStringExtra(getString(R.string.category_name));
        String categoryRecipesURL = getString(R.string.get_category_recipes) + categoryName;

        new FetchCategoryRecipes().execute(categoryRecipesURL);
    }

    private class FetchCategoryRecipes extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            return null;
        }
    }
}
