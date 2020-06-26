package com.example.a183311027_makgz.LoginManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a183311027_makgz.MainManager.ListActivty;
import com.example.a183311027_makgz.R;
import com.example.a183311027_makgz.UserManager.Users;

public class LoginScreen extends AppCompatActivity {

    SharedPreferences girisSharedPreferences;
    SharedPreferences.Editor girisEditor;
    EditText etMail;
    EditText etGirisSifre;
    Button btnGiris,btnKayitOl;
    Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_ekrani);

        etMail=findViewById(R.id.etMail);
        etGirisSifre=findViewById(R.id.etGirişSifre);
        btnGiris=findViewById(R.id.btnGiris);
        btnKayitOl=findViewById(R.id.btnKayitOl);



        girisSharedPreferences=getSharedPreferences("KayıtBilgileri",MODE_PRIVATE);
        girisEditor=girisSharedPreferences.edit();

        final String eMail=girisSharedPreferences.getString("Email","");
        final String sifre=girisSharedPreferences.getString("Sifre","");


        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!(etMail.getText().toString().isEmpty()) && !(etGirisSifre.getText().toString().isEmpty())){
                   if (etMail.getText().toString().equals(eMail) && etGirisSifre.getText().toString().equals(sifre)){
                       girisEditor.putBoolean("IS_LOGIN",true);
                       girisEditor.apply();
                       changeScreen(LoginScreen.this, ListActivty.class);
                       finish();
                   }else{
                       Toast.makeText(LoginScreen.this,"Şifre veya Kulanıcı Adı Yanlış",Toast.LENGTH_LONG).show();
                   }
               }else{

                   Toast.makeText(LoginScreen.this,"Boş Alanları Doldurunuz",Toast.LENGTH_LONG).show();
               }

            }
        });

        btnKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeScreen(LoginScreen.this, RegisterScreen.class);
                finish();

            }
        });
        isLogin();

    }



    public void changeScreen(Context context, Class<?> where){

        Intent myIntent=new Intent(context,where);
        startActivity(myIntent);
        finish();
    }

    public void isLogin(){
        Boolean isLogin=girisSharedPreferences.getBoolean("IS_LOGIN",false);
        if (isLogin==true){
           Intent myIntent=new Intent(LoginScreen.this, ListActivty.class);
           myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(myIntent);
        }

    }


}