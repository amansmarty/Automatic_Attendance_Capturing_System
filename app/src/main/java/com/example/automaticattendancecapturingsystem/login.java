package com.example.automaticattendancecapturingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
TextView tv_signup;
Button btn_signin;
EditText email,password;
FirebaseAuth mAuth;
ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        tv_signup=findViewById(R.id.tv_register);
        btn_signin=findViewById(R.id.button);
        email=findViewById(R.id.etEmail);
        password=findViewById(R.id.eTPassword);
        mAuth=FirebaseAuth.getInstance();
        final FirebaseUser user =mAuth.getCurrentUser();
        progressBar=findViewById(R.id.bar);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Password = password.getText().toString().trim();
                if(TextUtils.isEmpty(Email)){
                    email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    password.setError("Password is Required");
                    return;
                }
                if(password.length()<6){
                    password.setError("Password must be >= 6 Characters");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                           if (user.isEmailVerified()) {
                               Toast.makeText(login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                               progressBar.setVisibility(View.INVISIBLE);
                           } else {
                               user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void unused) {
                                       Toast.makeText(login.this, "Please check your registered mail to verify it.", Toast.LENGTH_SHORT).show();
                                   }
                               });
                           }
                       }
                       else{
                           Toast.makeText(login.this,"error !"+task.getException(),Toast.LENGTH_SHORT).show();
                       }

                    }
                });
            }
        });
        if (user.isEmailVerified()) {
            startActivity(new Intent(getApplicationContext(), MapsActivity.class));
        }


        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, signup.class);
                startActivity(intent);
            }
        });
    }
}