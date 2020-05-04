package com.example.betaversionmaya2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button ParentsList, BabysitterList, EnterParents, EnterBabysitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ParentsList = findViewById(R.id.signUpParents);
        BabysitterList = findViewById(R.id.signUpBabysitter);
        EnterParents = findViewById(R.id.loginParents);
        EnterBabysitter = findViewById(R.id.loginBabysitter);

        ParentsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this, ParentsList.class);
                startActivity(signup);
            }
        });
        BabysitterList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this, BabysitterList.class);
                startActivity(signup);
            }
        });
        EnterBabysitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this, LoginBabysitter.class);
                startActivity(signup);
            }
        });
        EnterParents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this, LoginParents.class);
                startActivity(signup);
            }
        });
    }

}