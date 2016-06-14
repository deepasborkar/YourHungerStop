package com.yourhungerstop.yourhungerstop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Created by Sumegh on 14-06-2016.
 */
public class DisplayRecipeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.webview_recipe);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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


}
