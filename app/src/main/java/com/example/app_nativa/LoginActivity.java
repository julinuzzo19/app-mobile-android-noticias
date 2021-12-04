package com.example.app_nativa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button btnlogin;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email = findViewById(R.id.email1);
        password = findViewById(R.id.password1);
        btnlogin = findViewById(R.id.btnsignin1);
        DB = new DBHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailAdress = email.getText().toString();
                String pass = password.getText().toString();

                if(!(isValidEmail(emailAdress))||pass.equals(""))
                    Toast.makeText(LoginActivity.this, R.string.complete_fields, Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkUserPass = DB.checkEmailPassword(emailAdress, pass);
                    if(checkUserPass==true){
                        Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
                        savePrefs(emailAdress,pass);
                        Intent intent  = new Intent(getApplicationContext(), MasterDetailActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, R.string.invalid_credentials, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void savePrefs(String email,String password) {
        SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }
    public boolean isValidEmail(CharSequence email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

}