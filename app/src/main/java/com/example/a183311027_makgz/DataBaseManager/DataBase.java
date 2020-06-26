package com.example.a183311027_makgz.DataBaseManager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DataBase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Adverts";
    private static final int VERSION = 1;


    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS mulkbilgileri (id INTEGER PRIMARY KEY AUTOINCREMENT,baslik VARCHAR," +
                "mulktipi VARCHAR,adres VARCHAR,ucret VARCHAR,sehir VARCHAR,ilce VARCHAR,kat VARCHAR,odasayisi VARCHAR,resim BOMB)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(String baslik, String daireOda, String adres, String ucret, String sehir, String ilce, String kat, String odasayisi, byte[] image) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO mulkbilgileri VALUES(NULL,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, baslik);
        statement.bindString(2, daireOda);
        statement.bindString(3, adres);
        statement.bindString(4, ucret);
        statement.bindString(5, sehir);
        statement.bindString(6, ilce);
        statement.bindString(7, kat);
        statement.bindString(8, odasayisi);
        statement.bindBlob(9, image);

        statement.executeInsert();

    }

    public void updateData(String baslik, String daireOda, String adres, String ucret, String sehir, String ilce, String kat, String odasayisi, byte[] image, int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE mulkbilgileri SET baslik=?, mulktipi=?, adres=?, ucret=?, sehir=?, ilce=?, kat=?, odasayisi=?, resim=? WHERE id=?";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.bindString(1, baslik);
        statement.bindString(2, daireOda);
        statement.bindString(3, adres);
        statement.bindString(4, ucret);
        statement.bindString(5, sehir);
        statement.bindString(6, ilce);
        statement.bindString(7, kat);
        statement.bindString(8, odasayisi);
        statement.bindBlob(9, image);
        statement.bindDouble(10, (double) id);

        statement.execute();
        database.close();
    }

    public void deleteData(int id) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "DELETE FROM mulkbilgileri WHERE id=?";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();
        statement.bindDouble(1, (double) id);

        statement.execute();
        database.close();
    }

    public Cursor getData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);

    }
}
