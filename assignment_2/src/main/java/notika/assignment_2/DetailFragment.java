package notika.assignment_2;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailFragment extends Fragment {

    public DetailFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //detailsViewEventListener = (onSaveDetailsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement onSaveDetailsListener");
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

        getActivity().setTitle(getArguments().getString("name"));

        View view = inflater.inflate(R.layout.fragment_details, container, false);

        // Get data from bundle
        int position = getArguments().getInt("id");
        String description = getArguments().getString("descr");
        ((ImageView) view.findViewById(R.id.DetailsView)).setImageResource(position);
        ((TextView) view.findViewById(R.id.DetailsText)).setText(description);
        return view;
    }

}
