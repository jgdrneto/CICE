package com.example.neto.projetofinal.dialogs;

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
import android.widget.Toast;

import com.example.neto.projetofinal.bancodedados.evento.Evento;

import java.util.HashMap;
import java.util.Map;

public class AtualizarPreferenciasDialogFragment extends DialogFragment implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnClickListener{

    private String [] itens;
    private boolean [] itensChecked;
    private OnUpdatePreferencesListener listener;

    public static void show(FragmentManager fm, Evento e, OnUpdatePreferencesListener nListener){
        AtualizarPreferenciasDialogFragment dialog = new AtualizarPreferenciasDialogFragment();
        dialog.listener = nListener;
        dialog.show(fm,"AtualizarPreferenciasDialogFragment");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Map<String,Boolean> lista = new HashMap<String, Boolean>();

        this.itens = (String[]) lista.keySet().toArray();

        Boolean [] checked = (Boolean[])lista.values().toArray();

        for(int i=0;i<checked.length;i++){
            this.itensChecked[i] = checked[i];
        }

        return new AlertDialog.Builder(getActivity())
                .setMultiChoiceItems(this.itens,itensChecked,this)
                .setPositiveButton("Salvar",this)
                .setNegativeButton("Cancelar", this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

        String tipo = this.itens[which];

        if(isChecked){
            this.itensChecked[which] = true;
            Toast.makeText(getActivity(),"Escolhido eventos do tipo " + tipo, Toast.LENGTH_SHORT).show();
        }else{
            this.itensChecked[which] = false;
            Toast.makeText(getActivity(),"Não escolhido eventos do tipo " + tipo, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if(which == DialogInterface.BUTTON_POSITIVE){

            this.listener.onUpdatePreferences(this.itens,this.itensChecked);

        }

    }

    public interface OnUpdatePreferencesListener{
        public void onUpdatePreferences(String[] preferencias,boolean [] valores);
    }

}
