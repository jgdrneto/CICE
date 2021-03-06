package com.example.neto.projetofinal.activitys;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.neto.activitys.R;
import com.example.neto.projetofinal.adapters.ListViewEventosAdapter;
import com.example.neto.projetofinal.bancodedados.evento.Evento;
import com.example.neto.projetofinal.bancodedados.preferencia.ManipuladorPreferencias;
import com.example.neto.projetofinal.broadcasts.EventosReceiver;
import com.example.neto.projetofinal.dialogs.AtualizarPreferenciasDialogFragment;
import com.example.neto.projetofinal.dialogs.ExcluirEventoDialogFragment;
import com.example.neto.projetofinal.services.AtualizarEventosService;
import com.example.neto.projetofinal.services.NotificacaoService;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener,
                    AdapterView.OnItemClickListener,
                    AdapterView.OnItemLongClickListener,
                    ExcluirEventoDialogFragment.OnOptionSelectedListener,
                    AtualizarPreferenciasDialogFragment.OnUpdatePreferencesListener {

    ManipuladorPreferencias manipuladorPreferencias;

    public static volatile List<Evento> list = Collections.synchronizedList(new ArrayList<Evento>());
    private List<Evento> backup = new ArrayList<Evento>();
    ListViewEventosAdapter adapter;
    ListView listView;
    String email;
    private FirebaseAuth auth;
    EventosReceiver eventosReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            public void onDrawerOpened(View drawerView) {
                TextView t = findViewById(R.id.textView_email);
                t.setText(email);
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        this.email = getIntent().getStringExtra("EMAIL");

        this.auth = FirebaseAuth.getInstance();

        initServices();

        initReceives();

        this.listView = findViewById(R.id.listaEventos);

        this.adapter = new ListViewEventosAdapter(this,
                android.R.layout.simple_list_item_1, list);

        this.listView.setAdapter(adapter);
        this.listView.setOnItemClickListener(this);
        this.listView.setOnItemLongClickListener(this);

        this.manipuladorPreferencias = new ManipuladorPreferencias(this.email+".json",this);

    }

    private void initReceives() {

        this.eventosReceiver =  new EventosReceiver();
        IntentFilter intentFilter =  new IntentFilter();
        intentFilter.addAction("cice.android.broadcast.MUDAR_EVENTOS");
        registerReceiver(eventosReceiver,intentFilter);

    }

    private void initServices() {

        AtualizarEventosService.continuar = true;

        Intent atueveservice = new Intent(getApplicationContext(),AtualizarEventosService.class);
        startService(atueveservice);

        NotificacaoService.continuar = true;
        Intent notservice = new Intent(getApplicationContext(),NotificacaoService.class);
        startService(notservice);
    }

    private  void stopReceivers(){
        if(this.eventosReceiver!=null){
            unregisterReceiver(eventosReceiver);
        }
    }

    private void stopServices(){
        AtualizarEventosService.continuar = false;
        NotificacaoService.continuar = false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sobre) {
            Intent i = new Intent(getApplicationContext(),SobreActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_preferencias) {

            AtualizarPreferenciasDialogFragment.show(getSupportFragmentManager(),this, this.manipuladorPreferencias);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void atualizarLista(List<Evento> listaEventos) {

        this.backup = new ArrayList<Evento>(listaEventos);

        JSONObject json = manipuladorPreferencias.gerarJSONObject();

        List<Evento> eventos =  new ArrayList<Evento>();

        for(int i=0;i<listaEventos.size();i++){
            try {
                if(json.getBoolean(listaEventos.get(i).getTipo())==true){
                    eventos.add(listaEventos.get(i));
                }
            } catch (JSONException e1) {
                Log.e("MYAPP","Erro na obtenção do tipo de evento " + listaEventos.get(i).getTipo(), e1);
            }
        }

        this.adapter.updateAll(eventos);
    }

    public void atualizarPreferencias(List<String> novasPreferencias) {

        this.manipuladorPreferencias.gravarNovasPreferencias(novasPreferencias);

    }

    @Override
    protected void onDestroy() {

        stopReceivers();
        stopServices();

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        auth.signOut();

        super.onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Evento item = (Evento) parent.getItemAtPosition(position);

        Intent intent2 = new Intent(this,EventoActivity.class);
        intent2.putExtra("EVENTO",item);

        startActivity(intent2);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        Evento item = (Evento) parent.getItemAtPosition(position);

        ExcluirEventoDialogFragment.show(getSupportFragmentManager(),item,this);

        return false;
    }

    @Override
    public void onOptionSelected(boolean opcao,Evento e, Context c) {
        if(opcao){
            this.adapter.remove(e);
        }
    }

    public ManipuladorPreferencias getManipuladorPreferencias() {
        return manipuladorPreferencias;
    }

    @Override
    public void onUpdatePreferences(String[] preferencias, boolean[] valores) {

        this.manipuladorPreferencias.atualizarPreferencias(preferencias,valores);

        JSONObject json = manipuladorPreferencias.gerarJSONObject();

        List<Evento> listaEventos = new ArrayList<Evento>();

        for(int i=0;i<this.backup.size();i++) {

            Evento e = this.backup.get(i);

            try {
                if (json.getBoolean(e.getTipo()) == true) {
                    listaEventos.add(e);
                }
            } catch (JSONException e1) {
                Log.e("MYAPP","Erro na obtenção do tipo de evento " + e.getTipo(), e1);
            }
        }

        this.adapter.updateAll(listaEventos);

    }
}
