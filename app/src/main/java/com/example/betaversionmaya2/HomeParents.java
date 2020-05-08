package com.example.betaversionmaya2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class HomeParents extends AppCompatActivity {
    Intent t;

    @Override
    protected void onStart(){
    super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_parents);
    }


    public void order(View view) {
        Intent i = new Intent(HomeParents.this, OrderBabysitter.class);
        startActivity(i);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String s = item.getTitle().toString();
        t = new Intent(this, HomeParents.class);
        if (s.equals("History")) {
            t = new Intent(this, History.class);
            startActivity(t);
        }
        if (s.equals("Credits")) {
            t = new Intent(this, Credits.class);
            startActivity(t);
        }
        return true;
    }
}