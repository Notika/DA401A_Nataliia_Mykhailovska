package notika.assignment_4;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class MyDialogFragment extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final CharSequence[] items = {getArguments().getString("alt1"), getArguments().getString("alt2"), getArguments().getString("alt3")};

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString("question"))
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        MapsActivity callingActivity = (MapsActivity) getActivity();
                        callingActivity.onUserSelectValue(item);
                        dialog.dismiss();
                    }
                });
        return dialog.create();
    }
}
