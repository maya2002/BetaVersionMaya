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

public class ParentsList extends AppCompatActivity {
    private EditText ParentsName, ParentsEmail, ParentsPwd, ParentsCPwd, ParentsAddress, ParentsCity, ParentsProvince, ParentsZIP, ParentsPhone;
    Button buttonParentsList;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    String userId;
    String statusParents = "parents";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents_list);

        ParentsPhone = findViewById(R.id.ParentsPhoneList);
        ParentsName = findViewById(R.id.ParentsNameList);
        ParentsEmail = findViewById(R.id.ParentsEmailList);
        ParentsPwd = findViewById(R.id.ParentsPasswordList);
        ParentsCPwd = findViewById(R.id.ParentsCPasswordList);
        ParentsAddress = findViewById(R.id.ParentsAddressList);
        ParentsCity = findViewById(R.id.ParentsCityList);
        ParentsProvince = findViewById(R.id.ParentsProvincesList);
        ParentsZIP = findViewById(R.id.ParentsZIPList);
        buttonParentsList = findViewById(R.id.buttonSignUpParents);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        buttonParentsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PN = ParentsName.getText().toString();
                final String PE = ParentsEmail.getText().toString();
                final String PPwd = ParentsPwd.getText().toString();
                String PCPwd = ParentsCPwd.getText().toString();
                String PA = ParentsAddress.getText().toString();
                String PC = ParentsCity.getText().toString();
                String PPR = ParentsProvince.getText().toString();
                String PZIP = ParentsZIP.getText().toString();
                String PPN = ParentsPhone.getText().toString();

                if (PN.isEmpty() || PE.isEmpty() || PPwd.isEmpty() || PPwd.length() < 8 || PCPwd.isEmpty() || !ParentsCPwd.equals(PPwd) || PPN.isEmpty() || PA.isEmpty() || PC.isEmpty() || PPR.isEmpty() || PZIP.isEmpty()) {
                    if (PN.isEmpty()) {
                        ParentsName.setError("Please enter your full name");
                        ParentsName.requestFocus();
                    }
                    if (PE.isEmpty()) {
                        ParentsEmail.setError("Please enter your email");
                        ParentsEmail.requestFocus();
                    }
                    if (PPwd.isEmpty()) {
                        ParentsPwd.setError("Please enter your password");
                        ParentsPwd.requestFocus();
                    }
                    if (PPwd.length() < 8) {
                        ParentsPwd.setError("Password must be at least 8 characters long");
                        ParentsPwd.requestFocus();
                    }
                    if (PCPwd.isEmpty()) {
                        ParentsCPwd.setError("Please confirm your password");
                        ParentsCPwd.requestFocus();
                    }
                    if (!PCPwd.equals(PPwd)) {
                        ParentsCPwd.setError("Confirm your password is not the same as your password");
                        ParentsCPwd.requestFocus();
                    }
                    if (PPN.isEmpty()) {
                        ParentsPhone.setError("Please enter your telephone number");
                        ParentsPhone.requestFocus();
                    }
                    if (PA.isEmpty()) {
                        ParentsAddress.setError("Please enter your address");
                        ParentsAddress.requestFocus();
                    }
                    if (PC.isEmpty()) {
                        ParentsCity.setError("Please enter your city");
                        ParentsCity.requestFocus();
                    }
                    if (PPR.isEmpty()) {
                        ParentsProvince.setError("Please enter your province");
                        ParentsProvince.requestFocus();
                    }
                    if (PZIP.isEmpty()) {
                        ParentsZIP.setError("Please enter your ZIP code");
                        ParentsZIP.requestFocus();
                    }
                } else {
                    final Map<String, Object> userMap = new HashMap<>();
                    userMap.put("Parents Name ", PN);
                    userMap.put("Parents Email ", PE);
                    userMap.put("Parents Password ", PPwd);
                    userMap.put("Confirm Parents's Password ", PCPwd);
                    userMap.put("Parents Phone Number ", PPN);
                    userMap.put("Parents Address ", PA);
                    userMap.put("Parents City ", PC);
                    userMap.put("Province of Parents ",PPR);
                    userMap.put("Parents ZIP ", PZIP);
                    userMap.put("Status ", statusParents);

                    mAuth.createUserWithEmailAndPassword(PE, PPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
//                                userId = mAuth.getCurrentUser().getUid();
//                                CollectionReference data_Parents = mFirestore.collection("Parents Data");
//                                data_Parents.document(userId).set(userMap);
                                //create user id dulu, get uid, baru simpan di database
                                userId = mAuth.getCurrentUser().getUid();
                                mFirestore.collection("Parents Data").document(userId).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                    }
                                });
                                Toast.makeText(ParentsList.this, "Hello, welcome!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ParentsList.this, HomeParents.class);
                                startActivity(i);
                            }
                        }
                    });



//                    mFirestore.collection("Data Majikan").document(userId).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                        }
//                    });


                }
            }
        });

    }
}
