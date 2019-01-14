package erikblanca.dsa.eetac.edu.upc.nightmares;

/**
 * Created by erikb on 1/13/2019.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


public class NewTrkDialog extends DialogFragment {

    private OnDialogListener onDialogListener;

    public interface OnDialogListener{
        void OnPositiveButtonClicked(String singer, String title); }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        onDialogListener = (OnDialogListener) getActivity();

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = getActivity().getLayoutInflater().inflate(R.layout.new_track_layout, null);

        final EditText singer = view.findViewById(R.id.Singer);
        final EditText title = view.findViewById(R.id.Title);


        builder.setTitle("Add a new Track")
                .setMessage("Fill the fields and click confirm.")
                .setView(view)
                //.setView(inflater.inflate(R.layout.new_track_layout, null))
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDialogListener.OnPositiveButtonClicked(singer.getText().toString(), title.getText().toString());
                        Log.d("QuestionsCallback", "//////////////////////////////////  SUCCES????????:" +singer.getText() + "   "+title.getText()+ "///////////////////////////////");

                    }
                })
                .setNegativeButton("Cancel", null);

        return builder.create();

    }
}

