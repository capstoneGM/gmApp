package com.example.gmev;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

public class ChargingStationInfo extends AppCompatActivity {

    TextView etPrice, etAddress, etTimeOpen, etNumStations, etChargerType;
    MaterialButton exitBtn;
    static String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging_station_info);

        Intent callingIntent = this.getIntent();
        id = callingIntent.getStringExtra("id");
        Log.d("Mine", "id =  " + id);

        etPrice = findViewById(R.id.price);
        etAddress = findViewById(R.id.chargingStationAddress);
        etTimeOpen = findViewById(R.id.timeOpen);
        etNumStations = findViewById(R.id.numStations);
        etChargerType = findViewById(R.id.chargerType);
        exitBtn = findViewById(R.id.exitbtn);

        if (id.equals("1")) {
            ((TextView)findViewById(R.id.price)).setText("$0.1331/kWH");
            ((TextView)findViewById(R.id.chargingStationAddress)).setText("1636 S 48th St, Springdale, AR 72762");
            ((TextView)findViewById(R.id.timeOpen)).setText("24 hours");
            ((TextView)findViewById(R.id.numStations)).setText("Available: 0/2");
            ((TextView)findViewById(R.id.chargerType)).setText("J1772");
        }
        else if (id.equals("2")) {
            ((TextView)findViewById(R.id.price)).setText("$0.20021/KWH");
            ((TextView)findViewById(R.id.chargingStationAddress)).setText("4280 S Pleasant Crossing Blvd, Rogers, AR 72758");
            ((TextView)findViewById(R.id.timeOpen)).setText("24 hours");
            ((TextView)findViewById(R.id.numStations)).setText("Available: 8/8");
            ((TextView)findViewById(R.id.chargerType)).setText("CCS");
        }
        else if (id.equals("3")) {
            ((TextView)findViewById(R.id.price)).setText("$0.83032/KWH");
            ((TextView)findViewById(R.id.chargingStationAddress)).setText("3000 Gene George Blvd, Springdale, AR 72762");
            ((TextView)findViewById(R.id.timeOpen)).setText("8:00 AM - 10:30 PM");
            ((TextView)findViewById(R.id.numStations)).setText("Available: 1/2");
            ((TextView)findViewById(R.id.chargerType)).setText("TESLA");
        }
        else {
            ((TextView)findViewById(R.id.price)).setText("$0.0910223/KWH");
            ((TextView)findViewById(R.id.chargingStationAddress)).setText("1636 S 48th St, Springdale, AR 72762");
            ((TextView)findViewById(R.id.timeOpen)).setText("12:00 AM - 3:00 PM");
            ((TextView)findViewById(R.id.numStations)).setText("Available: 4/4");
            ((TextView)findViewById(R.id.chargerType)).setText("CHAdeMO");
        }

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}