package com.example.neto.projetofinal.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.neto.projetofinal.activitys.MainActivity;
import com.example.neto.projetofinal.bancodedados.evento.Evento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class EventosReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("MYAPP","recebeu broadcast de mudança");

        if (context instanceof MainActivity) {

            MainActivity m = (MainActivity)context;

            List<Evento> listaEventos = new ArrayList<Evento>();

            try {
                JSONObject json = new JSONObject(intent.getStringExtra("JSON"));

                JSONArray eventos = json.getJSONArray("dados");

                for (int i = 0; i < eventos.length(); i++) {
                    try {
                        listaEventos.add(new Evento((JSONObject) eventos.get(i)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            m.atualizarLista(listaEventos);

        }

    }
}
