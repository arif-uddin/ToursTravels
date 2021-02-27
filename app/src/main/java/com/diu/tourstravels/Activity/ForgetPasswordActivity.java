package com.diu.tourstravels.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.diu.tourstravels.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class ForgetPasswordActivity extends AppCompatActivity {

    TextView email;
    Button btnConfirm;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        Objects.requireNonNull(getSupportActionBar()).hide();

        email=findViewById(R.id.email);
        btnConfirm=findViewById(R.id.confirm);
        mAuth=FirebaseAuth.getInstance();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(email.getText().toString().trim().replaceAll(" ",""))
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ForgetPasswordActivity.this, "Check your email to reset password!", Toast.LENGTH_SHORT).show();
                                    //go to login page
                                    Intent intent = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        });
            }
        });
    }
}