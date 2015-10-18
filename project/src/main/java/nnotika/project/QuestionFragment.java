package nnotika.project;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;

/**
 * Created by Nataly on 2015-10-17.
 */
public class QuestionFragment extends Fragment {

    Activity a;
    String id;
    EditText interestingThing;

    public QuestionFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            a = (Activity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(a.toString() + " must implement QuestionFragment");
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

        Firebase.setAndroidContext(a.getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.question_fragment, container, false);
        interestingThing = (EditText) view.findViewById(R.id.editText2);

        // Get data from bundle
        id = getArguments().getString("id");

        Button submitB = (Button) view.findViewById(R.id.button3);
        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Firebase myFirebaseRef = new Firebase("https://instantify.firebaseio.com/");
                myFirebaseRef.child("Answer5").setValue("Do you have data? You'll love Firebase.");

            }
        });

        return view;
    }
}
