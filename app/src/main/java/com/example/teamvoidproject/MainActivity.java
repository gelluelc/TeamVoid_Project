package com.example.teamvoidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialization();
        listeners();
    }

    public void initialization(){
        btn_register = (Button) findViewById(R.id.id_RegisterButton);
    }

    public void listeners(){
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_RegisterButton:
                startActivity(new Intent(this,register.class));
                break;
        }
    }
}