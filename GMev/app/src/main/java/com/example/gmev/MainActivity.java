package com.example.gmev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_main);


        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        MaterialButton singupbtn = (MaterialButton) findViewById(R.id.signupbtn);


        //Default username (team1) and password (team1)


        loginbtn.setOnClickListener((new View.OnClickListener() {
            @Override
//            public void onClick(View view) {
//                if(username.getText().toString().equals("team1") && password.getText().toString().equals("team1")){
//                    //Toast.makeText(MainActivity.this, "LOGIN SUCCESSFULL", Toast.LENGTH_SHORT).show();
//                    openMainMenu();
//                }else {
//                    Toast.makeText(MainActivity.this, "your user name or password are not correct", Toast.LENGTH_SHORT).show();
//
//                }
//            }
            public void onClick(View view) {
                String email = username.getText().toString();
                String pass = password.getText().toString();
                signIn(email, pass);
            }
        }));
        singupbtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegistration();
            }
        }));


    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            openMainMenu();
                        }
                    }
                });
    }
    public void openRegistration(){
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }
    public void openMainMenu(){
        Intent intent2 = new Intent(this, MainMenu.class);
        startActivity(intent2);
    }

}