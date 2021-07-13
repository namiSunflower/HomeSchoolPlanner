package com.example.homeschoolplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Sign In Screen";
    private EditText editTextEmail, editTextPassword;
    private String email, password,userId;
    private Boolean userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextEmail = (EditText) findViewById(R.id.userName);
        editTextPassword = (EditText) findViewById(R.id.passWord);
    }
    public void logIn(View view){
        email = editTextEmail.getText().toString();
        password= editTextPassword.getText().toString();
        if (email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        FirebaseAuth authenticateLogin = FirebaseAuth.getInstance();
        authenticateLogin.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    userId= task.getResult().getUser().getUid();
                    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference().child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            userType = snapshot.child("is_parent").getValue(Boolean.class);

                            //if usertype is parent, take user to parent dashboard
                            if(userType==true){
                                Intent parentDashboard = new Intent(MainActivity.this, ParentDashboard.class);
                                parentDashboard.putExtra("userId", userId);
                                startActivity(parentDashboard);
                            }
                            //if usertype is child, take user to child dashboard
                            if(userType==false){
                                String userName = (String) snapshot.child("name").getValue();

                                Intent childDashboard = new Intent(MainActivity.this, ChildProfile.class);
                                childDashboard.putExtra("childId", userId);
                                childDashboard.putExtra("childName", userName);
                                startActivity(childDashboard);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(),task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void signUp(View view){
        Intent signUpScreen = new Intent(MainActivity.this, SignUpScreen.class);
        startActivity(signUpScreen);
    }
}

