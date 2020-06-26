package com.example.a183311027_makgz.MainManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.a183311027_makgz.AdvertManager.AdvertListAdapter;
import com.example.a183311027_makgz.AdvertManager.AddAdvert;
import com.example.a183311027_makgz.AdvertManager.UpdateAdvert;
import com.example.a183311027_makgz.AdvertManager.Adverts;
import com.example.a183311027_makgz.DataBaseManager.DataBase;
import com.example.a183311027_makgz.LoginManager.LoginScreen;
import com.example.a183311027_makgz.R;
import com.example.a183311027_makgz.UserManager.UserProfile;
import com.example.a183311027_makgz.UserManager.Users;

import java.util.ArrayList;

public class ListActivty extends AppCompatActivity{

    ArrayList<Adverts> myList;
    AdvertListAdapter myAdapter=null;
    byte[] imgByte;
    ListView listView;
    SearchView searchView;
    Boolean isNotClick=true;
    ImageView imageMulk;
    private static DataBase myDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDataBase=new DataBase(this);

        imageMulk=findViewById(R.id.imageMulk);
        listView=findViewById(R.id.listview);

        myList=new ArrayList<>();
        myAdapter=new AdvertListAdapter(this,R.layout.simple_line,myList);
        listView.setAdapter(myAdapter);

        getDataWithQuary();

    }

    public void getDataWithQuary(){

        Cursor cursor= ListActivty.myDataBase.getData("SELECT * FROM mulkbilgileri");
        myList.clear();
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String baslik=cursor.getString(1);
            String sehir=cursor.getString(5);
            String mulktipi=cursor.getString(2);
            String kat=cursor.getString(7);
            String ilce=cursor.getString(6);
            imgByte=cursor.getBlob(9);

            myList.add(new Adverts(id,baslik,mulktipi,sehir,ilce,kat,imgByte));
        }
        myAdapter.notifyDataSetChanged();
        if (myList.size()==0){

            Toast.makeText(ListActivty.this,"Kayıt Bulunamadı",Toast.LENGTH_SHORT).show();
        }

        deleteOrUpdate();
    }


    private void deleteOrUpdate() {

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final CharSequence[] choice={"Sil","Güncelle"};

                AlertDialog.Builder message=new AlertDialog.Builder(ListActivty.this);
                message.setTitle("Seçim Yap");
                message.setItems(choice, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //eğer silmek isteniyorsa
                       if (which==0){
                           Cursor cursor= ListActivty.myDataBase.getData("SELECT * FROM mulkbilgileri");
                           ArrayList<Integer> ID=new ArrayList<Integer>();
                           while (cursor.moveToNext()){
                               ID.add(cursor.getInt(0));
                           }

                               if (ID.get(position)!= null){
                                   showDialogDelete(ID.get(position));
                               }

                       }//eğer güncelenmek isteniyorsa
                       if(which==1){
                            Cursor cursor= ListActivty.myDataBase.getData("SELECT * FROM mulkbilgileri");
                            ArrayList<Integer> ID=new ArrayList<Integer>();
                            while (cursor.moveToNext()){
                                ID.add(cursor.getInt(0));
                            }

                            Intent myIntent=new Intent(ListActivty.this, UpdateAdvert.class);
                            myIntent.putExtra("ID",ID.get(position));
                            startActivity(myIntent);

                           updateListView();

                        }
                    }
                });
                message.show();
                return true;
            }
        });

    }

    private void showDialogDelete(final Integer IDposition) {
        System.out.println(IDposition);
        final AlertDialog.Builder deleteData=new AlertDialog.Builder(ListActivty.this);
        deleteData.setTitle("Dikkat !!");
        deleteData.setTitle("İlanı Silmek İstediğinizden Emin Misiniz?");
        deleteData.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    try {
                        if (isNotClick){
                            ListActivty.myDataBase.deleteData(IDposition);
                            Toast.makeText(ListActivty.this,"Silme İşlemi Başarılı",Toast.LENGTH_SHORT).show();
                        }else{
                            ListActivty.myDataBase.deleteData(IDposition);
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                            Toast.makeText(ListActivty.this,"Silme İşlemi Başarılı",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                updateListView();
            }
        });
        deleteData.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        deleteData.show();
    }


    private void updateListView() {

        myList.clear();
        Cursor cursor= ListActivty.myDataBase.getData("SELECT * FROM mulkbilgileri");
        while (cursor.moveToNext()){
            int id=cursor.getInt(0);
            String baslik=cursor.getString(1);
            String mulktipi=cursor.getString(2);
            String adres=cursor.getString(3);
            String ucret=cursor.getString(4);
            String sehir=cursor.getString(5);
            String ilce=cursor.getString(6);
            String kat=cursor.getString(7);
            String odasayisi=cursor.getString(8);
            byte[] image=cursor.getBlob(9);

            //ekranda 4 tane verigösterileceği için bunların gönderilmesi yeterli.
            myList.add(new Adverts(id,baslik,mulktipi,sehir,ilce,kat,image));
        }
        myAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Inflater
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);

        final MenuItem menuItem2=menu.findItem(R.id.search);
        searchView=(SearchView)menuItem2.getActionView();
        searchView.setQueryHint(Html.fromHtml("<font color = #696969>"
                + getResources().getString(R.string.search_bar_text) + "</font>"));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                isNotClick=false;
                newText=newText.toLowerCase();
                ArrayList<Adverts> newList=new ArrayList<>();
                for (Adverts adverts:myList){
                    String query=adverts.getCity().toLowerCase()+" "+adverts.getDistrict().toLowerCase()+" "+adverts.getAdvertsType().toLowerCase()+" "+adverts.getFloor();
                    if (query.contains(newText))
                        newList.add(adverts);
                }
                myAdapter.setFilter(newList);
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.ilan_ver){

            Intent intent=new Intent(ListActivty.this, AddAdvert.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    public void profile(MenuItem item) {

        if (item.getItemId()==R.id.user_profile){
            SharedPreferences sharedPreferences=getSharedPreferences("KayıtBilgileri",MODE_PRIVATE);

            Intent intent=new Intent(ListActivty.this, UserProfile.class);
            Users user=new Users();
            String adSoyad= sharedPreferences.getString("AdSoyad","");
            String eMail=sharedPreferences.getString("Email","");
            String sifre=sharedPreferences.getString("Sifre","");
            String tel= sharedPreferences.getString("CepTelefonu","");

            user.setUsername(adSoyad);
            user.setUsermail(eMail);
            user.setUsernumber(tel);
            user.setUserpassword(sifre);

            intent.putExtra("user",user);
            startActivity(intent);

        }
    }



}