package nnotika.assignment_3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class WebListAdapter extends BaseAdapter {

    private Context mContext;
    LayoutInflater lInflater;
    ArrayList<Movie> objects;

    WebListAdapter(Context c, ArrayList<Movie> movies) {
        mContext = c;
        objects = movies;
        lInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    Movie getMovie(int position) {
        return ((Movie) getItem(position));
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
    // create a new ViewHolder for view referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = lInflater.inflate(R.layout.item, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.vDescr);
            holder.year = (TextView) convertView.findViewById(R.id.vYear);
            holder.img = (ImageView) convertView.findViewById(R.id.vImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Movie m = getMovie(position);
        holder.name.setText(m.getName());
        holder.year.setText(m.getYear());
        if (m.getImage() != null) {
            holder.img.setImageBitmap(m.getImage());
        } else {
            // Default image
            holder.img.setImageResource(R.drawable.ic_launcher);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView name;
        TextView year;
        ImageView img;
    }
}