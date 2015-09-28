package nnotika.assignment_3;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ZenFragment extends Fragment {

    Activity activity;
    WebListAdapter mAdapter;
    private AsyncListViewLoader newTask;
    ProgressBar progressBar;
    List<Quotepad> result;

    public ZenFragment() { /* Required empty public constructor */ }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activity = (Activity) context;
            mAdapter = new WebListAdapter(new ArrayList<Quotepad>(), activity.getApplicationContext());
            result = new ArrayList<>();

        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement ZenFragment");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_zen, null);

        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
        progressBar.setProgress(0);

        ListView liview = (ListView) v.findViewById(R.id.listView);
        liview.setAdapter(mAdapter);

        FloatingActionButton button = (FloatingActionButton) v.findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                progressBar.setProgress(0);

                if (newTask == null) { // a task can be executed only once
                    // Exec async load task
                    newTask = new AsyncListViewLoader();
                    newTask.execute("https://api.github.com/zen?access_token=0f892e365071c7e778a020e463d715b8ccb816f5");
                } else if (newTask.getStatus() != AsyncTask.Status.RUNNING) {
                    newTask = new AsyncListViewLoader();
                    newTask.execute("https://api.github.com/zen?access_token=0f892e365071c7e778a020e463d715b8ccb816f5");
                }
            }
        });
        return v;
    }

    public void onDestroy() {

        newTask.cancel(true);
        super.onDestroy();
    }

    private class AsyncListViewLoader extends AsyncTask<String, Void, List<Quotepad>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setProgress(50);
        }

        @Override
        protected List<Quotepad> doInBackground(String... params) {

            try {
                URL u = new URL(params[0]);

                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                conn.setRequestMethod("GET");

                conn.connect();
                InputStream is = conn.getInputStream();

                // Read the stream
                byte[] b = new byte[512];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                while (is.read(b) != -1)
                    baos.write(b);

                String JSONResp = "[{\"quote\":" + " \"" + baos.toString();
                String newName = JSONResp.substring(0, JSONResp.indexOf(".") + 1) + "\"}]";


                JSONArray arr = new JSONArray(newName);
                for (int i = 0; i < arr.length(); i++) {
                    result.add(convertQuote(arr.getJSONObject(i)));
                }

                return result;
            } catch (Throwable t) {
                t.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Quotepad> result) {
            super.onPostExecute(result);

            mAdapter.setItemList(result);
            mAdapter.notifyDataSetChanged();
            progressBar.setProgress(100);
        }

        private Quotepad convertQuote(JSONObject obj) throws JSONException {

            String webQuote = obj.getString("quote");
            return new Quotepad(webQuote);
        }
    }
}
