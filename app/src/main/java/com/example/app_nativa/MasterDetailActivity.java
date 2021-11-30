package com.example.app_nativa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_nativa.placeholder.PlaceholderContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MasterDetailActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private AdapterMasterDetail mAdapter;
    private RequestQueue requestQueue;
    ArrayList<PlaceholderContent.PlaceholderItem> listaNoticias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        setTitle("Explorar");
        //requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();


        listaNoticias = new ArrayList<>();
        try {
            getNoticias(); //llenar array con noticias
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mAdapter = new AdapterMasterDetail(listaNoticias);

        mAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                i.putExtra("url", listaNoticias.get(mRecyclerView.getChildAdapterPosition(v)).getUrl());
                startActivity(i);
            }
        });


        mRecyclerView.setAdapter(mAdapter);

    }


    public void getNoticias() throws JSONException {
        /*
        //String url_api = "http://api.mediastack.com/v1/news?access_key=d67e5f39b3825efab82f83e260ae52ca&languages=es&countries=ar";
        String url_api = "https://node-mysql3.herokuapp.com/api/projects";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url_api, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++)
                {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String title = jsonObject.getString("title");
                        String description = jsonObject.getString("description");
                        String image = jsonObject.getString("image");
                        String url = jsonObject.getString("url");
                        String author = jsonObject.getString("author");
                        String country = jsonObject.getString("country");
                        String language = jsonObject.getString("language");
                        String source = jsonObject.getString("source");
                        String category = jsonObject.getString("category");
                        String published_at = jsonObject.getString("published_at");

                        PlaceholderContent.PlaceholderItem item = new PlaceholderContent.PlaceholderItem(title,author,description,image,country,url,language,source,category,published_at);
                        listaNoticias.add(item);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //mAdapter = new AdapterMasterDetail(listaNoticias);
                    //mRecyclerView.setAdapter(mAdapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MasterDetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(" de la api paaaa:", "onErrorResponse: "+error.getMessage());
            }
        });

        requestQueue.add(jsonArrayRequest);
        */
        /*
//metodo 2
        RequestQueue queue = Volley.newRequestQueue(this);
        String url_api = "https://node-mysql3.herokuapp.com/api/projects";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_api,
                response -> {
                    // Display the first 500 characters of the response string.
                    //textView.setText("Response is: "+ response.substring(0,500));
                    Toast.makeText(getApplicationContext(), "Hola", Toast.LENGTH_SHORT).show();
                }, error -> {
                    //textView.setText("That didn't work!");
                    Toast.makeText(getApplicationContext(), "err", Toast.LENGTH_SHORT).show();
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
*/

        JSONArray arrayJson= new JSONArray();

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

        JSONObject objetoJson3 = new JSONObject();
        objetoJson3.put("title", "Ingresó a una casa y manoseó a una joven");
        objetoJson3.put("author", "Redacción LA");
        objetoJson3.put("description", "\"Un hombre de 55 años denunció que un joven ingresó de forma subrepticia a su vivienda, manoseó a su hija adolescente. El sospechoso tiene 25 años y fue retenido por vecinos cuando quiso escapar.La entrada Ingresó a una casa y manoseó a una joven se publicó primero en Primera Edición.");
        objetoJson3.put("image", "https://www.lavoz.com.ar/resizer/bg8i6cXExKS0a8PgAiTpRSF0gBY=/cloudfront-us-east-1.images.arcpublishing.com/grupoclarin/VIANZC57W5B7PDO7ABXTMDXBXY.jpg");
        objetoJson3.put("country", "ar");
        objetoJson3.put("category", "general");
        objetoJson3.put("language", "es");
        objetoJson3.put("published_at", "2021-08-01T01:16:09+00:00");
        objetoJson3.put("source", "losandes");
        objetoJson3.put("url", "https://www.losandes.com.ar/politica/alberto-fernandez-propuso-discutir-la-duracion-de-los-jueces-en-sus-cargos");

        //guardo en arreglo los elementos
        arrayJson.put(objetoJson1);
        arrayJson.put(objetoJson2);
        arrayJson.put(objetoJson3);

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

            PlaceholderContent.PlaceholderItem item = new PlaceholderContent.PlaceholderItem(title,author,description,image,country,url,language,source,category,published_at);

            listaNoticias.add(item);
        }



    }

    @Override
    int getContentViewId() {
        return R.layout.activity_master_detail;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.MasterDetailActivity;
    }
}