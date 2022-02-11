package com.example.gmev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText mEmail, mPassword;
    MaterialButton loginbtn;
    MaterialButton singupbtn;
    ProgressBar progressbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        singupbtn = (MaterialButton) findViewById(R.id.signupbtn);
        progressbar = findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();


        loginbtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email address can not be blank");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password can not be blank");
                    return;
                }
                if(password.length()<6){
                    mPassword.setError("Password must be at least six characters");
                    return;
                }
                progressbar.setVisibility(view.VISIBLE);
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Logged in.",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainMenu.class));
                        }else{
                            Toast.makeText(MainActivity.this, "your email or password are incorrect"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }

                    }
                });
            }
        }));
        singupbtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistration();
            }
        }));


    }
    public void openRegistration(){
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }


}