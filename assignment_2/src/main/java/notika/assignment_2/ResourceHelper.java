package notika.assignment_2;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nataly on 2015-09-20.
 */
public class ResourceHelper {

    public static List<TypedArray> getMultiTypedArray(Context context) {

        List<TypedArray> array = new ArrayList<>();
        Resources res = context.getResources();
        TypedArray index = res.obtainTypedArray(R.array.movies); //call the index array

        try {
            for (int i = 0; i < index.length(); i++) {
                int id = index.getResourceId(i, 0); //get the index
                if (id != 0) {

                    array.add(res.obtainTypedArray(id));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return array;
        }
    }
}