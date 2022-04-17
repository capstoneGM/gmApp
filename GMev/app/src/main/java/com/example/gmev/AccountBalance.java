package com.example.gmev;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class AccountBalance extends AppCompatActivity {
    private final Integer MINIMUM_CASHOUT = 500;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference userInfoRef;
    private Map<String, String> data = new HashMap<>();
    private Long balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_balance);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        userInfoRef = FirebaseDatabase.getInstance().getReference("/users").child(currentUser.getUid()).child("balance");
        System.out.println("39045283095023");
        userInfoRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                System.out.println("asfawefwef");
                System.out.println(task.getResult().getValue());
                balance = (Long) task.getResult().getValue();
                if (balance != null) {
                    TextView textView = (TextView) findViewById(R.id.balance);
                    Float parsedBalance =  balance / 100f;
                    textView.setText("$" + String.format("%.2f", parsedBalance));
                }
            }
        });

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
//        Intent intentExit = new Intent(this, MainMenu.class);
//        startActivity(intentExit);
        finish();
    }

    public void openPaypalSignin() {
        if (balance < MINIMUM_CASHOUT) {
            Toast.makeText(AccountBalance.this, "Minimum cashout is $5.00", Toast.LENGTH_LONG).show();
        } else {
            Intent intentPaypal = new Intent("android.intent.action.VIEW", Uri.parse("https://www.paypal.com/us/signin"));
            startActivity((intentPaypal));
        }
    }


}