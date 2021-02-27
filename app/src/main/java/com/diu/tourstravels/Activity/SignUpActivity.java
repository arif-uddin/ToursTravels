package com.diu.tourstravels.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.diu.tourstravels.Model.ModelUser;
import com.diu.tourstravels.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity implements  View.OnClickListener{

    private TextView textViewLogin,textViewEmail,textViewName,textViewPassword,contactNo;
    private FirebaseAuth mAuth;

    private Button signUp;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Objects.requireNonNull(getSupportActionBar()).hide();

        mAuth=FirebaseAuth.getInstance();

        textViewLogin=findViewById(R.id.login);
        textViewEmail=findViewById(R.id.email);
        textViewName=findViewById(R.id.name);
        textViewPassword=findViewById(R.id.password);
        signUp=findViewById(R.id.btnSignUp);
        contactNo=findViewById(R.id.contactNo);
        progressBar=findViewById(R.id.progressBar);

        textViewLogin.setOnClickListener(this);
        signUp.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                Intent intent= new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnSignUp:
                registeruser();
                break;

        }
    }

    private void registeruser() {

        final String email=textViewEmail.getText().toString().trim();
        String password=textViewPassword.getText().toString().trim();
        final String fullname=textViewName.getText().toString().trim();
        final String contact=contactNo.getText().toString().trim();

        if(fullname.isEmpty()) {
            textViewName.setError("Full name is required");
            textViewName.requestFocus();
            return;
        }

        if(contact.isEmpty()) {
            contactNo.setError("E-mail is required");
            contactNo.requestFocus();
            return;
        }

        if(email.isEmpty()) {
            textViewEmail.setError("E-mail is required");
            textViewEmail.requestFocus();
            return;
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textViewEmail.setError("Please enter valid email");
            textViewEmail.requestFocus();
            return;
        }


        if(password.isEmpty()) {
            textViewPassword.setError("Password is required");
            textViewPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            textViewPassword.setError("Password must be at least 6 characters!");
            textViewPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            ModelUser user= new ModelUser(FirebaseAuth.getInstance().getCurrentUser().getUid(),fullname,email,contact,"default");

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this,"User has been registered!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                        Intent intent= new Intent(SignUpActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(SignUpActivity.this,"Failed to register!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }

                                }
                            });
                        }

                        else {
                            Toast.makeText(SignUpActivity.this,"Failed to register!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

    }
}