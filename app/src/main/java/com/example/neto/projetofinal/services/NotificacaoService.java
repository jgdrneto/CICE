package com.example.neto.projetofinal.services;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.example.neto.projetofinal.activitys.MainActivity;
import com.example.neto.projetofinal.bancodedados.evento.Evento;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NotificacaoService extends IntentService {

    public static volatile boolean continuar = true;

    public NotificacaoService() {
        super("NotificacaoService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        SimpleDateFormat formataData = new SimpleDateFormat("HH:mm dd/MM/yyyy");

        while(continuar){

            Log.i("MYAPP", MainActivity.list.toString());

            Date data = new Date();

            for(Evento e :  MainActivity.list){

                if(/*e.equals(data)*/ e.getNome().equals("Programação dinâmica")){

                    Notification.Builder builder = new Notification.Builder(this);

                    builder.setContentTitle("Horario do Evento " + e.getNome());
                    builder.setContentText("Você tem um evento agora ás " + formataData.format(e.getDataInicio()));
                    builder.setSmallIcon(android.R.drawable.sym_action_chat);

                    Intent intent2 = new Intent();
                    PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent2,0);
                    builder.setContentIntent(pendingIntent);
                    builder.setAutoCancel(true);

                    Notification notification = builder.build();

                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    nm.notify(50,notification);
                }

            }

            Log.i("MYAPP","ENTROU AQUI FORA");

            SystemClock.sleep(10000);

        }

        stopSelf();
    }
}
