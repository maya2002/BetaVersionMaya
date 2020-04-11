package com.example.betaversionmaya2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginBabysitter extends AppCompatActivity {
    EditText emailBabysitter, passwordBabysitter;
    Button signIn;
    TextView signUpFirstBabysitter;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_babysitter);
        emailBabysitter = findViewById(R.id.loginEmailBabysitter);
        passwordBabysitter = findViewById(R.id.loginPasswordBabysitter);
        signUpFirstBabysitter = findViewById(R.id.signUpFirstBabysitter);
        mAuth = FirebaseAuth.getInstance();
        signIn = findViewById(R.id.signInBabysitter);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFirebaseUser = mAuth.getCurrentUser();

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mFirebaseUser != null){
                    Toast.makeText(LoginBabysitter.this, "You're Logged In",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginBabysitter.this,HomeBabysitter.class);
                    startActivity(i);
                } else{
                    Toast.makeText(LoginBabysitter.this, "Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailIdPylr = emailBabysitter.getText().toString();
                String pwdPylr = passwordBabysitter.getText().toString();

                if (emailIdPylr.isEmpty() || pwdPylr.isEmpty() || pwdPylr.length() < 8 || !(emailIdPylr.isEmpty()&&pwdPylr.isEmpty())){
                    if (emailIdPylr.isEmpty()) {
                        emailBabysitter.setError("Please enter your email");
                        emailBabysitter.requestFocus();
                    }
                    if (pwdPylr.isEmpty()) {
                        passwordBabysitter.setError("Please enter your password");
                        passwordBabysitter.requestFocus();
                    }
                    if (pwdPylr.length() < 8) {
                        passwordBabysitter.setError("Password must be at least 8 characters");
                        passwordBabysitter.requestFocus();
                    }
                    if (!(emailIdPylr.isEmpty() && pwdPylr.isEmpty())){
                        mAuth.signInWithEmailAndPassword(emailIdPylr,pwdPylr).addOnCompleteListener(LoginBabysitter.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(LoginBabysitter.this, "Login error, please login again", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Intent intToHome = new Intent(LoginBabysitter.this, HomeBabysitter.class);
                                    startActivity(intToHome);
                                }
                            }
                        });
                    } else{
                        Toast.makeText(LoginBabysitter.this,"Please login",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        signUpFirstBabysitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToSignUpBabysitter = new Intent(LoginBabysitter.this, BabysitterList.class);
                startActivity(intToSignUpBabysitter);
            }
        });
    }
}

