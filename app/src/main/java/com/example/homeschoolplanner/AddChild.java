package com.example.homeschoolplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.util.Patterns;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

public class AddChild extends AppCompatActivity {
    public static final String TAG = "AddChild Screen";
    private EditText editTextEmail, editTextPassword, editTextName;
    private String email, password, userId, parentUserId, userName;
    private FirebaseDatabase firebaseDatabase;
    private ImageView exit;
    FirebaseAuth authenticateSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        exit = (ImageView) findViewById(R.id.exit);
        editTextEmail = (EditText) findViewById(R.id.childUsername);
        editTextPassword = (EditText) findViewById(R.id.childPassword);
        editTextName = (EditText) findViewById(R.id.childName);
        firebaseDatabase = FirebaseDatabase.getInstance();
        Intent intent = getIntent();
        parentUserId = (parentUserId != null) ? parentUserId : intent.getStringExtra("userId");
    }

    //When user clicks submit
    public void newChild(View view) {
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();
        userName = editTextName.getText().toString();

        authenticateSignup = null;
        if (userName.isEmpty()) {
            editTextName.setError("Name is required!");
            editTextName.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please Provide Valid email!");
            editTextEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            editTextPassword.setError("Min Password Length is should be 6 Characters!");
            editTextPassword.requestFocus();
            return;
        }


        authenticateSignup = FirebaseAuth.getInstance();
        authenticateSignup.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    userId = task.getResult().getUser().getUid();
                    User user = new User(userId, false, password, email, userName);

                    firebaseDatabase.getReference().child(userId).setValue(user);
                    firebaseDatabase.getReference().child(parentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Get the current database version of the children list.
                            ArrayList<String> db_children = (ArrayList<String>) snapshot.child("children").getValue();
                            ArrayList<String> children = (db_children != null) ? db_children : new ArrayList();
                            //Add the new child id to the DataBase
                            children.add(userId);
                            firebaseDatabase.getReference().child(parentUserId).child("children").setValue(children);
                            String parentEmail = (String) snapshot.child("email").getValue();
                            String parentPassword = (String) snapshot.child("password").getValue();
                            authenticateSignup.signOut();

///*
                            authenticateSignup.signInWithEmailAndPassword(parentEmail, parentPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "Parent resigned in");


                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "Parent resign in failed", task.getException());
                                        Toast.makeText(AddChild.this, "Please sign in again.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent main = new Intent(AddChild.this, MainActivity.class);
                                        main.putExtra("userId", parentUserId);
                                        startActivity(main);

                                    }
                                }
                            });//*/
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    Toast.makeText(AddChild.this, "User successfully created!", Toast.LENGTH_LONG).show();

                    Intent parentDashboard = new Intent(AddChild.this, ParentDashboard.class);
                    parentDashboard.putExtra("userId", parentUserId);
                    startActivity(parentDashboard);
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //When user clicks add subject button
    public void popupSubject(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_add_child_popup);
        exit = (ImageView) dialog.findViewById(R.id.exit);
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
    public void viewSubjects(View v) {
        Intent subjectList = new Intent(AddChild.this, SubjectsList.class);
        startActivity(subjectList);
    }
}






