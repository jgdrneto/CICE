package com.example.neto.projetofinal.services;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
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


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class AtualizarEventosService extends IntentService {

    public static volatile boolean continuar = true;
    StorageReference eventos;
    SimpleDateFormat formatador;
    Date ultimaAtualizacao;
    JSONObject json;

    public AtualizarEventosService() {
        super("AtualizarEventosService");
        this.eventos = FirebaseStorage.getInstance().getReference("cice.json");

        this.formatador = new SimpleDateFormat("HH:mm dd/MM/yyy");
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

                if(ultimaAtualizacao==null || !ultimaAtualizacao.equals(dateAtual)){

                    ultimaAtualizacao = dateAtual;

                    Log.i("MYAPP","Enviando broadcast de mudança");

                    Intent nIntent = new Intent("cice.android.broadcast.MUDAR_EVENTOS");
                    nIntent.putExtra("JSON",json.toString());
                    sendBroadcast(nIntent);
                }

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Log.i("MYAPP",this.eventos.getName());

            SystemClock.sleep(10000);
        }

        stopSelf();

    }
}
