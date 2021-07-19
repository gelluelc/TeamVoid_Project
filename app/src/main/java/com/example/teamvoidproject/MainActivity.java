package com.example.teamvoidproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_register, btn_login;

    private EditText et_email, et_password;
    private FirebaseAuth mAuth;

    private ProgressBar progresslog;
=======
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


        et_email = findViewById(R.id.edt_email);
        et_password = findViewById(R.id.edt_password);

        btn_register = findViewById(R.id.id_RegisterButton);
        btn_login = findViewById(R.id.id_loginButton);

        progresslog = findViewById(R.id.progressBarLogin);

        mAuth = FirebaseAuth.getInstance();
    }

    public void listeners(){

        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
=======
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

            case R.id.id_loginButton:
                validation();
                break;
        }
    }

    public void validation(){
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if (email.isEmpty()){
            et_email.setError("Username is Empty");
            et_email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            et_password.setError("Password is Empty");
            et_password.requestFocus();
            return;
        }

        if (password.length() < 6){
            et_password.setError("Password should be in 6 Characters Above!");
            et_password.requestFocus();
            return;
        }

        progresslog.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull  Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Weather.class);
                            startActivity(intent);
                            progresslog.setVisibility(View.GONE);

                        }
                        else {
                            progresslog.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Failed to Login" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
=======
        }

    }
}