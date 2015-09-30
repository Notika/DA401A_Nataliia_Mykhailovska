package nnotika.assignment_5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {

    private Context mContext;
    LayoutInflater lInflater;
    ArrayList<Movie> objects;

    public MovieAdapter(Context c, ArrayList<Movie> movies) {
        mContext = c;

        objects = movies;

        lInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            v = lInflater.inflate(R.layout.item, parent, false);
        }

        Movie m = getMovie(position);

        ((TextView) v.findViewById(R.id.vDescr)).setText(m.name);
        ((ImageView) v.findViewById(R.id.vImage)).setImageResource(m.poster);

        return v;
    }

    Movie getMovie(int position) {
        return ((Movie) getItem(position));
    }
}