package com.example.neto.projetofinal.activitys.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.neto.projetofinal.activitys.MainActivity;
import com.example.neto.activitys.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends Activity {

    EditText editTextEmail, editTextSenha;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.editTextEmail =  findViewById(R.id.editText_Email);
        this.editTextSenha = findViewById(R.id.editText_Senha);
        this.auth = FirebaseAuth.getInstance();
    }

    public void login(View view) {

        final String email = this.editTextEmail.getText().toString();
        final String senha = this.editTextSenha.getText().toString();

        auth.signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    editTextSenha.setText("");

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("EMAIL",email);
                    startActivity(intent);
                }else{
                    Log.e("MyApp", "onComplete: Failed=" + task.getException().getMessage());

                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
