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

public class LoginParents extends AppCompatActivity {

    EditText emailParents, passwordParents;
    String emailIdP, pwdP;
    CheckBox cBstayconnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_parents);

        emailParents = findViewById(R.id.loginEmailParents);
        passwordParents = findViewById(R.id.loginPasswordParents);
        cBstayconnect=(CheckBox)findViewById(R.id.cBconnectview);
    }
    
    public void signInParents(View view) {
        emailIdP = emailParents.getText().toString();
        pwdP = passwordParents.getText().toString();

        if (emailIdP.isEmpty() || pwdP.isEmpty()) {
            if (emailIdP.isEmpty()) {
                emailParents.setError("Please enter your email");
                emailParents.requestFocus();
            } else {
                passwordParents.setError("Please enter your password");
                passwordParents.requestFocus();
            }
        } else {
            refAuth.signInWithEmailAndPassword(emailIdP,pwdP).addOnCompleteListener(LoginParents.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(LoginParents.this, "Login error, please login again", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences settings=getSharedPreferences("PREFS_NAME",MODE_PRIVATE);
                        SharedPreferences.Editor editor=settings.edit();
                        editor.putBoolean("stayConnect",cBstayconnect.isChecked());
                        editor.putBoolean("parents",false);
                        editor.commit();

                        Intent intToHome = new Intent(LoginParents.this, HomeParents.class);
                        startActivity(intToHome);
                    }
                }
            });
        }
    }

    public void goregParents(View view) {
        Intent intToReg = new Intent(LoginParents.this, ParentsList.class);
        startActivity(intToReg);
    }
}
