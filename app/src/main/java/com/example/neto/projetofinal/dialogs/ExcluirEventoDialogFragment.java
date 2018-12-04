package com.example.neto.projetofinal.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.example.neto.projetofinal.bancodedados.evento.Evento;

public class ExcluirEventoDialogFragment extends DialogFragment implements DialogInterface.OnClickListener{

    private OnOptionSelectedListener listener;
    private Evento evento;

    public static void show(FragmentManager fm, Evento e, OnOptionSelectedListener listener){
        ExcluirEventoDialogFragment dialog = new ExcluirEventoDialogFragment();
        dialog.listener = listener;
        dialog.evento = e;
        dialog.show(fm,"AtualizarListaDialogFragment");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Excluir Evento")
                .setMessage("Você Realmente deseja excluir o evento?")
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

        listener.onOptionSelected(atualiza,this.evento, this.getContext());
    }

    public interface OnOptionSelectedListener{
        public void onOptionSelected(boolean opcao,Evento evento, Context c);
    }
}
