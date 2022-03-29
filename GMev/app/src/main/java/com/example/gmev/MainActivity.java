package com.example.gmev;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText mEmail, mPassword;
    MaterialButton loginbtn;
    MaterialButton signupbtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        checkCurrentUser();

        mEmail = findViewById(R.id.chargingStationAddress);
        mPassword = findViewById(R.id.password);
        loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        signupbtn = (MaterialButton) findViewById(R.id.signupbtn);
        progressBar = findViewById(R.id.progressbar);
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
                progressBar.setVisibility(view.VISIBLE);
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            openMainMenu();
                        } else {
                            Toast.makeText(MainActivity.this, "your email or password are incorrect" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }));

        signupbtn.setOnClickListener((new View.OnClickListener() {
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
    public void openMainMenu(){
        Toast.makeText(MainActivity.this, "Logged in.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void checkCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            openMainMenu();
        }
    }
}