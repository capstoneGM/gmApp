package com.example.gmev;

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
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    EditText emaiEditText;
    MaterialButton resetPasswordb;

    ProgressBar progressBar;
    private FirebaseAuth Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        emaiEditText= (EditText) findViewById(R.id.email);
        resetPasswordb=(MaterialButton) findViewById(R.id.resetPasswordbtn);
        progressBar=(ProgressBar) findViewById(R.id.progressbar);

        Auth=FirebaseAuth.getInstance();

        resetPasswordb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetPassword();
            }
        });

    }

    private void resetPassword(){
        String email =emaiEditText.getText().toString().trim();

        if(email.isEmpty()){
            emaiEditText.setError("Email can not be blank");
            emaiEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emaiEditText.setError("Email is not vaild");
            emaiEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        Auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgetPassword.this, "Check your email for the link the reset your password!", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(ForgetPassword.this, "Try again! Something went wrong! "+task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });


    }
}