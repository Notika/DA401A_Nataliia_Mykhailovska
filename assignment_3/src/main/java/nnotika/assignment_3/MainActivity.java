package nnotika.assignment_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ZenFragment zenFragment = new ZenFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_container, zenFragment)
                .commit();
    }

}
