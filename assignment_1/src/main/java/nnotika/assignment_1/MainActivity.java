package nnotika.assignment_1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import nnotika.assignment_1.FragmentStart.fragmendEventListener;

public class MainActivity extends Activity implements fragmendEventListener {

    final String LOG_TAG = "Assignment1";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG, "Executing MainActivity onCreate");

        FragmentStart frag1 = new FragmentStart();
        getFragmentManager().beginTransaction()
                            .add(R.id.main_container, frag1)
                            .addToBackStack(null)
                            .commit();
    }

    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "Executing MainActivity onStart");
    }

    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "Executing MainActivity onResume");
    }

    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "Executing MainActivity onPause");
    }

    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "Executing MainActivity onStop");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "Executing MainActivity onDestroy");
    }

    @Override
    public void quoteButtonEvent()  {

        QuoteFragment frag2 = new QuoteFragment();
        getFragmentManager().beginTransaction()
                            .replace(R.id.main_container, frag2)
                .addToBackStack(null)
                .commit();
    }
}
