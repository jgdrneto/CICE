package com.example.neto.projetofinal.bancodedados.preferencia;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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


    void gravarNovasPreferencias(List<String> novasPreferencias){

        try {

            String atuaisPreferencias = this.lerDoArquivo();

            JSONObject preferencias = new JSONObject(atuaisPreferencias);

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(this.context.openFileOutput(this.nomeArquivo, Context.MODE_PRIVATE));

        } catch (IOException e) {
            Log.e("MYAPP", "Falha em gravar dados no arquivo " + this.nomeArquivo, e);
        } catch (JSONException e) {
            Log.e("MYAPP", "Falha ao criar objeto JSON com os dados do arquivo " + this.nomeArquivo, e);
        }
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
                ret = stringBuilder.toString();
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
