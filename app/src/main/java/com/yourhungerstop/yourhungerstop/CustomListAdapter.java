package com.yourhungerstop.yourhungerstop;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends ArrayAdapter<MyRecipe> {
    ArrayList<MyRecipe> recipesList;
    Context context;
    int layoutResourceId;
    ViewHolder holder;
    LayoutInflater inflator;

    public CustomListAdapter(Context context, int resource, ArrayList<MyRecipe> objects) {
        super(context, resource, objects);
        this.context = context;
        inflator = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        layoutResourceId = resource;
        recipesList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View row = convertView;
        if (row == null) {
            holder = new ViewHolder();
            row = inflator.inflate(layoutResourceId, null);
            holder.imageview = (ImageView) row.findViewById(R.id.recipeImage);
            holder.tvTitle = (TextView) row.findViewById(R.id.recipeTitle);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.imageview.setImageResource(R.mipmap.ic_launcher);
        new DownloadImageTask(holder.imageview).execute(recipesList.get(position).getRecipeImage());
        holder.tvTitle.setText(recipesList.get(position).getRecipeName());
        return row;
    }

    static class ViewHolder {
        public ImageView imageview;
        public TextView tvTitle;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            Bitmap bitmap = null;
            try {
                URL uri = new URL(urls[0]);
                urlConnection = (HttpURLConnection) uri.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }
}