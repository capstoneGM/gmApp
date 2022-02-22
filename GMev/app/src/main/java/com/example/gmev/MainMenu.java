package com.example.gmev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import java.net.URI;

import enums.Screens;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        MaterialButton logoutbtn = (MaterialButton) findViewById(R.id.logoutbtn);
        MaterialButton charingstationsbtn = (MaterialButton) findViewById(R.id.charingstationsbtn);
        MaterialButton balancebtn = (MaterialButton) findViewById(R.id.balancebtn);
        MaterialButton accountInfoBtn = (MaterialButton) findViewById((R.id.editprofile));

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainActivity();
            }
        });
        charingstationsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // openGmMap();
                openGmMap("https://www.google.com/maps/search/electric+vehicles+charging+stations/@36.2031967,-94.1819611,12z/data=!3m1!4b1");
            }
        });
        balancebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountBalance();
            }
        });

        accountInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openAccountInfo();}
        });
    }

    private void openAccountBalance() {
        Intent intentBalance = new Intent(this,AccountBalance.class);
        startActivity(intentBalance);
    }

    private void openGmMap(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    public void openMainActivity(){
        Intent intentMain = new Intent(this,MainActivity.class);
        startActivity(intentMain);
    }

    public void openAccountInfo() {
        Intent intentAccountInfo = new Intent(this, AccountInfo.class);
        intentAccountInfo.putExtra("from_screen", Screens.MAIN_MENU);
        startActivity(intentAccountInfo);
    }

}