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
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginParents extends AppCompatActivity {
    EditText emailParents, passwordParents;
    Button signIn;
    TextView signUpFirstParents;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_parents);
        emailParents = findViewById(R.id.loginEmailParents);
        passwordParents = findViewById(R.id.loginPasswordParents);
        signUpFirstParents = findViewById(R.id.signUpFirstParents);
        mAuth = FirebaseAuth.getInstance();
        signIn = findViewById(R.id.signInParents);
        mFirestore = FirebaseFirestore.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFirebaseUser = mAuth.getCurrentUser();

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mFirebaseUser != null){
                    Toast.makeText(LoginParents.this, "You're Logged In",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginParents.this,HomeParents.class);
                    startActivity(i);
                } else{
                    Toast.makeText(LoginParents.this, "Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailIdP = emailParents.getText().toString();
                String pwdP = passwordParents.getText().toString();

                if (emailIdP.isEmpty() || pwdP.isEmpty() || pwdP.length() < 8 || !(emailIdP.isEmpty()&&pwdP.isEmpty())){
                    if (emailIdP.isEmpty()) {
                        emailParents.setError("Please enter your email");
                        emailParents.requestFocus();
                    }
                    if (pwdP.isEmpty()) {
                        passwordParents.setError("Please enter your password");
                        passwordParents.requestFocus();
                    }
                    if (pwdP.length() < 8) {
                        passwordParents.setError("Password must be at least 8 characters long");
                        passwordParents.requestFocus();
                    }
                    if (!(emailIdP.isEmpty() && pwdP.isEmpty())){
                        mAuth.signInWithEmailAndPassword(emailIdP,pwdP).addOnCompleteListener(LoginParents.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(LoginParents.this, "Login error, please login again", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Intent intToHome = new Intent(LoginParents.this, HomeParents.class);
                                    startActivity(intToHome);
                                }
                            }
                        });
                    } else{
                        Toast.makeText(LoginParents.this,"Please login",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        signUpFirstParents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intToSignUpParents = new Intent(LoginParents.this, ParentsList.class);
                startActivity(intToSignUpParents);
            }
        });
    }
}
