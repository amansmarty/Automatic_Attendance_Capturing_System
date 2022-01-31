package com.example.automaticattendancecapturingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class signup extends AppCompatActivity {
TextView tv_login;
Button btn_singups;
EditText email,password,c_pass;
FirebaseAuth mAuth;
ProgressBar progressBar;
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
        progressBar=findViewById(R.id.progressBar);

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
                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                           // dialog.show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            AlertDialog.Builder dialog= new AlertDialog.Builder(signup.this)
                                    .setIcon(R.drawable.ic_baseline_warning_24)
                                    .setTitle("Verify Your Email")
                                    .setMessage("Please check your mail and verify it and then Proceed")
                                    .setPositiveButton("PROCEED", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(getApplicationContext(),login.class));
                                                finish();
                                                progressBar.setVisibility(View.INVISIBLE);
                                        }
                                    })
                                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            progressBar.setVisibility(View.INVISIBLE);
                                            dialog.dismiss();
                                        }
                                    })
                                    .setNeutralButton("RESEND", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if(!user.isEmailVerified()){
                                                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(signup.this,"Please check your registered mail to verify it.",Toast.LENGTH_SHORT).show();
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(signup.this,"onFailure : Email not sent"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }
                                    });

                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(signup.this,"Please check your registered mail to verify it.",Toast.LENGTH_SHORT).show();
                                    dialog.show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(signup.this,"onFailure : Email not sent"+e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });


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
            }
        });
    }
}