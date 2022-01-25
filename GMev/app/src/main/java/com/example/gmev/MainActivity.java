package com.example.gmev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        MaterialButton loginbtn = (MaterialButton) findViewById(R.id.loginbtn);
        MaterialButton singupbtn = (MaterialButton) findViewById(R.id.signupbtn);


        //Default username (team1) and password (team1)


        loginbtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("team1") && password.getText().toString().equals("team1")){
                    //Toast.makeText(MainActivity.this, "LOGIN SUCCESSFULL", Toast.LENGTH_SHORT).show();
                    openMainMenu();
                }else {
                    Toast.makeText(MainActivity.this, "your user name or password are not correct", Toast.LENGTH_SHORT).show();

                }
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
    public void openMainMenu(){
        Intent intent2 = new Intent(this, MainMenu.class);
        startActivity(intent2);
    }

}