package com.example.app_nativa.placeholder;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class PlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();
    public static Map<String, PlaceholderItem> ITEM_MAP =
            new HashMap<String, PlaceholderItem>();


    static {
        getNews();
    }

    private static void addItem(PlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);

    }



    public static class PlaceholderItem {
        public final String id;
        public final String title;
        public final String author;
        public final String description;
        public final String image;
        public final String country;
        public final String url;
        public final String language;
        public final String source;
        public final String category;
        public final String published_at;


        public PlaceholderItem(String title,String author,String description,String image,String country,String url, String language, String source,String category,String published_at) {
            this.id = UUID.randomUUID().toString();
            this.title = title;
            this.author = author;
            this.description =description ;
            this.image = image;
            this.country = country;
            this.url =url ;
            this.language = language;
            this.source= source;
            this.category= category;
            this.published_at=published_at;

        }


        public String getDescription() {
            return description;
        }

        public String getTitle() {
            return title;
        }

        public String getImage() {
            return image;
        }

        public String getId() {
            return id;
        }

        public String getUrl() {
            return url;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    public static void getNews() {

        JSONArray arrayJson= new JSONArray();

        try {
            JSONObject objetoJson1 = new JSONObject();
            objetoJson1.put("title", "Ingresó a una casa y manoseó a una joven");
            objetoJson1.put("author", "Redacción LA");
            objetoJson1.put("description", "\"Un hombre de 55 años denunció que un joven ingresó de forma subrepticia a su vivienda, manoseó a su hija adolescente. El sospechoso tiene 25 años y fue retenido por vecinos cuando quiso escapar.La entrada Ingresó a una casa y manoseó a una joven se publicó primero en Primera Edición.");
            objetoJson1.put("image", "https://www.lavoz.com.ar/resizer/bg8i6cXExKS0a8PgAiTpRSF0gBY=/cloudfront-us-east-1.images.arcpublishing.com/grupoclarin/VIANZC57W5B7PDO7ABXTMDXBXY.jpg");
            objetoJson1.put("country", "ar");
            objetoJson1.put("category", "general");
            objetoJson1.put("language", "es");
            objetoJson1.put("published_at", "2021-08-01T01:16:09+00:00");
            objetoJson1.put("source", "losandes");
            objetoJson1.put("url", "https://www.losandes.com.ar/politica/alberto-fernandez-propuso-discutir-la-duracion-de-los-jueces-en-sus-cargos");

            JSONObject objetoJson2= new JSONObject();
            objetoJson2.put("title", "Lo que dejó la natación: las consagraciones de Dressel y McKeon");
            objetoJson2.put("author", "Redacción LA");
            objetoJson2.put("description", "EE.UU. dominó entre los hombres y Australia entre las mujeres");
            objetoJson2.put("image", "https://images.pagina12.com.ar/styles/width960/public/2021-08/182414-000-9hd8y4.jpg?itok=jbcngzNx");
            objetoJson2.put("country", "ar");
            objetoJson2.put("category", "general");
            objetoJson2.put("language", "es");
            objetoJson2.put("published_at", "2021-09-01T01:16:09+00:00");
            objetoJson2.put("source", "cronica");
            objetoJson2.put("url", "https://www.losandes.com.ar/politica/alberto-fernandez-propuso-discutir-la-duracion-de-los-jueces-en-sus-cargos");

            //guardo en arreglo los elementos
            arrayJson.put(objetoJson1);
            arrayJson.put(objetoJson2);

            for (int i=0; i<=arrayJson.length();i++)
            {
                JSONObject mJsonObject =  arrayJson.getJSONObject(i);
                String title = mJsonObject.getString("title");
                String author = mJsonObject.getString("author");
                String description = mJsonObject.getString("description");
                String image = mJsonObject.getString("image");
                String country = mJsonObject.getString("country");
                String category = mJsonObject.getString("category");
                String language = mJsonObject.getString("language");
                String published_at = mJsonObject.getString("published_at");
                String source = mJsonObject.getString("source");
                String url = mJsonObject.getString("url");

                PlaceholderItem item = new PlaceholderItem(title,author,description,image,country,url,language,source,category,published_at);

                addItem(item);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

    }
}