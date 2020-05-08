package com.example.betaversionmaya2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.betaversionmaya2.FBref.refAuth;
import static com.example.betaversionmaya2.FBref.refBUsers;
import static com.example.betaversionmaya2.FBref.refOrders;
import static com.example.betaversionmaya2.Serv.viewDateTime;

public class HomeBabysitter extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lVapproved, lVorders;
    ArrayAdapter<String> adp, adp1;

    TextView tV;

    ArrayList<String> listorders = new ArrayList<>();
    ArrayList<Order> approval = new ArrayList<Order>();
    ArrayList<String> vieworders = new ArrayList<String>();
    ArrayList<String> viewapproval = new ArrayList<String>();

    String uid;
    User userB;
    Order tmporder;

    Intent t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_babysitter);

        lVapproved = (ListView) findViewById(R.id.lVapproved);
        lVorders = (ListView) findViewById(R.id.lVorders);
        tV = (TextView) findViewById(R.id.welcome);

        lVorders.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lVorders.setOnItemClickListener(HomeBabysitter.this);

        tV.setText("Welcome");

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
                        userB = data.getValue(User.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        String datenow = Serv.readDateTime();
        Query query2 = refOrders.orderByChild("datetime").startAt(datenow);
        query2.addListenerForSingleValueEvent(VEL2);
        adp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, vieworders);
        lVorders.setAdapter(adp);

        Query query3 = refOrders.orderByChild("uidB").equalTo(uid);
        query3.addListenerForSingleValueEvent(VEL3);
        adp1 = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, viewapproval);
        lVapproved.setAdapter(adp1);


    }

    com.google.firebase.database.ValueEventListener VEL2 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            listorders.clear();
            vieworders.clear();
            for (DataSnapshot data : dS.getChildren()) {
                tmporder=new Order();
                tmporder = data.getValue(Order.class);
                if (tmporder.getActive()) {
                    listorders.add(tmporder.getDatetime());
                    String tmpDt = tmporder.getDatetime();
                    String viewdt = viewDateTime(tmpDt);
                    vieworders.add(viewdt);
                }
            }
            adp.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    com.google.firebase.database.ValueEventListener VEL3 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dS) {
            approval.clear();
            viewapproval.clear();
            for (DataSnapshot data : dS.getChildren()) {
                Order tmpapproval = data.getValue(Order.class);
                if (tmpapproval.getActive()) {
                    approval.add(tmpapproval);
                    String tmpDt = tmpapproval.getDatetime();
                    String viewdt = viewDateTime(tmpDt);
                    viewapproval.add(viewdt);
                }
            }
            adp1.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent si = new Intent(HomeBabysitter.this, OrderDetailsB.class);
        si.putExtra("ordernum", listorders.get(position));
        startActivity(si);
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

