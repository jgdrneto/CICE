package com.example.neto.projetofinal.bancodedados.preferencia;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

public class ManipuladorPreferencias {

    String nomeArquivo;
    Context context;

    public ManipuladorPreferencias(String nNomeArquivo, Context c){
        this.nomeArquivo = nNomeArquivo;
        this.context = c;

        //CRIANDO O ARQUIVO SE ELE NAO EXISTIR
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.context.openFileOutput(this.nomeArquivo, Context.MODE_APPEND));
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void atualizarPreferencias(String [] chaves, boolean [] valores){

        if(chaves.length==valores.length) {

            JSONObject preferencias = this.gerarJSONObject();

            for (int i = 0; i < chaves.length; i++) {
                try {
                    preferencias.put(chaves[i],valores[i]);
                } catch (JSONException e) {
                    Log.e("MYAPP", "Falha ao colocar valores na chave " + chaves[i] + " do arquivo " + this.nomeArquivo, e);
                }
            }

            this.gravarNoArquivo(preferencias.toString());

        }
    }

    public JSONObject gerarJSONObject(){

        String atuaisPreferencias = this.lerDoArquivo();

        try {
            return new JSONObject(atuaisPreferencias);
        } catch (JSONException e) {
            Log.e("MYAPP", "Falha ao criar objeto JSON com os dados do arquivo " + this.nomeArquivo, e);
        }

        return new JSONObject();
    }

    public void gravarNovasPreferencias(List<String> novasPreferencias){

        JSONObject preferencias = this.gerarJSONObject();

        Iterator<String> prefs = preferencias.keys();

        while(prefs.hasNext()){

            String pref = prefs.next();

            if(!novasPreferencias.contains(pref)){
                prefs.remove();
            }else{
                novasPreferencias.remove(pref);
            }
        }

        for(String i : novasPreferencias){
            try {
                preferencias.put (i,true);
            } catch (JSONException e) {
                Log.e("MYAPP", "Falha ao colocar valores na chave " + i + " do arquivo " + this.nomeArquivo, e);
            }
        }

        this.gravarNoArquivo(preferencias.toString());

    }



    private void gravarNoArquivo(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.context.openFileOutput(this.nomeArquivo, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("MYAPP", "Falha em gravar dados no arquivo " + this.nomeArquivo, e);
        }
    }

    private String lerDoArquivo() {

        String ret = "";

        try {
            InputStream inputStream = this.context.openFileInput(this.nomeArquivo);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();

                if(stringBuilder.toString().isEmpty()){
                    ret = "{}";
                }else {

                    ret = stringBuilder.toString();
                }
            }
        }
        catch (FileNotFoundException e) {
            Log.e("MYAPP", "Arquivo " + this.nomeArquivo + " não encontrado",e);
        } catch (IOException e) {
            Log.e("MYAPP", "Falha em ler dados no arquivo " + this.nomeArquivo, e);
        }

        return ret;
    }



}
