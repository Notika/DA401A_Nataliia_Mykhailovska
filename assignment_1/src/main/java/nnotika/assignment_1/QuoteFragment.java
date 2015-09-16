package nnotika.assignment_1;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
  * to handle interaction events.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class QuoteFragment extends Fragment {

    final String LOG_TAG = "Assignment1";
    Context c;

    public QuoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        c = activity.getApplicationContext();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(LOG_TAG, "Executing QuoteFragment.onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(LOG_TAG, "Executing QuoteFragment.onCreateView()");

        Helpers helpersClass = new Helpers(c);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_quote, null);

        ((TextView) v.findViewById(R.id.dateTextView)).setText(helpersClass.getDateTime());

        ((TextView) v.findViewById(R.id.quoteTextView)).setText(helpersClass.getRandomQuote());

        return v;
    }


}
