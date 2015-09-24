package notika.assignment_2;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import java.util.ArrayList;

public class MasterFragment extends Fragment {

    ArrayList<Movie> movies = new ArrayList<>();
    MovieAdapter mAdapter;
    final String LOG_TAG = "Assignment2";

    public MasterFragment() {
    }

    public interface onShowDetailsListener {
        void eventShowDetails(int elementId, String descr, String m_name);
    }

    onShowDetailsListener detailsViewEventListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            detailsViewEventListener = (onShowDetailsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onShowDetailsListener");
        }
    }

    /**
     * This method will only be called once when the retained
     * Fragment is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG, "Executing MasterFragment onCreate");

        // создаем адаптер
        fillData();
        mAdapter = new MovieAdapter(getActivity().getApplicationContext(), movies);

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Log.d(LOG_TAG, "Executing MasterFragment onCreateView");

        GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(mAdapter);

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                // Send event to MainActivity
                int i = movies.get(position).fanart;
                String k = movies.get(position).description;
                String n = movies.get(position).name;
                detailsViewEventListener.eventShowDetails(i, k, n);
            }
        });

        return rootView;
    }

    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "Executing MasterFragment onStart");

        getActivity().setTitle(LOG_TAG);
    }

    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "Executing MasterFragment onResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detailsViewEventListener = null;
    }

    void fillData() {

        for (TypedArray item : ResourceHelper.getMultiTypedArray(getActivity().getApplicationContext())) {
            Movie mMovie = new Movie(item.getString(0), item.getString(1), item.getString(2), item.getResourceId(3, -1), item.getResourceId(4, -1));
            movies.add(mMovie);
        }
    }
}
