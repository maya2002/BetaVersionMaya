package com.example.betaversionmaya2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import static com.example.betaversionmaya2.FBref.refAuth;


public class LoginBabysitter extends AppCompatActivity {
    EditText emailBabysitter, passwordBabysitter;
    String emailIdBS, pwdBS;
    CheckBox cBstayconnect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_babysitter);

        emailBabysitter = findViewById(R.id.loginEmailBabysitter);
        passwordBabysitter = findViewById(R.id.loginPasswordBabysitter);
        cBstayconnect=(CheckBox)findViewById(R.id.cBconnectview);

    }
    public void signInBabysitter(View view) {
        emailIdBS = emailBabysitter.getText().toString();
        pwdBS = passwordBabysitter.getText().toString();

        if (emailIdBS.isEmpty() || pwdBS.isEmpty()) {
            if (emailIdBS.isEmpty()) {
                emailBabysitter.setError("Please enter your email");
                emailBabysitter.requestFocus();
            } else {
                passwordBabysitter.setError("Please enter your password");
                passwordBabysitter.requestFocus();
            }
        } else {
            refAuth.signInWithEmailAndPassword(emailIdBS,pwdBS).addOnCompleteListener(LoginBabysitter.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(LoginBabysitter.this, "Login error, please login again", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                        SharedPreferences.Editor editor=settings.edit();
                        editor.putBoolean("stayConnect",cBstayconnect.isChecked());
                        editor.putBoolean("parents",false);
                        editor.commit();

                        Intent intToHome = new Intent(LoginBabysitter.this, HomeBabysitter.class);
                        startActivity(intToHome);
                    }
                }
            });
        }
    }

    public void goregBabysitter(View view) {
        Intent intToReg = new Intent(LoginBabysitter.this, BabysitterList.class);
        startActivity(intToReg);
    }
}
