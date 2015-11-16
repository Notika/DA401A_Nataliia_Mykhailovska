package nnotika.assignment_3;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ZenFragment extends Fragment {

    Activity activity;
    WebListAdapter mAdapter;
    private AsyncListViewLoader newTask;
    ProgressBar progressBar;
    ArrayList<Movie> movies = new ArrayList<>();

    public ZenFragment() { /* Required empty public constructor */ }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activity = (Activity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement ZenFragment");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new WebListAdapter(getActivity().getApplicationContext(), movies);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_zen, null);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);

        GridView gridview = (GridView) v.findViewById(R.id.gridview);
        gridview.setAdapter(mAdapter);

        if (newTask == null) { // a task can be executed only once
            // Exec async load task
            newTask = new AsyncListViewLoader();
            newTask.execute("https://api-v2launch.trakt.tv/movies/popular?extended=images");
        } else if (newTask.getStatus() != AsyncTask.Status.RUNNING) {
            newTask = new AsyncListViewLoader();
            newTask.execute("https://api-v2launch.trakt.tv/movies/popular?extended=images");
        }
        return v;
    }

    public void onDestroy() {

        newTask.cancel(true);
        super.onDestroy();
    }

    private class AsyncListViewLoader extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setProgress(50);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            BufferedReader rd;
            ArrayList<Movie> films = new ArrayList<>();
            StringBuilder sb;
            String line;
            HttpURLConnection conn = null;
            try {
                URL u = new URL(params[0]);

                conn = (HttpURLConnection) u.openConnection();
                conn.setRequestProperty("trakt-api-version", "2");
                conn.setRequestProperty("trakt-api-key", "492a165927bfaff86b3030454939981d4e2d94c50515e15e42f41fbf57481a44");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("GET");
                conn.connect();

                // read the result from the server
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                sb = new StringBuilder();

                while ((line = rd.readLine()) != null) {
                    sb.append(line);
                }

                try {
                    JSONArray ridesJsonArray = new JSONArray(sb.toString());

                    for (int i = 0; i < ridesJsonArray.length(); i++) {
                        Movie movie = new Movie("0", "0", "0");
                        JSONObject o = ridesJsonArray.optJSONObject(i);
                        movie.setName(o.optString("title"));
                        movie.setYear(o.optString("year"));
                        JSONObject images = o.getJSONObject("images");
                        JSONObject type = images.getJSONObject("poster");
                        movie.setImgUrl(type.optString("thumb"));
                        films.add(movie);
                    }
                    return films;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // close the connection
                conn.disconnect();
            }
            return films;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> result) {
            super.onPostExecute(result);

            movies.clear();
            movies.addAll(result);

            for (Movie m : movies) {
                // Start to load images for each movie
                m.loadImage(mAdapter);
            }
            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
