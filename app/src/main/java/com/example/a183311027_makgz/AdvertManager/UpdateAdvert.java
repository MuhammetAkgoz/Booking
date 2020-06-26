package com.example.a183311027_makgz.AdvertManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.a183311027_makgz.DataBaseManager.DataBase;
import com.example.a183311027_makgz.MainManager.ListActivty;
import com.example.a183311027_makgz.R;

import java.io.ByteArrayOutputStream;

public class UpdateAdvert extends AppCompatActivity {
    Bitmap selectedImage;
    ImageView yeniResim;
    EditText etYeniBaslik, etYeniDaireOda, etYeniAdres, etYeniUcret;
    Spinner sehir, ilce, kat, oda;
    Button btnGuncelle;
    int ID;
    private static DataBase myDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulk_guncele);

        yeniResim = findViewById(R.id.imgYeniResim);
        etYeniBaslik = findViewById(R.id.etYeniBaslik);
        etYeniDaireOda = findViewById(R.id.etYeniDaireOda);
        etYeniAdres = findViewById(R.id.etYeniAdres);
        etYeniUcret = findViewById(R.id.etYeniUcret);
        sehir = findViewById(R.id.spnYeniSehir);
        ilce = findViewById(R.id.spnYeniİlce);
        kat = findViewById(R.id.spnYeniKat);
        oda = findViewById(R.id.spnYeniOdaSayisi);
        btnGuncelle = findViewById(R.id.btnGuncelle);

        myDataBase = new DataBase(this);

        //spinnerları dolduralım
        spinnerDoldur(getResources().getStringArray(R.array.sehirler), sehir);
        spinnerDoldur(getResources().getStringArray(R.array.sayilar), kat);
        spinnerDoldur(getResources().getStringArray(R.array.sayilar), oda);

        ID = getIntent().getIntExtra("ID", 0);

    }

    @Override
    protected void onResume() {

            sehir.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position == 0) {
                        spinnerDoldur(getResources().getStringArray(R.array.Ankİlceler), ilce);
                    }
                    if (position == 1) {
                        spinnerDoldur(getResources().getStringArray(R.array.İstİlceler), ilce);
                    }
                    if (position == 2) {
                        spinnerDoldur(getResources().getStringArray(R.array.Konİlceler), ilce);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        Cursor cursor = myDataBase.getData("SELECT * FROM mulkbilgileri WHERE id=" + ID);

        while (cursor.moveToNext()) {
            String baslik = cursor.getString(1);
            etYeniBaslik.setText(baslik);
            String mulkbilgi = cursor.getString(2);
            etYeniDaireOda.setText(mulkbilgi);
            String adres = cursor.getString(3);
            etYeniAdres.setText(adres);
            String ucret = cursor.getString(4);
            etYeniUcret.setText(ucret);
            byte[] newImage = cursor.getBlob(9);
            yeniResim.setImageBitmap(BitmapFactory.decodeByteArray(newImage, 0, newImage.length));

        }


        btnGuncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Bitmap kucukResim = compressImage(selectedImage, 100);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    kucukResim.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
                    final byte[] image = byteArrayOutputStream.toByteArray();

                    UpdateAdvert.myDataBase.updateData(etYeniBaslik.getText().toString().trim(),
                            etYeniDaireOda.getText().toString().trim(),
                            etYeniAdres.getText().toString().trim(),
                            etYeniUcret.getText().toString().trim(),
                            sehir.getSelectedItem().toString().trim(),
                            ilce.getSelectedItem().toString().trim(),
                            kat.getSelectedItem().toString().trim(),
                            oda.getSelectedItem().toString().trim(),
                            image, ID);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent myIntent = new Intent(UpdateAdvert.this, ListActivty.class);
                startActivity(myIntent);
                finish();
            }
        });



        super.onResume();
    }

    private void spinnerDoldur(String[] List, Spinner spn) {
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, List);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(myAdapter);
    }

    public void selectedImage(View view) {
        //izin verilmediyse
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }//izin verilmişse
        else {
            //kayıtlı olan dosyaya git
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery, 2);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //izin verildiyse
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery, 2);
            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            Uri imageData = data.getData();
            try {
                if (Build.VERSION.SDK_INT >= 28) {
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), imageData);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    yeniResim.setImageBitmap(selectedImage);
                } else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageData);
                    yeniResim.setImageBitmap(selectedImage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public Bitmap compressImage(Bitmap image, int maxsize) {

        int width = image.getWidth();
        int height = image.getHeight();
        //yatay dikey kontrolü
        float newSize = (float) width / (float) height;
        //resim yatay
        if (newSize > 1) {
            width = maxsize;
            height = (int) (width / newSize);
        }//resim dikey
        else {
            height = maxsize;
            width = (int) (height * newSize);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

}