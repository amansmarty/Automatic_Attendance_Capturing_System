package com.example.automaticattendancecapturingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class signup extends AppCompatActivity {
TextView tv_login;
Button btn_singups;
EditText email,password,c_pass;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        tv_login=findViewById(R.id.tv_log);
        btn_singups=findViewById(R.id.btn_signups);
        email=findViewById(R.id.et_email);
        password=findViewById(R.id.et_pass);
        c_pass=findViewById(R.id.et_pass1);
        getSupportActionBar().hide();
        mAuth=FirebaseAuth.getInstance();


        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        btn_singups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String Pass = password.getText().toString().trim();
                String C_Pass = c_pass.getText().toString().trim();
                if(TextUtils.isEmpty(Email)){
                    email.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(Pass)){
                    password.setError("Password is Required");
                    return;
                }
                if(password.length()<6){
                    password.setError("Password must be >= 6 Characters");
                    return;
                }
                if(Pass.equals(C_Pass)){
                }
                else{
                    c_pass.setError("Password don't matched");
                    return;
                }
                mAuth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(signup.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(signup.this,"Error ! "+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });






        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this,login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}