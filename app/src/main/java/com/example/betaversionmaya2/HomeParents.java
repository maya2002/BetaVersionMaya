package com.example.betaversionmaya2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

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


public class HomeParents extends AppCompatActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemSelectedListener {
    Spinner sp;
    ListView lV;

    String uid, uidB, tmpstr;
    User userP, userB;
    ArrayList<String> vieworders = new ArrayList<>();
    ArrayList<Order> listorders = new ArrayList<Order>();
    Order tmporder, order;
    Offer offer;
    ArrayAdapter<String> adpSp, adpLv;
    ArrayList<Offer> offers;
    String Bprice;
    ArrayList<String> viewoffers = new ArrayList<>();

    Intent t;

    @Override
    protected void onStart(){
    super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_parents);

        sp = (Spinner) findViewById(R.id.spinner);
        lV= (ListView) findViewById(R.id.listView);

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
                        userP = data.getValue(User.class);
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
        adpSp = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, vieworders);
        sp.setAdapter(adpSp);
        sp.setOnItemSelectedListener(this);

        offers=new ArrayList<Offer>();
        /*
        lV.setOnItemClickListener(this);
        lV.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

*/
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
                    if (uid==tmporder.getUidP()) {
                        listorders.add(tmporder);
                        String tmpDt = tmporder.getDatetime();
                        String viewdt = viewDateTime(tmpDt);
                        if (tmporder.getUidB()!=null){
                            viewdt=viewdt+"   Aprooved !!!";
                        }
                        vieworders.add(viewdt);
                    }
                }
            }
            adpSp.notifyDataSetChanged();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };


    public void order(View view) {
        Intent i = new Intent(HomeParents.this, OrderBabysitter.class);
        startActivity(i);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if (order.getUidB()!=null) {
            Toast.makeText(this, "Order already approved !", Toast.LENGTH_SHORT).show();
        } else {
            offer=offers.get(position);
            tmpstr="Are you sure you want to approve "+viewoffers.get(position)+" to this babysitter?";
            AlertDialog.Builder adb = new AlertDialog.Builder(HomeParents.this);
            adb.setTitle("Approval");
            adb.setMessage(tmpstr);
            adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    refOrders.child(order.getDatetime()).child(uidB).setValue(uidB);
                    refOrders.child(order.getDatetime()).child("offerlist").child(""+position).child("approved").setValue("true");
                }
            });
            adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog ad=adb.create();
            ad.show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        order=listorders.get(position);
        offers=order.getOfferlist();
        if (offers!=null){
            for (int i=1 ;i<offers.size() ;i++){
                Offer offer=offers.get(i);
                Bprice=Integer.toString(offer.getPrice());
                if (offer.getApproved()) {
                    Bprice=Bprice+" Approved";
                }
                uidB=offer.getUidB();
                Query query3 = refBUsers
                        .orderByChild("uid")
                        .equalTo(uid);
                query3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dS) {
                        if (dS.exists()) {
                            for (DataSnapshot data : dS.getChildren()) {
                                userB = data.getValue(User.class);
                            }
                            viewoffers.add(userB.getName()+" for "+Bprice);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            adpLv= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,viewoffers);
            lV.setAdapter(adpLv);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
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