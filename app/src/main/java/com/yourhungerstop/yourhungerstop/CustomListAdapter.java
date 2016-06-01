package com.yourhungerstop.yourhungerstop;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            HttpURLConnection urlConnection;
            Bitmap bitmap = null;
            try {
                URL uri = new URL(urls[0]);
                urlConnection = (HttpURLConnection) uri.openConnection();

                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                if (inputStream != null) {

                    //read the image details from the input stream
                    BitmapFactory.Options options = new BitmapFactory.Options();

                    options.inSampleSize = 4;

                    // Decode bitmap with inSampleSize set
                    bitmap = BitmapFactory.decodeStream(inputStream, null, options);
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


        /**
         * -------------------------------------------------------------
         * METHOD NOT USED CURRENTLY. CAN BE USED IN FUTURE IF REQUIRED
         * --------------------------------------------------------------
         * This method will retrieve the actual size of the image and then calculate the inSampleSize
         * value to scale it down. This helps in avoiding out of memory error for large image sizes
         *
         * @param options    : The BitmapFactory options object which has the details about the image
         * @param reqWidth   : The required width
         * @param reqHeight: The required height
         * @return inSampleSize value
         */
        public int calculateInSampleSize(
                BitmapFactory.Options options, int reqWidth, int reqHeight) {
            // Raw height and width of image
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth) {

                final int halfHeight = height / 2;
                final int halfWidth = width / 2;

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while ((halfHeight / inSampleSize) > reqHeight
                        && (halfWidth / inSampleSize) > reqWidth) {
                    inSampleSize *= 2;
                }
            }

            return inSampleSize;
        }
    }
}