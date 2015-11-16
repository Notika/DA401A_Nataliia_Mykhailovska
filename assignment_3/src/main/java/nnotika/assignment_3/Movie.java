package nnotika.assignment_3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Nataly on 2015-11-15.
 */

public class Movie {

    private String name;
    private String year;
    private String imgUrl;
    private Bitmap image;
    private WebListAdapter weba;

    Movie(String _name, String _year, String _fanart) {
        name = _name;
        year = _year;
        imgUrl = _fanart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Bitmap getImage() {
        return image;
    }

    public WebListAdapter getAdapter() {
        return weba;
    }

    public void setAdapter(WebListAdapter sta) {
        this.weba = sta;
    }

    public void loadImage(WebListAdapter sta) {
        // Hold a reference to the adapter
        this.weba = sta;
        if (imgUrl != null && !imgUrl.equals("")) {
            new AsyncImagesLoader().execute(imgUrl);
        }
    }

    // Async task to avoid choking up UI thread.
    private class AsyncImagesLoader extends AsyncTask<String, String, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(params[0]).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap ret) {
            if (ret != null) {
                image = ret;
                if (weba != null) {
                    // When image is loaded notify the adapter
                    weba.notifyDataSetChanged();
                }
            } else {
                Log.e("ImageLoadTask", "Failed to load " + name + " image");
            }
        }
    }
}
