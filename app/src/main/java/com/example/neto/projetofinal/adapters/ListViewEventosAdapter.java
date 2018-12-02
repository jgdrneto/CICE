package com.example.neto.projetofinal.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.neto.projetofinal.bancodedados.evento.Evento;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class ListViewEventosAdapter extends ArrayAdapter<Evento>{

    List<Evento> eventos;

    public ListViewEventosAdapter(Context context, int textViewResourceId, List<Evento> objects) {
        super(context, textViewResourceId, objects);
        eventos = objects;
    }

    public void updateAll(Collection<? extends  Evento> newEventos){
        eventos.clear();
        eventos.addAll(newEventos);
        this.notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return eventos.indexOf(eventos.get(position));
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
    
}
