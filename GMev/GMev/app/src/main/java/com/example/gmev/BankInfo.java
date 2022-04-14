package com.example.gmev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class BankInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_info);


        MaterialButton savebtn = (MaterialButton) findViewById(R.id.savebtn);
        MaterialButton skipbtn =(MaterialButton) findViewById(R.id.skipbtn);


        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we still need to add the condition if bank info are correct
                openMainMenu();
            }
        });

        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainMenu();
            }
        });
    }
    public void openMainMenu(){
        Intent intenMain = new Intent(this,MainMenu.class);
        startActivity(intenMain);
    }
}