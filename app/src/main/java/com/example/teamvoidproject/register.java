
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
import com.google.firebase.database.FirebaseDatabase;



public class register extends AppCompatActivity implements View.OnClickListener {
    private EditText et_username, et_password, et_conpass, et_email, et_fullname;
    private Button btn_reg;
    private ProgressBar progressreg;

=======

public class register extends AppCompatActivity implements View.OnClickListener {
    private EditText et_username, et_password, et_conpass, et_email, et_fullname;
    private Button btn_reg;
    private ProgressBar progressreg;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialization();
        listeners();
    }

    public void initialization (){
        //EditText
        et_username = findViewById(R.id.edt_username);
        et_password = findViewById(R.id.edt_password);
        et_conpass = findViewById(R.id.edt_confirmpass);
        et_email = findViewById(R.id.edt_email);
        et_fullname = findViewById(R.id.edt_fullname);

        //Button
        btn_reg = findViewById(R.id.id_Registerbtn);

        progressreg = findViewById(R.id.progressBarRegis);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.id_Registerbtn:
                registerUser();
                break;
        }
=======
            switch (v.getId()){
                case R.id.id_Registerbtn:
                    registerUser();
                    break;
            }

    }

    public void listeners(){

        btn_reg.setOnClickListener(this);
    }

    public void registerUser(){
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String conpass = et_conpass.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String fullname = et_fullname.getText().toString().trim();

        if (username.isEmpty()){
            et_username.setError("Username is Empty");
            et_username.requestFocus();
            return;
        }

        if (password.isEmpty()){
            et_password.setError("Password is Empty");
            et_password.requestFocus();
            return;
        }


        if (password.length() < 6){
            et_password.setError("Password should be in 4 characters Above!");
=======
        if (password.length() < 4){
            et_password.setError("Password should be in 4 characters!");

            et_password.requestFocus();
            return;
        }

        if (conpass.isEmpty()){
            et_conpass.setError("Confirm Password is Empty");
            et_conpass.requestFocus();
            return;
        }

//        if (password != conpass){
//            Toast.makeText(this, "Password Does not Match", Toast.LENGTH_SHORT).show();
//            et_conpass.requestFocus();
//            return;
//        }

        if (email.isEmpty()){
            et_email.setError("Email is Empty");
            et_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Please use a valid Email!");
            et_email.requestFocus();
            return;
        }

        if (fullname.isEmpty()){
            et_fullname.setError("Full Name is Empty");
            et_fullname.requestFocus();
            return;
        }

        progressreg.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password)

                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(register.this, "nice", Toast.LENGTH_SHORT).show();
                            User user = new User(fullname, email, username, password);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(register.this, "User Registered", Toast.LENGTH_LONG).show();
                                        progressreg.setVisibility(View.GONE);
                                        Intent intent = new Intent(register.this, MainActivity.class);
                                        startActivity(intent);

                                    }
                                    else {
                                        Toast.makeText(register.this, "Failed to Register!", Toast.LENGTH_SHORT).show();
                                        progressreg.setVisibility(View.GONE);
                                    }

                                }
                            });
                        }else{
                            Toast.makeText(register.this, "Failed to Register. Try Again!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressreg.setVisibility(View.GONE);
                        }

                    }
                });
=======
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(register.this, "nice", Toast.LENGTH_SHORT).show();
                        User user = new User(fullname, email, username, password);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(register.this, "User Registered", Toast.LENGTH_LONG).show();
                                    progressreg.setVisibility(View.GONE);
                                    Intent intent = new Intent(register.this, MainActivity.class);
                                    startActivity(intent);

                                }
                                else {
                                    Toast.makeText(register.this, "Failed to Register!", Toast.LENGTH_SHORT).show();
                                    progressreg.setVisibility(View.GONE);
                                }

                            }
                        });
                    }else{
                        Toast.makeText(register.this, "Failed to Register. Try Again!", Toast.LENGTH_SHORT).show();
                        progressreg.setVisibility(View.GONE);
                    }

                }
            });


    }
}