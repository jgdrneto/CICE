package com.example.neto.projetofinal.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.neto.activitys.R;
import com.example.neto.projetofinal.MapsActivity;
import com.example.neto.projetofinal.bancodedados.evento.Evento;

import java.text.SimpleDateFormat;

public class EventoActivity extends AppCompatActivity {

    Evento evento;

    TextView textView_dataInicio_valor;
    TextView textView_dataFinal_valor;
    TextView textView_local_valor;
    TextView textView_complemento_valor;
    TextView textView_tipo_valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent i = getIntent();

        this.evento = (Evento) i.getSerializableExtra("EVENTO");

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        setTitle(evento.getNome().toUpperCase());

        this.textView_dataInicio_valor = findViewById(R.id.textView_dataInicial_valor);
        this.textView_dataFinal_valor = findViewById(R.id.textView_dataFinal_valor);
        this.textView_complemento_valor =  findViewById(R.id.textView_complemento_valor);
        this.textView_tipo_valor =  findViewById(R.id.textView_tipo_valor);
        this.textView_local_valor =  findViewById(R.id.textView_local_valor);

        this.textView_dataInicio_valor.setText(format.format(this.evento.getDataInicio()));
        this.textView_dataFinal_valor.setText(format.format(this.evento.getDataFinal()));
        this.textView_local_valor.setText(this.evento.getNomeLocal());
        this.textView_tipo_valor.setText(this.evento.getTipo().toUpperCase());
        this.textView_complemento_valor.setText(this.evento.getComplemento());

    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

    public void verNoMaps(View view) {

        Intent i = new Intent(getApplicationContext(),MapsActivity.class);
        i.putExtra("EVENTO", this.evento);
        startActivity(i);

        //Toast.makeText(this,"Ainda não implementado",Toast.LENGTH_SHORT);

    }
}
