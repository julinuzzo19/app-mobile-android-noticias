package com.example.app_nativa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.ViewDebug;

import com.example.app_nativa.placeholder.PlaceholderContent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Login.db";
    SQLiteDatabase MyDB = this.getWritableDatabase();
    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table users(email TEXT primary key, password TEXT)");
        db.execSQL("create Table favourites(id TEXT primary key, title TEXT,image TEXT,description TEXT,url TEXT,author TEXT,source TEXT,published_At TEXT,category TEXT,country TEXT, language TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists users");
        db.execSQL("drop Table if exists favourites");
    }

    public Boolean insertData(String email, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkEmail(String email) {
        //SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ?", new String[]{email});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkEmailPassword(String email, String password){
        //SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ? and password = ?", new String[] {email,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean insertFavourite(PlaceholderContent.PlaceholderItem noticia)
    {
        Cursor cursor = MyDB.rawQuery("Select * from favourites where title = ?", new String[]{noticia.title});

          int count =  cursor.getCount();

          if (count == 0) {

              ContentValues contentValues = new ContentValues();
              contentValues.put("id", noticia.getId());
              contentValues.put("title", noticia.getTitle());
              contentValues.put("image", noticia.getImage());
              contentValues.put("description", noticia.getDescription());
              contentValues.put("url", noticia.getUrl());
              contentValues.put("author", noticia.getAuthor());
              contentValues.put("source", noticia.getSource());
              contentValues.put("published_at", noticia.getPublished_at());
              contentValues.put("category", noticia.getCategory());
              contentValues.put("country", noticia.getCountry());
              contentValues.put("language", noticia.getLanguage());


              long result = MyDB.insert("favourites", null, contentValues);
              if (result == -1) return false;
              else
                  return true;
          }
          else return false;
        }

    public Boolean removeFavourite (String itemId){

       long result = MyDB.delete("favourites","title=?",new String[]{itemId});

       if (result == 1) return true;
        return false;
    }

    public ArrayList<PlaceholderContent.PlaceholderItem> getFavourites(){
        ArrayList<PlaceholderContent.PlaceholderItem> listaNoticias=new ArrayList<PlaceholderContent.PlaceholderItem>();


        Cursor cursor = MyDB.rawQuery("Select * from favourites",null);

        while(cursor.moveToNext()) {
            String id = cursor.getString(0);
            String title = cursor.getString(1);
            String image = cursor.getString(2);
            String description = cursor.getString(3);
            String url = cursor.getString(4);
            String author = cursor.getString(5);
            String source = cursor.getString(6);
            String published_at = cursor.getString(7);
            String category = cursor.getString(8);
            String country = cursor.getString(9);
            String language = cursor.getString(10);

            PlaceholderContent.PlaceholderItem item = new PlaceholderContent.PlaceholderItem(title,author,description,image,country,url,language,source,category,published_at);
            listaNoticias.add(item);
        }

       return listaNoticias;

    }

}
