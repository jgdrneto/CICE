package com.example.neto.projetofinal.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.neto.projetofinal.activitys.MainActivity;
import com.example.neto.projetofinal.bancodedados.evento.Evento;
import com.example.neto.projetofinal.dialogs.AtualizarListaDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class EventosReceiver extends BroadcastReceiver implements AtualizarListaDialogFragment.OnOptionSelectedListener {

    private boolean init = true;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("MYAPP","recebeu broadcast de mudança");

        if (context instanceof MainActivity) {

            if(init){
                this.onOptionSelected(true,intent,context);
                init=false;
            }else {

                MainActivity m = (MainActivity) context;

                AtualizarListaDialogFragment.show(m.getSupportFragmentManager(), intent, this);
            }
        }
    }


    @Override
    public void onOptionSelected(boolean opcao, Intent intent, Context context) {

        if(opcao && context instanceof MainActivity) {

            MainActivity m = (MainActivity) context;

            List<Evento> listaEventos = new ArrayList<Evento>();
            List<String> preferencias = new ArrayList<String>();

            try {
                JSONObject json = new JSONObject(intent.getStringExtra("JSON"));

                JSONArray eventos = json.getJSONArray("dados");

                for (int i = 0; i < eventos.length(); i++) {
                    try {
                        Evento e = new Evento((JSONObject) eventos.get(i));

                        listaEventos.add(e);

                        if(!preferencias.contains(e.getTipo())){
                            preferencias.add(e.getTipo());
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            m.atualizarPreferencias(preferencias);
            m.atualizarLista(listaEventos);
        }

    }
}
