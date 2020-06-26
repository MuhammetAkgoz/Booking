package com.example.a183311027_makgz.UserManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a183311027_makgz.AdvertManager.AddAdvert;
import com.example.a183311027_makgz.LoginManager.LoginScreen;
import com.example.a183311027_makgz.R;

public class UserProfile extends AppCompatActivity {
    EditText etProfileAdSoyad,
             etProfileEmail,
             etProfileSifre,
             etProfileTel;
    Button btnGuncelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        etProfileAdSoyad=findViewById(R.id.etProfileAdSoyad);
        etProfileEmail=findViewById(R.id.etProfileEmail);
        etProfileSifre=findViewById(R.id.etProfileSifre);
        etProfileTel=findViewById(R.id.etProfileTel);
        btnGuncelle=findViewById(R.id.btnGuncelle);

        showData();



        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateData();
                Toast.makeText(UserProfile.this,"Güncelleme İşlemi Başarılı",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void showData() {
        Users user=(Users)getIntent().getParcelableExtra("user");
        if (user != null) {
            etProfileAdSoyad.setText(user.getUsername());
            etProfileEmail.setText(user.getUsermail());
            etProfileSifre.setText(user.getUserpassword());
            etProfileTel.setText(user.getUsernumber());
        }
    }

    private void updateData() {
        SharedPreferences updateSharePreferences=getSharedPreferences("KayıtBilgileri",MODE_PRIVATE);
        SharedPreferences.Editor updateEditor =updateSharePreferences.edit();
        updateEditor.putString("AdSoyad",etProfileAdSoyad.getText().toString());
        updateEditor.putString("Email",etProfileEmail.getText().toString());
        updateEditor.putString("Sifre",etProfileSifre.getText().toString());
        updateEditor.putString("CepTelefonu",etProfileTel.getText().toString());
        updateEditor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflater
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.profile_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    public void Logout(MenuItem item) {

        final AlertDialog.Builder check=new AlertDialog.Builder(this);
        check.setTitle("Çıkış");
        check.setMessage("Çıkmak istediğinizden emin misiniz?");
        check.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences logoutSharePreferences=getSharedPreferences("KayıtBilgileri",MODE_PRIVATE);
                SharedPreferences.Editor logoutEditor =logoutSharePreferences.edit();
                //kullanıcıyı sisteme bir daha almasın diye
                logoutEditor.putBoolean("IS_LOGIN",false);
                logoutEditor.commit();

                Intent myIntent=new Intent(getApplicationContext(), LoginScreen.class);
                startActivity(myIntent);
                finish();
            }
        });
        check.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
            }
        });
        check.show();



    }
}