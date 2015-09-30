package nnotika.assignment_5;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    static WebListAdapter mAdapter;
    private static AsyncListViewLoader newTask;
    static ProgressBar progressBar;
    static List<Quotepad> result;
    ActionMode mActionMode;
    ListView liview;
    int mPosition;

    public ZenFragment() {
    }

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

        liview = (ListView) v.findViewById(R.id.listView);
        liview.setAdapter(mAdapter);
        liview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (mActionMode != null) {
                    return false;
                }
                mPosition = position;
                // Start the CAB using the ActionMode.Callback defined above
                mActionMode = getActivity().startActionMode(mActionModeCallback);
                view.setSelected(true);
                return true;
            }
        });
        return v;
    }

    public void onDestroy() {
        newTask.cancel(true);
        super.onDestroy();
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_cab_movies, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_delete:

                    result.remove(mPosition);
                    mAdapter.notifyDataSetChanged();

                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };

    public static void executeTask() {
        progressBar.setIndeterminate(true);

        if (newTask == null) { // a task can be executed only once
            // Exec async load task
            newTask = new AsyncListViewLoader();
            newTask.execute("https://api.github.com/zen?access_token= ?_TOKEN_?");
        } else if (newTask.getStatus() != AsyncTask.Status.RUNNING) {
            newTask = new AsyncListViewLoader();
            newTask.execute("https://api.github.com/zen?access_token=?_TOKEN_?");
        }
    }

    private static class AsyncListViewLoader extends AsyncTask<String, Void, List<Quotepad>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
            progressBar.setIndeterminate(false);
        }
    }

    private static Quotepad convertQuote(JSONObject obj) throws JSONException {

        String webQuote = obj.getString("quote");
        return new Quotepad(webQuote);
    }
}
