package com.example.homeschoolplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Patterns;
import android.widget.ImageView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class AddChild extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private String email, password, userId;
    private FirebaseDatabase firebaseDatabase;
    private ImageView exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        exit = (ImageView)findViewById(R.id.exit);
        editTextEmail = (EditText)findViewById(R.id.childUsername);
        editTextPassword = (EditText)findViewById(R.id.childPassword);
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
    //When user clicks submit
    public void newChild(View view){
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
                    User user = new User(userId,false,password,email);

                    firebaseDatabase.getReference().child("Users").child(userId).setValue(user);
                    Toast.makeText(AddChild.this, "User successfully created!", Toast.LENGTH_LONG).show();

                    Intent parentDashboard = new Intent(AddChild.this,ParentDashboard.class);
                    startActivity(parentDashboard);
                }
                else{
                    Toast.makeText(getApplicationContext(),task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //When user clicks add subject button
    public void popupSubject(View view){
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.activity_add_child_popup);
        exit=(ImageView) dialog.findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    //When user clicks view subjects button
    public void viewSubjects(View v){
        Intent subjectList = new Intent(AddChild.this,SubjectsList.class);
        startActivity(subjectList);
    }
}






