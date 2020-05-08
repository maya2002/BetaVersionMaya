package com.example.betaversionmaya2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
import static com.example.betaversionmaya2.FBref.refOrders;
import static com.example.betaversionmaya2.FBref.refPUsers;
import static com.example.betaversionmaya2.Serv.viewDateTime;

public class OrderDetailsB extends AppCompatActivity {
    TextView tVdatetime, tVparents, tVPremark;
    EditText eTremark, eTprice;
    String Bremark, Bprice;
    int BpriceInt;
    String uidB, ordernum;
    String uidP, datetime, dur, Premark, parents;
    ArrayList<Offer> offerlist;
    Boolean orderOffered;

    String tmpstr;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_b);

        eTremark = (EditText) findViewById(R.id.eTremark);
        eTprice= (EditText) findViewById(R.id.eTprice);
        tVdatetime= (TextView) findViewById(R.id.tVdatetime);
        tVparents= (TextView) findViewById(R.id.tVparents);
        tVPremark= (TextView) findViewById(R.id.tVPremark);

        Intent gi=getIntent();
        ordernum=gi.getStringExtra("ordernum");

        FirebaseUser fbuser = refAuth.getCurrentUser();
        uidB = fbuser.getUid();

        orderOffered=false;

        Query query=refOrders.orderByChild("datetime").equalTo(ordernum);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dS) {
                if (dS.exists()) {
                    for(DataSnapshot data : dS.getChildren()) {
                        order = data.getValue(Order.class);
                        uidP=order.getUidP();
                        datetime=order.getDatetime();
                        dur=order.getDur();
                        Premark=order.getRemark();
                        offerlist=order.getOfferlist();
                    }
                    tmpstr=viewDateTime(datetime);
                    tVdatetime.setText("At "+tmpstr+" for "+dur+" hours");
                    tVPremark.setText(Premark);
                    Query query1=refPUsers.orderByChild("uid").equalTo(uidP);
                    query1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dS) {
                            if (dS.exists()) {
                                for(DataSnapshot data : dS.getChildren()) {
                                    User userP = data.getValue(User.class);
                                    parents=userP.getName();
                                }
                                tVparents.setText(parents);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
/*                    if (offerlist!=null){
                        for (){
                            ext offer
                                if (uidB==offer.getUidB) {
                                    eTremark.setText(offer.getremark())
                                    eTprice.setText(offer.getPrice())
                                    orderOffered=true;
                                }

                        }
                    }*/
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }



    public void OrderB(View view) {
        Bremark = eTremark.getText().toString();
        Bprice = eTprice.getText().toString();

        if (Bremark.isEmpty() || Bprice.isEmpty()) {
            Toast.makeText(this, "Please fill all order!", Toast.LENGTH_LONG).show();
        } else if (orderOffered) {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(OrderDetailsB.this, HomeBabysitter.class);
            startActivity(i);
        } else {
            tmpstr="Are you sure you want to offer "+Bprice+" for this babysitter?";
            AlertDialog.Builder adb = new AlertDialog.Builder(OrderDetailsB.this);
            adb.setTitle("Approval");
            adb.setMessage(tmpstr);
            adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    BpriceInt = Integer.parseInt(Bprice);
                    Offer offer=new Offer(uidB,BpriceInt,Bremark);
                    if (offerlist==null) {
                        offerlist=new ArrayList<Offer>();
                    }
                    offerlist.add(offer);
                    Order order=new Order(uidP,"Null",datetime,dur,Premark,offerlist,true);
                    refOrders.child(ordernum).setValue(order);
                    Intent i = new Intent(OrderDetailsB.this, HomeBabysitter.class);
                    startActivity(i);
                }
            });
            adb.setNegativeButton("Edit again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog ad=adb.create();
            ad.show();
        }
    }
}
