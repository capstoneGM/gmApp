package com.example.gmev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

import enums.Screens;

public class AccountInfo extends AppCompatActivity {
    private dto.Registration registrationInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_info);


        boolean isSignUp = false;
        registrationInfo = (dto.Registration) getIntent().getSerializableExtra("registration_info");
        if (registrationInfo != null) isSignUp = true;

        MaterialButton exitBtn = (MaterialButton) findViewById(R.id.exitbtn);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        PopulateTextBoxes(isSignUp);

    }

    public void goBack() {
        if (registrationInfo != null) {
            registrationInfo.fullName = ((EditText)findViewById(R.id.fullname)).getText().toString();
            setResult(100, new Intent().putExtra("registration_info", registrationInfo));
        }
        finish();
    }

    public void PopulateTextBoxes(boolean isSignUp) {
        EditText fullName = (EditText) findViewById(R.id.fullname);
        EditText email = (EditText) findViewById(R.id.email);
        EditText phone = (EditText) findViewById(R.id.phone);
        EditText address = (EditText) findViewById(R.id.address);
        EditText model = (EditText) findViewById(R.id.vehiclemodel);
        EditText year = (EditText) findViewById(R.id.year);
        EditText vin = (EditText) findViewById(R.id.vin);

        if (!isSignUp) {
            fullName.setText("Carson Partee");
            email.setText("cpartee6@gmail.com");
            phone.setText("(816) 392-1294");
            address.setText("Fayetteville AR");
            model.setText("Bolt");
            year.setText("2022");
            vin.setText("JHMWD5523DS022721");
        } else {
            fullName.setText(registrationInfo.fullName);
            email.setText(registrationInfo.email);
            phone.setText(registrationInfo.phone);
            address.setText(registrationInfo.address);
            model.setText(registrationInfo.vehicleModel);
            year.setText(registrationInfo.vehicleYear);
            vin.setText(registrationInfo.vehicleIdNumber);
        }
    }
}