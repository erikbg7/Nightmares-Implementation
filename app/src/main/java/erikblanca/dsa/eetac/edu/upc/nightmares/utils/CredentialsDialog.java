package erikblanca.dsa.eetac.edu.upc.nightmares.utils;


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

import erikblanca.dsa.eetac.edu.upc.nightmares.R;


public class CredentialsDialog extends DialogFragment {

    private OnDialogListener onDialogListener;

    public interface OnDialogListener{
        void OnPositiveButtonClicked(String name, String password); }

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

        View view = getActivity().getLayoutInflater().inflate(R.layout.credentials_layout, null);

        final EditText name = view.findViewById(R.id.Name);
        final EditText password = view.findViewById(R.id.Password);


        builder.setTitle("Credentials")
                .setMessage("Fill the fields and click confirm.")
                .setView(view)
                //.setView(inflater.inflate(R.layout.credentials_layout, null))
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDialogListener.OnPositiveButtonClicked(name.getText().toString(), password.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", null);

        return builder.create();

    }
}


