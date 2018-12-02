package com.example.neto.projetofinal.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class MyDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Mudança nos dados")
                .setMessage("Deseja mudar os dados?")
                .setPositiveButton("Sim",this)
                .setNegativeButton("Não", this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String msg = null;
        if(which == DialogInterface.BUTTON_POSITIVE){
            msg = "Você escolheu sim";
        }else{
            if(which == DialogInterface.BUTTON_NEGATIVE) {
                msg = "Você escolheu não";
            }
        }
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}
