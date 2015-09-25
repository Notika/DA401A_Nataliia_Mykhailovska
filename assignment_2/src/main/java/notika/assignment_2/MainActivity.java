package notika.assignment_2;

import notika.assignment_2.MasterFragment.onShowDetailsListener;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements onShowDetailsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MasterFragment masterF = new MasterFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, masterF)
                .commit();
    }

    @Override
    public void eventShowDetails(int elementId, String descr, String m_name) {

        Bundle args = new Bundle();
        DetailFragment detailsF = new DetailFragment();

            args.putInt("id", elementId);
        args.putString("descr", descr);
        args.putString("name", m_name);

        detailsF.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, detailsF)
                .addToBackStack(null)
                .commit();
    }
}
