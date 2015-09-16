package nnotika.assignment_1;

import android.content.Context;
import android.content.res.Resources;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Notika on 9/15/2015.
 */
public class Helpers {
    private Context context;

    public Helpers(Context current){
            this.context = current;
    }


    public String getRandomQuote() {
        Resources res = context.getResources();
        String[] quotes = res.getStringArray(R.array.quote_txt_array);

        Random r = new Random();
        int i = r.nextInt(quotes.length);
        return quotes[i];
    }


    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd MMMM yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}
