package com.example.betaversionmaya2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeBabysitter extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String currentUserId;

    @Override
    protected void onStart(){
        super.onStart();

        if (currentUserId == null){
            startActivity(new Intent(HomeBabysitter.this,BabysitterList.class));
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_babysitter);

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getUid();
    }
}
