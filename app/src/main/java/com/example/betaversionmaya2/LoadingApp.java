package com.example.betaversionmaya2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingApp extends AppCompatActivity {
    private static final long DELAY = 3000;
    private boolean scheduled = false;
    private Timer splashTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_app);
        splashTimer = new Timer();
        splashTimer.schedule(new TimerTask(){
            @Override
            public void run(){
                LoadingApp.this.finish();
                startActivity(new Intent(LoadingApp.this, MainActivity.class));
            }
        }, DELAY);
        scheduled = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scheduled)
            splashTimer.cancel();
        splashTimer.purge();

    }
}
