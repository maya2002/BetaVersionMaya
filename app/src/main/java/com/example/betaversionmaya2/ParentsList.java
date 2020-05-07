package com.example.betaversionmaya2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import static com.example.betaversionmaya2.FBref.refAuth;
import static com.example.betaversionmaya2.FBref.refPUsers;

public class ParentsList extends AppCompatActivity {
    private EditText ParentsName, ParentsEmail, ParentsPwd, ParentsAddress, ParentsCity, ParentsProvince, ParentsZIP, ParentsPhone;
    CheckBox cBstayconnect;
    String PN, PE, PPwd, PA,PC, PPR, PZIP, PPN, userId;
    String statusParents = "parents";

    /**
     * On activity start - Checking if user already logged in.
     * If logged in & asked to be remembered - pass on.
     * <p>
     */
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
        Boolean isChecked=settings.getBoolean("stayConnect",false);
        Intent si = new Intent(ParentsList.this,HomeParents.class);
        if (refAuth.getCurrentUser()!=null && isChecked) {
            startActivity(si);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents_list);

        ParentsPhone = findViewById(R.id.ParentsPhoneList);
        ParentsName = findViewById(R.id.ParentsNameList);
        ParentsEmail = findViewById(R.id.ParentsEmailList);
        ParentsPwd = findViewById(R.id.ParentsPasswordList);
        ParentsAddress = findViewById(R.id.ParentsAddressList);
        ParentsCity = findViewById(R.id.ParentsCityList);
        ParentsProvince = findViewById(R.id.ParentsProvincesList);
        ParentsZIP = findViewById(R.id.ParentsZIPList);
        cBstayconnect = findViewById(R.id.cBstayconnect);

    }

    public void regParents(View view) {
        PN = ParentsName.getText().toString();
        PE = ParentsEmail.getText().toString();
        PPwd = ParentsPwd.getText().toString();
        PA = ParentsAddress.getText().toString();
        PC = ParentsCity.getText().toString();
        PPR = ParentsProvince.getText().toString();
        PZIP = ParentsZIP.getText().toString();
        PPN = ParentsPhone.getText().toString();

        if (PN.isEmpty() || PE.isEmpty() || PPwd.isEmpty() || PPN.isEmpty() || PA.isEmpty() || PC.isEmpty() || PPR.isEmpty() || PZIP.isEmpty()) {
            if (PN.isEmpty()) {
                ParentsName.setError("Please enter your full name");
                ParentsName.requestFocus();
            }
            if (PE.isEmpty()) {
                ParentsEmail.setError("Please enter your email");
                ParentsEmail.requestFocus();
            }
            if (PPwd.isEmpty()) {
                ParentsPwd.setError("Please enter your password");
                ParentsPwd.requestFocus();
            }
            if (PPwd.length() < 8) {
                ParentsPwd.setError("Password must be at least 8 characters long");
                ParentsPwd.requestFocus();
            }
            if (PPN.isEmpty()) {
                ParentsPhone.setError("Please enter your telephone number");
                ParentsPhone.requestFocus();
            }
            if (PA.isEmpty()) {
                ParentsAddress.setError("Please enter your address");
                ParentsAddress.requestFocus();
            }
            if (PC.isEmpty()) {
                ParentsCity.setError("Please enter your city");
                ParentsCity.requestFocus();
            }
            if (PPR.isEmpty()) {
                ParentsProvince.setError("Please enter your province");
                ParentsProvince.requestFocus();
            }
            if (PZIP.isEmpty()) {
                ParentsZIP.setError("Please enter your ZIP code");
                ParentsZIP.requestFocus();
            }
        } else {
            final ProgressDialog pd=ProgressDialog.show(this,"Register Parents","Registering...",true);
            refAuth.createUserWithEmailAndPassword(PE, PPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    pd.dismiss();
                    if (task.isSuccessful()) {
                        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                        SharedPreferences.Editor editor=settings.edit();
                        editor.putBoolean("stayConnect",cBstayconnect.isChecked());
                        editor.putBoolean("parents",true);
                        editor.commit();
                        userId = refAuth.getCurrentUser().getUid();
                        User userdb=new User(PN,PE,userId,PPN,PA,PC,PPR,PZIP,true);
                        refPUsers.child(PN).setValue(userdb);
                        Toast.makeText(ParentsList.this, "Hello, welcome!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ParentsList.this, HomeParents.class);
                        startActivity(i);
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException)
                            Toast.makeText(ParentsList.this, "User with e-mail already exist!", Toast.LENGTH_LONG).show();
                        else {
                            Log.w("MainActivity", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(ParentsList.this, "User create failed.",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }
}
