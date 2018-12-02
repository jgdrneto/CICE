package com.example.neto.projetofinal.bancodedados.evento;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Evento {

    String nome;
    String tipo;
    String complemento;
    String nomeLocal;
    Date dataInicio;
    Date dataFinal;
    String endereco;
    String pluscodes;
    SimpleDateFormat format;

    public Evento(JSONObject j) throws JSONException, ParseException {

        this.format = new SimpleDateFormat("HH:mm dd/MM/yyy");

        this.nome = j.getString("nome");
        this.tipo = j.getString("tipo");
        this.complemento = j.getString("complemento");
        this.nomeLocal =  j.getString("nomelocal");
        this.dataInicio = format.parse(j.getString("datainicio"));
        this.dataFinal = format.parse(j.getString("datafinal"));
        this.endereco = j.getString("endereco");
        this.pluscodes = j.getString("pluscodes");

    }


    @Override
    public String toString() {

        SimpleDateFormat hour = new SimpleDateFormat("HH:mm");

        return  "\n" + this.nome + " - " + this.tipo + "\n\n" +
                "Inicio: " + hour.format(this.dataInicio) + "\n" +
                "Fim: " + hour.format(this.dataFinal)+ "\n" ;
    }
}
