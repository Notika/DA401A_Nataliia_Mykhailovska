package nnotika.assignment_1;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {} interface
 * to handle interaction events.
 * Use the { FragmentStartnewInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentStart extends Fragment {

    final String LOG_TAG = "Assignment1";

    public FragmentStart() {
        // Required empty public constructor
    }

    public interface fragmentEventListener {
        void quoteButtonEvent();
    }

    fragmentEventListener buttonEvent;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            buttonEvent = (fragmentEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + "must implement fragmendEventListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG, "Executing FragmentStart.onCreate()");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(LOG_TAG, "Executing FragmentStart.onCreateView()");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_fragment_start, null);

        Button button = (Button) v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(LOG_TAG, "Button click in FragmentStart");

                buttonEvent.quoteButtonEvent();
            }
        });
        return v;
    }

    public void onDestroy() {
        buttonEvent = null;
        Log.d(LOG_TAG, "Executing FragmentStart onDestroy");
        super.onDestroy();
    }
}
