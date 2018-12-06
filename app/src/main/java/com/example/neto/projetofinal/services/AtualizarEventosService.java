package com.example.neto.projetofinal.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;



public class AtualizarEventosService extends IntentService {

    public static volatile boolean continuar = true;
    StorageReference eventos;
    SimpleDateFormat formatador;
    Date ultimaAtualizacao;
    JSONObject json;

    public AtualizarEventosService() {
        super("AtualizarEventosService");
        this.eventos = FirebaseStorage.getInstance().getReference("cice.json");

        this.formatador = new SimpleDateFormat("HH:mm dd/MM/yyyy");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        while(continuar){

            try {

                StreamDownloadTask.TaskSnapshot t = Tasks.await(this.eventos.getStream());

                InputStream is = t.getStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                json = new JSONObject(buffer.toString());

                Date dateAtual = formatador.parse(json.getString("ultimaatualizacao"));

                if (ultimaAtualizacao == null || !ultimaAtualizacao.equals(dateAtual)) {

                    ultimaAtualizacao = dateAtual;

                    Log.i("MYAPP", "Enviando broadcast de mudança");

                    Intent nIntent = new Intent("cice.android.broadcast.MUDAR_EVENTOS");
                    nIntent.putExtra("JSON", json.toString());
                    sendBroadcast(nIntent);
                }

            } catch (ExecutionException e) {
                Log.e("MYAPP","Erro na obtenção do arquivo no firebase",e);
            } catch (InterruptedException e) {
                Log.e("MYAPP","Erro na interrupção da tarefa de leitura do arquivo",e);
            } catch (IOException e) {
                Log.e("MYAPP","Erro de abertura do arquivo",e);
            } catch (JSONException e) {
                Log.e("MYAPP","Erro na obtenção de valores do JSON",e);
            } catch (ParseException e) {
                Log.e("MYAPP","Erro no conversor para arquivo JSON",e);
            }catch (Exception e ){
                Log.e("MYAPP","Erro desconhecido",e);
            }

            SystemClock.sleep(1000);
        }

        stopSelf();

    }

}
