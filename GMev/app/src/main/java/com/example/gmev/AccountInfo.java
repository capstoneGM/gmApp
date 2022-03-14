package com.example.gmev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import enums.Screens;

public class AccountInfo extends AppCompatActivity {
    private dto.Registration registrationInfo;
    private dto.AccountInfo accountInfo;
    private ProgressBar mProgressbar;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference userInfoRef;
    private Map<String, String> data = new HashMap<>();
    private Boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_account_info);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        isLoggedIn = currentUser != null;

        if (isLoggedIn) {
            userInfoRef = FirebaseDatabase.getInstance().getReference("/users").child(currentUser.getUid());
            userInfoRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    accountInfo = new dto.AccountInfo((Map<String, String>) task.getResult().getValue());
                    PopulateTextBoxes();
                }
            });
        } else {
            registrationInfo = (dto.Registration) getIntent().getSerializableExtra("registration_info");
        }

        MaterialButton exitBtn = (MaterialButton) findViewById(R.id.exitbtn);
        MaterialButton submitBtn = (MaterialButton) findViewById(R.id.account_info_submit);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBack();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    public void submit() {
        if (isLoggedIn) {
            saveState();
            userInfoRef.setValue(infoToHash()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // updated
                }
            });
            currentUser.updateEmail(accountInfo.email);
            // save
        } else {
            firebaseAuth.createUserWithEmailAndPassword(registrationInfo.email, registrationInfo.password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        userInfoRef = FirebaseDatabase.getInstance().getReference("/users").child(task.getResult().getUser().getUid());
                        saveState();
                        userInfoRef.setValue(infoToHash());
                        Toast.makeText(AccountInfo.this, "Account Created.",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),BankInfo.class));
                    } else {
                        //Toast.makeText(Registration.this, "missing fields"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void goBack() {
        if (registrationInfo != null) {
            this.saveState();
            setResult(100, new Intent().putExtra("registration_info", registrationInfo));
        }
        finish();
    }

    private Map<String, String> infoToHash() {
        Map<String, String> data = new HashMap<>();

        if (!isLoggedIn) {
            data.put("fullName", registrationInfo.fullName);
            data.put("phone", registrationInfo.phone);
            data.put("address", registrationInfo.address);
            data.put("vehicleModel", registrationInfo.vehicleModel);
            data.put("vehicleYear", registrationInfo.vehicleYear);
            data.put("vehicleIdNumber", registrationInfo.vehicleIdNumber);
        } else {
            data.put("fullName", accountInfo.fullName);
            data.put("phone", accountInfo.phone);
            data.put("address", accountInfo.address);
            data.put("vehicleModel", accountInfo.vehicleModel);
            data.put("vehicleYear", accountInfo.vehicleYear);
            data.put("vehicleIdNumber", accountInfo.vehicleIdNumber);
        }

        return data;
    }

    private void saveState() {
        if (!isLoggedIn) {
            registrationInfo.fullName = ((EditText) findViewById(R.id.fullname)).getText().toString();
            registrationInfo.phone = ((EditText) findViewById(R.id.phone)).getText().toString();
            registrationInfo.address = ((EditText) findViewById(R.id.address)).getText().toString();
            registrationInfo.vehicleModel = ((EditText) findViewById(R.id.vehiclemodel)).getText().toString();
            registrationInfo.vehicleYear = ((EditText) findViewById(R.id.year)).getText().toString();
            registrationInfo.vehicleIdNumber = ((EditText) findViewById(R.id.vin)).getText().toString();
        } else {
            accountInfo.email = ((EditText) findViewById(R.id.email)).getText().toString();
            accountInfo.fullName = ((EditText) findViewById(R.id.fullname)).getText().toString();
            accountInfo.phone = ((EditText) findViewById(R.id.phone)).getText().toString();
            accountInfo.address = ((EditText) findViewById(R.id.address)).getText().toString();
            accountInfo.vehicleModel = ((EditText) findViewById(R.id.vehiclemodel)).getText().toString();
            accountInfo.vehicleYear = ((EditText) findViewById(R.id.year)).getText().toString();
            accountInfo.vehicleIdNumber = ((EditText) findViewById(R.id.vin)).getText().toString();
        }
    }

    private void PopulateTextBoxes() {
        EditText fullName = (EditText) findViewById(R.id.fullname);
        EditText email = (EditText) findViewById(R.id.email);
        EditText phone = (EditText) findViewById(R.id.phone);
        EditText address = (EditText) findViewById(R.id.address);
        EditText model = (EditText) findViewById(R.id.vehiclemodel);
        EditText year = (EditText) findViewById(R.id.year);
        EditText vin = (EditText) findViewById(R.id.vin);

        if (!isLoggedIn) {
            fullName.setText(registrationInfo.fullName);
            email.setText(registrationInfo.email);
            phone.setText(registrationInfo.phone);
            address.setText(registrationInfo.address);
            model.setText(registrationInfo.vehicleModel);
            year.setText(registrationInfo.vehicleYear);
            vin.setText(registrationInfo.vehicleIdNumber);
        } else {
            fullName.setText(accountInfo.fullName);
            email.setText(currentUser.getEmail());
            phone.setText(accountInfo.phone);
            address.setText(accountInfo.address);
            model.setText(accountInfo.vehicleModel);
            year.setText(accountInfo.vehicleYear);
            vin.setText(accountInfo.vehicleIdNumber);
        }
    }
}