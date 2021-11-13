package com.example.app_nativa;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_nativa.databinding.ActivityItemDetailBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemDetailHostActivity extends AppCompatActivity {

    private RequestQueue queue;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityItemDetailBinding binding = ActivityItemDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_item_detail);
        NavController navController = navHostFragment.getNavController();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.
                Builder(navController.getGraph())
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        queue = Volley.newRequestQueue(this);
            getNews();



    }

    private void getNews() {

try {
    context=getApplicationContext();
    JSONObject objetoJson1 = new JSONObject();
    objetoJson1.put("title", "Ingresó a una casa y manoseó a una joven");
    objetoJson1.put("author", "Redacción LA");
    objetoJson1.put("description", "\"Un hombre de 55 años denunció que un joven ingresó de forma subrepticia a su vivienda, manoseó a su hija adolescente. El sospechoso tiene 25 años y fue retenido por vecinos cuando quiso escapar.La entrada Ingresó a una casa y manoseó a una joven se publicó primero en Primera Edición.");
    objetoJson1.put("image", "https://www.lavoz.com.ar/resizer/bg8i6cXExKS0a8PgAiTpRSF0gBY=/cloudfront-us-east-1.images.arcpublishing.com/grupoclarin/VIANZC57W5B7PDO7ABXTMDXBXY.jpg");
    objetoJson1.put("country", "ar");


    JSONObject objetoJson2= new JSONObject();
    objetoJson2.put("title", "Lo que dejó la natación: las consagraciones de Dressel y McKeon");
    objetoJson2.put("author", "Redacción LA");
    objetoJson2.put("description", "EE.UU. dominó entre los hombres y Australia entre las mujeres");
    objetoJson2.put("image", "https://images.pagina12.com.ar/styles/width960/public/2021-08/182414-000-9hd8y4.jpg?itok=jbcngzNx");
    objetoJson2.put("country", "ar");

    JSONArray arrayJson= new JSONArray();
    arrayJson.put(objetoJson1);
    arrayJson.put(objetoJson2);


    for (int i=0; i<arrayJson.length();i++)
    {
        JSONObject mJsonObject =  arrayJson.getJSONObject(i);
        String title = mJsonObject.getString("title");

        //Muestra informacion por pantalla
        Toast.makeText(context,"Titulo: "+title, Toast.LENGTH_SHORT).show();

    }


}catch (JSONException e) {
    e.printStackTrace();
}


        //queue.add(request);

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_item_detail);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}