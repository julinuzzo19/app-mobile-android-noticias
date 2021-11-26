package com.example.app_nativa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {
    String id="";
    String pw="";
    ConstraintLayout main_layout;
    int op=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        main_layout=findViewById(R.id.activity_main_layout);
        final EditText ID=findViewById(R.id.Email);
        final EditText PW=findViewById(R.id.PW);
        final Button login=findViewById(R.id.login);
        final Button register=findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID_input=ID.getText().toString();
                String PW_input=PW.getText().toString();
                if(id.equals(ID_input)&&pw.equals(PW_input)){
                    Toast.makeText(getApplicationContext(),"Welcome!!",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this,SubActivity.class);
                    startActivityForResult(intent,101);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Wrong ID or password",Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id=ID.getText().toString();
                pw=PW.getText().toString();
                Toast.makeText(getApplicationContext(),"ID and password changed",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            op=data.getIntExtra("return_int",0);
            setBackground(op);
        }
    }

    void setBackground(int op){
        switch(op) {
            case 0:
                break;
            case 1:
                main_layout.setBackgroundResource(R.drawable.red_background);
                break;
            case 2:
                main_layout.setBackgroundResource(R.drawable.blue_background);
                break;
            case 3:
                main_layout.setBackgroundResource(R.drawable.green_backgound);
                break;
        }
    }
}