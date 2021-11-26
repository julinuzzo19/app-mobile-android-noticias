package com.example.app_nativa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SubActivity extends AppCompatActivity {
    ConstraintLayout main_layout;
    int back_op=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent received_intent=getIntent();

        main_layout =findViewById(R.id.activity_sub_layout);
        Button op1=findViewById(R.id.option1);
        Button op2=findViewById(R.id.option2);
        Button op3=findViewById(R.id.option3);

        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackground(1);
            }
        });

        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackground(2);
            }
        });

        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackground(3);
            }
        });

    }
    void setBackground(int op){
        switch(op) {
            case 1:
                main_layout.setBackgroundResource(R.drawable.red_background);
                back_op=1;
                break;
            case 2:
                main_layout.setBackgroundResource(R.drawable.blue_background);
                back_op=2;
                break;
            case 3:
                main_layout.setBackgroundResource(R.drawable.green_backgound);
                back_op=3;
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("return_int",back_op);
        setResult(RESULT_OK,intent);
        super.onBackPressed();
    }
}