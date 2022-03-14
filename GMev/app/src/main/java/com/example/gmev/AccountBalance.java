package com.example.gmev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseUser;

public class AccountBalance extends AppCompatActivity {
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_balance);

        MaterialButton venmoBtn = (MaterialButton) findViewById(R.id.venmobtn);
        MaterialButton paypalBtn = (MaterialButton) findViewById(R.id.paypalbtn);

        MaterialButton exitbtn = (MaterialButton) findViewById(R.id.exitbtn);

        exitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainMenu();
            }
        });

        venmoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openPaypalSignin(); }
        });

        paypalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openPaypalSignin(); }
        });
    }

    public void openMainMenu() {
        Intent intentExit = new Intent(this, MainMenu.class);
        startActivity(intentExit);
    }

    public void openPaypalSignin() {
        Intent intentPaypal = new Intent("android.intent.action.VIEW", Uri.parse("https://www.paypal.com/us/signin"));
        startActivity((intentPaypal));
    }


}