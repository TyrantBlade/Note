package com.example.note;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.note.R;

public class NameDialog extends AppCompatDialogFragment {
    private EditText editText;
    public DataDialog dataDialog;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.jdialog_name_layout,null);

        builder.setView(view);
        builder.setTitle(R.string.nwFile);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(R.string.create, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String filename=editText.getText().toString();
                dataDialog.retrieveNameFile(filename);
            }
        });
        editText=view.findViewById(R.id.tx_choice_name);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            dataDialog=(DataDialog) context;
        }catch (ClassCastException ex)
        {
            throw new ClassCastException(context.toString());
        }

    }

    public interface DataDialog{
        void retrieveNameFile(String nameFile);
    }
}
