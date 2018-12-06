package com.example.neto.projetofinal.services;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.example.neto.projetofinal.activitys.EventoActivity;
import com.example.neto.projetofinal.activitys.MainActivity;
import com.example.neto.projetofinal.bancodedados.evento.Evento;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificacaoService extends IntentService {

    public static volatile boolean continuar = true;

    public NotificacaoService() {
        super("NotificacaoService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        SimpleDateFormat formataData = new SimpleDateFormat("HH:mm dd/MM/yyyy");

        while(continuar){

            Date data = new Date();

            for(int i =0; i< MainActivity.list.size();i++ ){

                Evento e = MainActivity.list.get(i);

                if(/*e.equals(data)*/ e.getDataInicio()!=null){

                    Notification.Builder builder = new Notification.Builder(this);

                    builder.setContentTitle("Horario do Evento " + e.getNome());
                    builder.setContentText("Você tem um evento agora ás " + formataData.format(e.getDataInicio()));
                    builder.setSmallIcon(android.R.drawable.sym_action_chat);

                    Intent intent2 = new Intent(getApplicationContext(),EventoActivity.class);
                    intent2.putExtra("EVENTO",e);
                    @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),e.hashCode(),intent2,Intent.FLAG_ACTIVITY_NEW_TASK);
                    builder.setContentIntent(pendingIntent);
                    builder.setAutoCancel(true);

                    Notification notification = builder.build();

                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(e.hashCode(),notification);
                }

            }

            Log.i("MYAPP","ENTROU AQUI FORA");

            SystemClock.sleep(10000);

        }

        stopSelf();
    }
}
