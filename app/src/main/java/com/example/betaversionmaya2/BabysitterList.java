package com.example.betaversionmaya2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import static com.example.betaversionmaya2.FBref.refAuth;
import static com.example.betaversionmaya2.FBref.refBUsers;

public class BabysitterList extends AppCompatActivity {
    EditText BabysitterName, BabysitterEmail, BabysitterPwd, BabysitterAddress, BabysitterCity, BabysitterProvince, BabysitterZIP, BabysitterPhone;
    String BSN, BSE, BSPwd, BSA,BSC, BSPR, BSZIP, BSPN, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_babysitter_list);

        BabysitterPhone = findViewById(R.id.BabysitterPhoneList);
        BabysitterName = findViewById(R.id.BabysitterNamesList);
        BabysitterEmail = findViewById(R.id.BabysitterEmailList);
        BabysitterPwd = findViewById(R.id.BabysitterPasswordList);
        BabysitterAddress = findViewById(R.id.BabysitterAddressList);
        BabysitterCity = findViewById(R.id.BabysitterCityList);
        BabysitterProvince = findViewById(R.id.BabysitterProvincialList);
        BabysitterZIP = findViewById(R.id.BabysitterZIPList);
    }

    public void regBabysitter(View view) {
        BSN = BabysitterName.getText().toString();
        BSE = BabysitterEmail.getText().toString();
        BSPwd = BabysitterPwd.getText().toString();
        BSA = BabysitterAddress.getText().toString();
        BSC = BabysitterCity.getText().toString();
        BSPR = BabysitterProvince.getText().toString();
        BSZIP = BabysitterZIP.getText().toString();
        BSPN = BabysitterPhone.getText().toString();

        if (BSN.isEmpty() || BSE.isEmpty() || BSPwd.isEmpty() || BSPN.isEmpty() || BSA.isEmpty() || BSC.isEmpty() || BSPR.isEmpty() || BSZIP.isEmpty()) {
            if (BSN.isEmpty()) {
                BabysitterName.setError("Please enter your full name");
                BabysitterName.requestFocus();
            }
            if (BSE.isEmpty()) {
                BabysitterEmail.setError("Please enter your email");
                BabysitterEmail.requestFocus();
            }
            if (BSPwd.isEmpty()) {
                BabysitterPwd.setError("Please enter your password");
                BabysitterPwd.requestFocus();
            }
            if (BSPN.isEmpty()) {
                BabysitterPhone.setError("Please enter your telephone number");
                BabysitterPhone.requestFocus();
            }
            if (BSA.isEmpty()) {
                BabysitterAddress.setError("Please enter your address");
                BabysitterAddress.requestFocus();
            }
            if (BSC.isEmpty()) {
                BabysitterCity.setError("Please enter your city");
                BabysitterCity.requestFocus();
            }
            if (BSPR.isEmpty()) {
                BabysitterProvince.setError("Please enter your province");
                BabysitterProvince.requestFocus();
            }
            if (BSZIP.isEmpty()) {
                BabysitterZIP.setError("Please enter your ZIP code");
                BabysitterZIP.requestFocus();
            }
        } else {
            final ProgressDialog pd=ProgressDialog.show(this,"Register Babysitter","Registering...",true);
            refAuth.createUserWithEmailAndPassword(BSE, BSPwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    pd.dismiss();
                    if (task.isSuccessful()) {
                        userId = refAuth.getCurrentUser().getUid();
                        User userdb=new User(BSN,BSE,userId,BSPN,BSA,BSC,BSPR,BSZIP,false);
                        refBUsers.child(BSN).setValue(userdb);
                        Toast.makeText(BabysitterList.this, "Hello, welcome!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(BabysitterList.this, HomeBabysitter.class);
                        startActivity(i);
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException)
                            Toast.makeText(BabysitterList.this, "User with e-mail already exist!", Toast.LENGTH_LONG).show();
                        else {
                            Log.w("MainActivity", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(BabysitterList.this, "User create failed.",Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }
}
