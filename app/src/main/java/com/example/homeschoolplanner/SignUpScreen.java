package com.example.homeschoolplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class SignUpScreen extends AppCompatActivity implements View.OnClickListener {
    public final static String TAG = "Signup Screen";
    private  Button submitSignup;
    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        submitSignup = (Button)findViewById(R.id.submitSignup);
        submitSignup.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.emailSignup);
        editTextPassword = (EditText) findViewById(R.id.passWordSignup);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.submitSignup:
                setUpAccount();
                break;
        }
    }

    public void setUpAccount() {
        String username = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(username.isEmpty()){
            editTextEmail.setError("Username is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            editTextEmail.setError("Username must be a Valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6) {
            editTextPassword.setError("Min Password Length is should be 6 Characters!");
            editTextPassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(username,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User("b", true, password, username, null);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpScreen.this,"User Has been Registered Successfully!",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(SignUpScreen.this,"Failed to Register User!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }

                    }
                });



    }

}