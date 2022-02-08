package com.example.gmev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.net.URI;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        MaterialCardView profile = (MaterialCardView) findViewById(R.id.profile_card);
        MaterialCardView location = (MaterialCardView) findViewById(R.id.charger_card);
        MaterialCardView bank = (MaterialCardView) findViewById(R.id.bank_card);
        MaterialCardView logOut = (MaterialCardView) findViewById(R.id.logout_card);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGmMap("https://www.google.com/maps/search/electric+vehicles+charging+stations/@36.2031967,-94.1819611,12z/data=!3m1!4b1");
            }
        });

        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountBalance();
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });


//        MaterialButton logoutbtn = (MaterialButton) findViewById(R.id.logoutbtn);
//        MaterialButton charingstationsbtn = (MaterialButton) findViewById(R.id.charingstationsbtn);
//        MaterialButton balancebtn = (MaterialButton) findViewById(R.id.balancebtn);
//
//        logoutbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openMainActivity();
//            }
//        });
//        charingstationsbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               // openGmMap();
//                openGmMap("https://www.google.com/maps/search/electric+vehicles+charging+stations/@36.2031967,-94.1819611,12z/data=!3m1!4b1");
//            }
//        });
//        balancebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openAccountBalance();
//            }
//        });
    }

    private void openAccountBalance() {
        Intent intentBalance = new Intent(this,AccountBalance.class);
        startActivity(intentBalance);
    }

    private void openGmMap(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    private void logout(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }



}