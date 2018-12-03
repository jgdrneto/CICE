package com.example.neto.projetofinal.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.example.neto.projetofinal.activitys.MainActivity;

public class AtualizarListaDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    private OnOptionSelectedListener listener;
    private Intent intent;
    public static void show(FragmentManager fm, Intent i, OnOptionSelectedListener listener){
        AtualizarListaDialogFragment dialog = new AtualizarListaDialogFragment();
        dialog.listener = listener;
        dialog.intent =  i;
        dialog.show(fm,"AtualizarListaDialogFragment");
    }

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
        boolean atualiza=false;
        if(which == DialogInterface.BUTTON_POSITIVE){
            atualiza = true;
        }else{
            if(which == DialogInterface.BUTTON_NEGATIVE) {
                atualiza = false;
            }
        }

        listener.onOptionSelected(atualiza, intent, this.getContext());
    }

    public interface OnOptionSelectedListener{
        public void onOptionSelected(boolean opcao, Intent intent,Context c);
    }
}
