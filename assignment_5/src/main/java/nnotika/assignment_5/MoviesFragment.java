package nnotika.assignment_5;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {
    ArrayList<Movie> movies = new ArrayList<>();
    MovieAdapter mAdapter;
    GridView gridview;
    Activity activity;
    ActionMode mActionMode;
    int mPosition;

    public MoviesFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activity = (Activity) context;
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

        // Retain this fragment across configuration changes.
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        // Create and fill an adapter
        fillData();
        mAdapter = new MovieAdapter(getActivity().getApplicationContext(), movies);

        gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(mAdapter);
        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
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
        return rootView;
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

                    movies.remove(mPosition);
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


    private void fillData() {

        for (TypedArray item : ResourceHelper.getMultiTypedArray(getActivity().getApplicationContext())) {

            Movie mMovie = new Movie(item.getString(0), item.getString(1), item.getString(2), item.getResourceId(3, -1), item.getResourceId(4, -1));
            movies.add(mMovie);
        }
    }
}
