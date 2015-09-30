package nnotika.assignment_5;

import android.content.Context;
import android.content.res.TypedArray;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nataly on 2015-09-20.
 */
public class ResourceHelper {

    public static List<TypedArray> getMultiTypedArray(Context context) {
        List<TypedArray> array = new ArrayList<>();

        try {
            Class<R.array> res = R.array.class;
            Field field;
            Field[] names = res.getFields();

            for (int i = 0; i < names.length; i++) {
                field = res.getField(names[i].getName().toString());
                array.add(context.getResources().obtainTypedArray(field.getInt(null)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return array;
        }
    }
}