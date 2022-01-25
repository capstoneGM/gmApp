package com.example.gmev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        MaterialButton submit = (MaterialButton) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBankInfo();
                // to add the condition if the form is fields are correctly
            }
        });
    }

    public void openBankInfo(){
        Intent intentreg = new Intent(this,BankInfo.class);
        startActivity(intentreg);
    }
}