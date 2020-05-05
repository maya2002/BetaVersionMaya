package com.example.betaversionmaya2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.betaversionmaya2.FBref.refAuth;
import static com.example.betaversionmaya2.FBref.refOrders;

public class OrderBabysitter extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button date_time_in;
    Spinner spDur;
    EditText eTremark;

    String uidP, dur, remark, datetime, dateview, timeview;
    String [] duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_babysitter);

        date_time_in = (Button) findViewById(R.id.date_time_input);
        eTremark = (EditText) findViewById(R.id.eTremark);
        spDur = (Spinner) findViewById(R.id.spDur);

        duration=new String[] {"0.5","1","1.5","2","2.5","3","3.5","4","More"};
        ArrayAdapter<String>adp=new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,duration);
        spDur.setAdapter(adp);
        spDur.setOnItemSelectedListener(this);

        FirebaseUser user = refAuth.getCurrentUser();
        uidP = user.getUid();

        date_time_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(date_time_in);
            }
        });
    }

    private void showDateTimeDialog(final Button date_time_in) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat sDF = new SimpleDateFormat("yyMMddHHmm");
                        SimpleDateFormat sDFdate = new SimpleDateFormat("dd/MM/yy");
                        SimpleDateFormat sDFtime = new SimpleDateFormat("HH:mm");

                        datetime=sDF.format(calendar.getTime());
                        dateview=sDFdate.format(calendar.getTime());
                        timeview=sDFtime.format(calendar.getTime());
                    }
                };
                new TimePickerDialog(OrderBabysitter.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };
        new DatePickerDialog(OrderBabysitter.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void OrderDone(View view) {
        if (datetime.isEmpty() || dur.isEmpty()){
            Toast.makeText(this, "Please fill all order !", Toast.LENGTH_LONG).show();
        } else {
            String strtmp;
            strtmp="Are you sure you want to order babysitter at "+dateview+" in "+timeview+"?";

            AlertDialog.Builder adb = new AlertDialog.Builder(OrderBabysitter.this);
            adb.setTitle("Approval");
            adb.setMessage(strtmp);
            adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    remark=eTremark.getText().toString();
                    ArrayList<Offer> offers=new ArrayList<Offer>();
                    Order newOrder=new Order(uidP,datetime,dur,remark,offers,true);
                    refOrders.child(datetime).setValue(newOrder);
                    Intent i = new Intent(OrderBabysitter.this, HomeParents.class);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dur=duration[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
