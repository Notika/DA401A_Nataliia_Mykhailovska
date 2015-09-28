package nnotika.assignment_3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Notika on 9/27/2015.
 */
public class WebListAdapter extends ArrayAdapter<Quotepad> {

    private List<Quotepad> itemList;
    private Context context;

    public WebListAdapter(List<Quotepad> itemList, Context ctx) {
        super(ctx, android.R.layout.simple_list_item_1, itemList);
        this.itemList = itemList;
        this.context = ctx;
    }

    public int getCount() {
        if (itemList != null)
            return itemList.size();
        return 0;
    }

    public Quotepad getItem(int position) {
        if (itemList != null)
            return itemList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (itemList != null)
            return itemList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }

        Quotepad c = itemList.get(position);
        TextView text = (TextView) v.findViewById(R.id.quote);
        text.setText(c.getQuote());

        return v;
    }

    public List<Quotepad> getItemList() {
        return itemList;
    }

    public void setItemList(List<Quotepad> itemList) {
        this.itemList = itemList;
    }
}