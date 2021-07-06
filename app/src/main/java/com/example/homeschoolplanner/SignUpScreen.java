package com.example.homeschoolplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class SignUpScreen extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private String email, password, userId;
    private FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        editTextEmail = (EditText)findViewById(R.id.emailSignup);
        editTextPassword = (EditText)findViewById(R.id.passWordSignup);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
    public void signUp(View view){
        email = editTextEmail.getText().toString();
        password= editTextPassword.getText().toString();
        if (email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please Provide Valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6) {
            editTextPassword.setError("Min Password Length is should be 6 Characters!");
            editTextPassword.requestFocus();
            return;
        }

        FirebaseAuth authenticateSignup = FirebaseAuth.getInstance();
        authenticateSignup.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    userId = task.getResult().getUser().getUid();
                    User user = new User(userId,true,password,email);

                    firebaseDatabase.getReference().child("Users").child(userId).setValue(user);
                    Toast.makeText(SignUpScreen.this, "User successfully created!", Toast.LENGTH_LONG).show();

                    //Takes you back to homepage screen
                    Intent signUpScreen = new Intent(SignUpScreen.this, MainActivity.class);
                    startActivity(signUpScreen);
                }
                else{
                    Toast.makeText(getApplicationContext(),task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}






