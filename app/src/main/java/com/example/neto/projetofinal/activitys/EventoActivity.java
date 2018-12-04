package com.example.neto.projetofinal.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.neto.activitys.R;
import com.example.neto.projetofinal.bancodedados.evento.Evento;

import java.text.SimpleDateFormat;

public class EventoActivity extends AppCompatActivity {

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

        Evento e = (Evento) i.getSerializableExtra("EVENTO");

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        setTitle(e.getNome().toUpperCase());

        this.textView_dataInicio_valor = findViewById(R.id.textView_dataInicial_valor);
        this.textView_dataFinal_valor = findViewById(R.id.textView_dataFinal_valor);
        this.textView_complemento_valor =  findViewById(R.id.textView_complemento_valor);
        this.textView_tipo_valor =  findViewById(R.id.textView_tipo_valor);
        this.textView_local_valor =  findViewById(R.id.textView_local_valor);

        this.textView_dataInicio_valor.setText(format.format(e.getDataInicio()));
        this.textView_dataFinal_valor.setText(format.format(e.getDataFinal()));
        this.textView_local_valor.setText(e.getNomeLocal());
        this.textView_tipo_valor.setText(e.getTipo().toUpperCase());
        this.textView_complemento_valor.setText(e.getComplemento());

    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

    public void verNoMaps(View view) {

        Toast.makeText(this,"Ainda não implementado",Toast.LENGTH_SHORT);

    }
}
