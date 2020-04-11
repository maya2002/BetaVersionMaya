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
        //        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        drawer = findViewById(R.id.drawerLayoutHomeMajikan);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_open);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();


        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getUid();
    }
}
