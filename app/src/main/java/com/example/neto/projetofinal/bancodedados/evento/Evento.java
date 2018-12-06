package com.example.neto.projetofinal.bancodedados.evento;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Evento implements Serializable {

    private static final long serialVersionUID = 5782589762770668432L;

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

        this.format = new SimpleDateFormat("HH:mm dd/MM/yyyy");

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

        return  "\n" + this.nome + " - " + this.tipo + "\n\n" +
                "Inicio: " + this.format.format(this.dataInicio) + "\n" +
                "Fim:    " + this.format.format(this.dataFinal)+ "\n" ;
    }

    public String getNome() {
        return nome;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNomeLocal() {
        return nomeLocal;
    }

    public void setNomeLocal(String nomeLocal) {
        this.nomeLocal = nomeLocal;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getPluscodes() {
        return pluscodes;
    }

    public void setPluscodes(String pluscodes) {
        this.pluscodes = pluscodes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return Objects.equals(nome, evento.nome) &&
                Objects.equals(tipo, evento.tipo) &&
                Objects.equals(complemento, evento.complemento) &&
                Objects.equals(nomeLocal, evento.nomeLocal) &&
                Objects.equals(dataInicio, evento.dataInicio) &&
                Objects.equals(dataFinal, evento.dataFinal) &&
                Objects.equals(endereco, evento.endereco) &&
                Objects.equals(pluscodes, evento.pluscodes);
    }

    @Override
    public int hashCode() {

        int i = Objects.hash(nome, tipo, complemento, nomeLocal, dataInicio, dataFinal, endereco, pluscodes);

        if(i<0){
            return i*(-1);
        }else{
            return i;
        }
    }
}
