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
import android.util.Log;
import android.widget.Toast;

import com.example.neto.projetofinal.bancodedados.evento.Evento;
import com.example.neto.projetofinal.bancodedados.preferencia.ManipuladorPreferencias;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AtualizarPreferenciasDialogFragment extends DialogFragment implements DialogInterface.OnMultiChoiceClickListener, DialogInterface.OnClickListener{

    private String [] itens;
    private boolean [] itensChecked;
    private OnUpdatePreferencesListener listener;
    public ManipuladorPreferencias manipuladorPreferencias;

    public static void show(FragmentManager fm, OnUpdatePreferencesListener nListener,ManipuladorPreferencias nManipulador){
        AtualizarPreferenciasDialogFragment dialog = new AtualizarPreferenciasDialogFragment();
        dialog.listener = nListener;
        dialog.manipuladorPreferencias =  nManipulador;
        dialog.show(fm,"AtualizarPreferenciasDialogFragment");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        JSONObject j = manipuladorPreferencias.gerarJSONObject();

        List<String> chaves = new ArrayList<String>();
        List<Boolean> valores =  new ArrayList<Boolean>();

        Iterator<String> chave = j.keys();

        while(chave.hasNext()) {

            String v = chave.next();
            chaves.add(v);
            try {
                valores.add(new Boolean(j.getBoolean(v)));
            } catch (JSONException e) {
                Log.e("MYAPP", "Erro ao obter os valores das chaves", e);
            }
        }

        this.itens = new String[chaves.size()];
        this.itensChecked = new boolean[valores.size()];

        for(int i=0;i<chaves.size();i++){
            this.itens[i] = chaves.get(i);
            this.itensChecked[i] = valores.get(i);
        }

        return new AlertDialog.Builder(getActivity())
                .setMultiChoiceItems(this.itens,this.itensChecked,this)
                .setPositiveButton("Salvar",this)
                .setNegativeButton("Cancelar", this)
                .create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

        String tipo = this.itens[which];

        if(isChecked){
            this.itensChecked[which] = true;
            //Toast.makeText(getActivity(),"Escolhido eventos do tipo " + tipo, Toast.LENGTH_SHORT).show();
        }else{
            this.itensChecked[which] = false;
            //Toast.makeText(getActivity(),"Não escolhido eventos do tipo " + tipo, Toast.LENGTH_SHORT).show();
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
