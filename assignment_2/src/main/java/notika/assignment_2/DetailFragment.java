package notika.assignment_2;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailFragment extends Fragment {

    Activity a;

    public DetailFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            a = (Activity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(a.toString() + " must implement onSaveDetailsListener");
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

        a.setTitle(getArguments().getString("name"));

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // Get data from bundle
        int position = getArguments().getInt("id");
        String description = getArguments().getString("descr");
        ((TextView) view.findViewById(R.id.DetailsText)).setText(description);
        ((ImageView) view.findViewById(R.id.DetailsView)).setImageResource(position);

        return view;
    }

}
