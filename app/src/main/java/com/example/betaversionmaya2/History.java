package com.example.betaversionmaya2;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.betaversionmaya2.FBref.refAuth;
import static com.example.betaversionmaya2.FBref.refBUsers;

public class History extends AppCompatActivity {

    String uid;
    User userdb;
    Boolean parents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        FirebaseUser fbuser = refAuth.getCurrentUser();
        uid = fbuser.getUid();
        Query query1 = refBUsers
                .orderByChild("uid")
                .equalTo(uid);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                if (dS.exists()) {
                    for (DataSnapshot data : dS.getChildren()) {
                        userdb = data.getValue(User.class);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        parents=settings.getBoolean("parents",false);
/*
        String datenow = Serv.readDateTime();
        Query query = refOrders.orderByChild("datetime").endAt(datenow);
        query.addListenerForSingleValueEvent(VEL);
        adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, vieworders);
        lVorders.setAdapter(adp);
*/
    }
/*
    com.google.firebase.database.ValueEventListener VEL = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            listorders.clear();
            vieworders.clear();
            for (DataSnapshot data : dS.getChildren()) {
                tmporder=new Order();
                tmporder = data.getValue(Order.class);
                if (parents) {
                    if (tmporder.getUidP()==uid) {
                        listorders.add(tmporder.getDatetime());
                        String tmpDt = tmporder.getDatetime();
                        String viewdt = viewDateTime(tmpDt);
                        vieworders.add(viewdt);
                    }
                } else {
                    if (tmporder.getUidB()==uid) {
                        listorders.add(tmporder.getDatetime());
                        String tmpDt = tmporder.getDatetime();
                        String viewdt = viewDateTime(tmpDt);
                        vieworders.add(viewdt);
                    }
                }
            }
            adp.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
*/
}
