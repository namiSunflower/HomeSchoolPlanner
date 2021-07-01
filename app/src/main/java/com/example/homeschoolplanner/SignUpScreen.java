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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class SignUpScreen extends AppCompatActivity implements View.OnClickListener {
    public final static String TAG = "Signup Screen";
    private  Button showUser;
    private EditText editTextEmail, editTextPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference reff;
    private DatabaseReference DatbaseOfUsers;
    private FirebaseDatabase db =FirebaseDatabase.getInstance();
    final private String Test = "Test Directory";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = db.getReference();
        editTextEmail = (EditText) findViewById(R.id.emailSignup);
        editTextPassword = (EditText) findViewById(R.id.passWordSignup);
        showUser = (Button) findViewById(R.id.showUser);
        showUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                User user = new User(null, true, password, email, null);
                mDatabase.child("Users").push().setValue(user);
 }
 });

 }

 @Override
 public void onClick(View v) {
 }
 }






