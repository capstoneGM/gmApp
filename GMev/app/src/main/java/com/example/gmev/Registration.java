package com.example.gmev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import enums.Screens;

public class Registration extends AppCompatActivity {


    private FirebaseAuth mAuth;
    EditText mFullName, mEmail, mPhoneNumber, mAddress,mVehicleModel, mYear, mVIN, mPassword1, mPassword2;
    MaterialButton mSubmit;
    ProgressBar mProgressbar;


    private dto.Registration registrationInfo = new dto.Registration();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        mFullName = findViewById(R.id.fullname);
        mEmail = findViewById(R.id.email);
        mPhoneNumber =findViewById(R.id.phone);
        mAddress =findViewById(R.id.address);
        mVehicleModel = findViewById(R.id.vehiclemodel);
        mYear = findViewById(R.id.year);
        mVIN = findViewById(R.id.vin);
        mPassword1 = findViewById(R.id.password1);
        mPassword2 = findViewById(R.id.password2);
        mSubmit = findViewById(R.id.submit);
        mAuth = FirebaseAuth.getInstance();
        mProgressbar = findViewById(R.id.progressbar);




        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = mEmail.getText().toString().trim();
                String password = mPassword1.getText().toString().trim();
                String passwordcopy= mPassword2.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email address can not be blank");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPassword1.setError("Password can not be blank");
                    return;
                }

                if(password.length()<6){
                    mPassword1.setError("Password must be at least six characters");
                    return;
                }
                if(!password.equals(passwordcopy)){
                  mPassword2.setError("Password must be the same");
                  return;
                }
                mProgressbar.setVisibility(view.VISIBLE);

                //registration
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Registration.this, "Account Created.",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),BankInfo.class));
                        }else{
                            //Toast.makeText(Registration.this, "missing fields"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                openAccountInfo();
            }
        });
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        dto.Registration returnData = (dto.Registration) data.getSerializableExtra("registration_info");
        if (returnData != null) {
            registrationInfo = returnData;
        }
    }

    public void openAccountInfo(){
        Intent intentAccountInfo = new Intent(this,AccountInfo.class);

        registrationInfo.username = ((EditText)findViewById(R.id.username)).getText().toString();
        registrationInfo.password = ((EditText)findViewById(R.id.password1)).getText().toString();

        intentAccountInfo.putExtra("registration_info", registrationInfo);
        startActivityForResult(intentAccountInfo, 200);
    }
}