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

public class FirstActivity extends AppCompatActivity {

    EditText email, password, repassword;
    Button signup, signin;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        isLogged();
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        signup = (Button) findViewById(R.id.btnsignup);
        signin = (Button) findViewById(R.id.btnsignin);
        DB = new DBHelper(this);

        signup.setOnClickListener(view -> {
            String emailAdress = email.getText().toString();
            String pass = password.getText().toString();
            String repass = repassword.getText().toString();

            if(!(isValidEmail(emailAdress))||pass.equals("")||repass.equals(""))
                Toast.makeText(FirstActivity.this, R.string.complete_fields, Toast.LENGTH_SHORT).show();
            else{
                if(pass.equals(repass)){
                    Boolean checkuser = DB.checkEmail(emailAdress);
                    if(checkuser==false){
                        Boolean insert = DB.insertData(emailAdress, pass);
                        if(insert==true){
                            Toast.makeText(FirstActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
                            savePrefs(emailAdress,pass);
                            Intent intent = new Intent(getApplicationContext(),MasterDetailActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(FirstActivity.this, R.string.register_failed, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(FirstActivity.this, R.string.user_already_exists, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(FirstActivity.this, R.string.password_incorrect, Toast.LENGTH_SHORT).show();
                }
            } });

        signin.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
    }
    private void isLogged()
    {
        SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);
        String password = prefs.getString("password", null);

        if (email != null && password != null){
            Intent intent  = new Intent(getApplicationContext(), MasterDetailActivity.class);
            startActivity(intent);
        }
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