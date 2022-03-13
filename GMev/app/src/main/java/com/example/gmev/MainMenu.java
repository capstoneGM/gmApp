package com.example.gmev;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smartcar.sdk.SmartcarAuth;
import com.smartcar.sdk.SmartcarCallback;
import com.smartcar.sdk.SmartcarResponse;

import enums.Screens;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainMenu extends AppCompatActivity {
    private static String CLIENT_ID;
    private static String REDIRECT_URI;
    private static String[] SCOPE;
    private static SmartcarAuth smartcarAuth;
    private int charge = 0;
    private float carRange = 0;
    private String carMake;
    private String carModel;
    private int carYear;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference userInfoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        String userId = currentUser.getUid();

        //Charge bar objects
        final ProgressBar chargeBar = findViewById(R.id.charge_bar);
        final TextView chargePercent = findViewById(R.id.charge_num);
        final TextView make = findViewById(R.id.vehicle_make);
        final TextView modelYear = findViewById(R.id.vehicle_model_year);
        final TextView range = findViewById(R.id.vehicle_range);

        //Card buttons
        MaterialCardView profile = (MaterialCardView) findViewById(R.id.profile_card);
        MaterialCardView location = (MaterialCardView) findViewById(R.id.charger_card);
        MaterialCardView bank = (MaterialCardView) findViewById(R.id.bank_card);
        MaterialCardView logOut = (MaterialCardView) findViewById(R.id.logout_card);
        MaterialCardView connection = (MaterialCardView) findViewById(R.id.connect_card);
        MaterialCardView carInfo = (MaterialCardView) findViewById(R.id.vehicle_card);

        //SmartCar initialization
        CLIENT_ID = getString(R.string.client_id);
        REDIRECT_URI = getString(R.string.smartcar_auth_scheme) + "://" + getString(R.string.smartcar_auth_host);
        SCOPE = new String[]{"required:read_vehicle_info", "required:read_battery"};



//        charge = 90;
//        carRange = (float) 314.44;
//        carMake = "CHEVROLET";
//        carModel = "Bolt";
//        carYear = 2018;
//
//
//        connection.setVisibility(View.GONE);
//        updateCharge(chargeBar, chargePercent, range);
//        updateVehicle(make, modelYear);
//        carInfo.setVisibility(View.VISIBLE);

        smartcarAuth = new SmartcarAuth(
                CLIENT_ID,
                REDIRECT_URI,
                SCOPE,
                true,
                new SmartcarCallback() {
                    @Override
                    public void handleResponse(final SmartcarResponse smartcarResponse) {
                        Log.d("SmartcarAuth", "authorization code: " + smartcarResponse.getCode());

                        final OkHttpClient client = new OkHttpClient();

                        new Thread(() -> {
                            //Send request to exchange auth code for access token
                            Request exchangeRequest = new Request.Builder()
                                    .url(getString(R.string.app_server) + "/exchange?code=" + smartcarResponse.getCode() + "&id=" + userId)
                                    .build();

                            try {
                                client.newCall(exchangeRequest).execute();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //Send request to retrieve info
                            Request infoRequest = new Request.Builder()
                                    .url(getString(R.string.app_server) + "/vehicle?id=" + userId)
                                    .build();

                            try {
                                Response response = client.newCall(infoRequest).execute();
                                String jsonBody = response.body().string();
                                JSONObject JObject = new JSONObject(jsonBody);

                                Float chargeDec = Float.valueOf(JObject.getString("percentRemaining")).floatValue() * 100;
                                charge = Math.round(chargeDec);
                                carRange = Float.valueOf(JObject.getString("range")).floatValue();
                                carMake = JObject.getString("make");
                                carModel = JObject.getString("model");
                                carYear = JObject.getInt("year");

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    connection.setVisibility(View.GONE);
                                    updateCharge(chargeBar, chargePercent, range);
                                    updateVehicle(make, modelYear);
                                    carInfo.setVisibility(View.VISIBLE);
                                }
                            });
                        }).start();
                    }
                });

        //Connect to car Button
        smartcarAuth.addClickHandler(getApplicationContext(), connection);

        //Profile Button
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountInfo();
            }
        });

        //Locate Charger Button
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGmMap("https://www.google.com/maps/search/electric+vehicles+charging+stations/@36.2031967,-94.1819611,12z/data=!3m1!4b1");
            }
        });

        //Manage Bank Button
        bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountBalance();
            }
        });

        //Log Out Button
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void openAccountBalance() {
        Intent intentBalance = new Intent(this,AccountBalance.class);
        startActivity(intentBalance);
    }

    private void openAccountInfo() {
        Intent intentAccountInfo = new Intent(this, AccountInfo.class);
        intentAccountInfo.putExtra("from_screen", Screens.MAIN_MENU);
        startActivity(intentAccountInfo);
    }

    private void openGmMap(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void updateCharge(ProgressBar chargeBar, TextView chargePercent, TextView range) {
        chargeBar.setProgress(charge);
        chargePercent.setText(String.valueOf(charge) + "%");
        range.setText("Range: " + String.valueOf(carRange));
    }

    private void updateVehicle(TextView make, TextView modelYear) {
        make.setText(carMake);
        modelYear.setText(carModel + " " + carYear);
    }
}