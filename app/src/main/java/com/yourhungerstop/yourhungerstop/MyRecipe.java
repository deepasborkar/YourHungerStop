package com.yourhungerstop.yourhungerstop;

/**
 * Created by Sumegh on 26-05-2016.
 */
public class MyRecipe {

    private String recipeName;
    private String recipeImage;

    public String getRecipeURL() {
        return recipeURL;
    }

    public void setRecipeURL(String recipeURL) {
        this.recipeURL = recipeURL;
    }

    private String recipeURL;

    public MyRecipe(String recipeName) {
        this.recipeName = recipeName;
    }

    public MyRecipe() {

    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }


}
