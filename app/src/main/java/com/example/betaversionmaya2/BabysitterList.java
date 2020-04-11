package com.example.betaversionmaya2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BabysitterList extends AppCompatActivity {
    private EditText BabysitterName, BabysitterEmail, BabysitterPwd, BabysitterCPwd, BabysitterAddress, BabysitterCity, BabysitterProvince, BabysitterZIP, BabysitterPN, BabysitterWA;
    Button buttonBabysitterList;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    String user_id;
    String statusBabysitter = "babysitter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter_list);
        BabysitterName = findViewById(R.id.BabysitterNamesList);
        BabysitterEmail = findViewById(R.id.BabysitterEmailList);
        BabysitterPwd = findViewById(R.id.BabysitterPasswordList);
        BabysitterCPwd = findViewById(R.id.BabysitterCPasswordList);
        BabysitterAddress = findViewById(R.id.BabysitterAddressList);
        BabysitterCity = findViewById(R.id.BabysitterCityList);
        BabysitterProvince = findViewById(R.id.BabysitterProvincialList);
        BabysitterZIP = findViewById(R.id.BabysitterZIPList);
        BabysitterPN = findViewById(R.id.BabysitterPhoneList);
        BabysitterWA = findViewById(R.id.BabysitterWAList);
        buttonBabysitterList = findViewById(R.id.buttonSignUpBabysitter);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        buttonBabysitterList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String BSN = BabysitterName.getText().toString();
                final String BSE = BabysitterEmail.getText().toString();
                final String BSPW = BabysitterPwd.getText().toString();
                final String BSCP = BabysitterCPwd.getText().toString();
                final String BSA = BabysitterAddress.getText().toString();
                final String BSC = BabysitterCity.getText().toString();
                final String BSPR = BabysitterProvince.getText().toString();
                final String BSZIP = BabysitterZIP.getText().toString();
                final String BSPN = BabysitterPN.getText().toString();
                final String BSWA = BabysitterWA.getText().toString();

                if (BSN.isEmpty() || BSE.isEmpty() || BSPW.isEmpty() || BSPW.length() < 8 || BSCP.isEmpty() || !BSCP.equals(BSCP) || BSPN.isEmpty() || BSA.isEmpty() || BSC.isEmpty() || BSPR.isEmpty() || BSZIP.isEmpty() || BSWA.isEmpty()) {
                    if (BSN.isEmpty()) {
                        BabysitterName.setError("Please enter your full name");
                        BabysitterName.requestFocus();
                    }
                    if (BSE.isEmpty()) {
                        BabysitterEmail.setError("Please enter your email");
                        BabysitterEmail.requestFocus();
                    }
                    if (BSPW.isEmpty()) {
                        BabysitterPwd.setError("Please enter your password");
                        BabysitterPwd.requestFocus();
                    }
                    if (BSPW.length() < 8) {
                        BabysitterPwd.setError("Password must be at least 8 characters long");
                        BabysitterPwd.requestFocus();
                    }
                    if (BSCP.isEmpty()) {
                        BabysitterCPwd.setError("Please confirm your password");
                        BabysitterCPwd.requestFocus();
                    }
                    if (!BSCP.equals(BSPW)) {
                        BabysitterCPwd.setError("Confirm your password is not the same as your password");
                        BabysitterCPwd.requestFocus();
                    }
                    if (BSPN.isEmpty()) {
                        BabysitterPN.setError("Please enter your telephone number");
                        BabysitterPN.requestFocus();
                    }
                    if (BSA.isEmpty()) {
                        BabysitterAddress.setError("Please enter your address");
                        BabysitterAddress.requestFocus();
                    }
                    if (BSC.isEmpty()) {
                        BabysitterCity.setError("Please enter your city");
                        BabysitterCity.requestFocus();
                    }
                    if (BSPR.isEmpty()) {
                        BabysitterProvince.setError("Please enter your province");
                        BabysitterProvince.requestFocus();
                    }
                    if (BSZIP.isEmpty()) {
                        BabysitterZIP.setError("Please enter your ZIP code");
                        BabysitterZIP.requestFocus();
                    }
                    if (BSWA.isEmpty()) {
                        BabysitterWA.setError("Please enter your WA number");
                        BabysitterWA.requestFocus();
                    }
                } else {
                    final Map<String,String> userData = new HashMap<>();
                    userData.put("Babysitter Name", BSN);
                    userData.put("Babysitter Email ", BSE);
                    userData.put("Babysitter Password ", BSPW);
                    userData.put("Babysitter Password Confirmation ", BSCP);
                    userData.put("Babysitter Phone Number ", BSPN);
                    userData.put("Babysitter Address ", BSA);
                    userData.put("Babysitter City ", BSC);
                    userData.put("Babysitter Province ", BSPR);
                    userData.put("Babysitter ZIP", BSZIP);
                    userData.put("Babysitter WA Number ", BSWA);
                    userData.put("Status ", statusBabysitter);



                    mAuth.createUserWithEmailAndPassword(BSE, BSPW).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user_id = mAuth.getCurrentUser().getUid();
                                mFirestore.collection("Babysitter Data ").document(user_id).set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                                Toast.makeText(BabysitterList.this, "Hello, welcome!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(BabysitterList.this, HomeBabysitter.class);
                                startActivity(i);
                            }
                        }
                    });

                }
            }
        });

    }
}

