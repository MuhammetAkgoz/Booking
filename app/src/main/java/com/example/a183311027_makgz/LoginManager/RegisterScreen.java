package com.example.a183311027_makgz.LoginManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a183311027_makgz.R;

public class RegisterScreen extends AppCompatActivity {

    EditText etAdSoyad;
    EditText etEmail;
    EditText etSifre;
    EditText etSifreTekrar;
    EditText etCepTelefonu;
    Button btnKayitOl;
    SharedPreferences kayitSharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ekrani);

        etAdSoyad=findViewById(R.id.etAdSoyad);
        etEmail=findViewById(R.id.etEmail);
        etSifre=findViewById(R.id.etSifre);
        etSifreTekrar=findViewById(R.id.etSifreTekrar);
        etCepTelefonu=findViewById(R.id.etCepTel);
        btnKayitOl=findViewById(R.id.btnKayıtOl);

        kayitSharedPreferences=getSharedPreferences("KayıtBilgileri",MODE_PRIVATE);
        editor=kayitSharedPreferences.edit();

        btnKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(etEmail.getText().toString().isEmpty()) && !(etSifre.getText().toString().isEmpty()) ){
                    editor.putString("AdSoyad",etAdSoyad.getText().toString());
                    editor.putString("Email",etEmail.getText().toString());
                    editor.putString("Sifre",etSifre.getText().toString());
                    editor.putString("SifreTekrar",etSifreTekrar.getText().toString());
                    editor.putString("CepTelefonu",etCepTelefonu.getText().toString());
                    editor.apply();

                    changeScreen();
                    finish();

                }else{
                    Toast.makeText(RegisterScreen.this,"Email veya Şifre Boş Olamaz",Toast.LENGTH_SHORT).show();

                }
            }
        });
      }

      public void changeScreen(){

        Intent myIntent=new Intent(RegisterScreen.this, LoginScreen.class);
        startActivity(myIntent);
        finish();

      }

}