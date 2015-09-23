package notika.assignment_2;

import notika.assignment_2.MasterFragment.onShowDetailsListener;
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity implements onShowDetailsListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void eventShowDetails(int elementId, String descr){

    	Bundle args = new Bundle();
    	DetailFragment detailsF = new DetailFragment();
    	args.putInt("id", elementId);
        args.putString("descr", descr);
        detailsF.setArguments(args);
        getFragmentManager().beginTransaction()
        .replace(R.id.container, detailsF)
                .addToBackStack(null)
		.commit();
    }
}
