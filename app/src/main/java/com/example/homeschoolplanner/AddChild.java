package com.example.homeschoolplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;


public class AddChild extends AppCompatActivity {
    Dialog dialog;
    ImageView exit;
    private User child;
    private EditText childUsername, childPassword;
    private FirebaseAuth cAuth;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
        cAuth = FirebaseAuth.getInstance();

        childUsername = (EditText)findViewById(R.id.childUsername);
        childPassword = (EditText)findViewById(R.id.childPassword);


    }
    public void newChild(View v) {
        setUpChildAccount();
    }

    public void popupSubject(View v){
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
    public void viewSubjects(View v){
        Intent intent = new Intent(this, SubjectsList.class);
        startActivity(intent);
    }
    /**
     public void newChild(View v){
     setUpChildAccount();
     //Intent intent = new Intent(this, ParentDashboard.class);
     //startActivity(intent);
     }
     */

    public void setUpChildAccount(){
        username = childUsername.getText().toString().trim();
        password = childPassword.getText().toString().trim();
        if(username.isEmpty()){
            childUsername.setError("Username is required!");
            childUsername.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            childUsername.setError("Username must be a Valid email!");
            childUsername.requestFocus();
            return;
        }
        if(password.isEmpty()){
            childPassword.setError("Password is required!");
            childPassword.requestFocus();
            return;
        }
        if(password.length()<6) {
            childPassword.setError("Min Password Length is should be 6 Characters!");
            childPassword.requestFocus();
            return;
        }
        cAuth.createUserWithEmailAndPassword(username,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User child = new User(null, false,password,username,null);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(child).addOnCompleteListener(new OnCompleteListener<Void>()
                            {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(AddChild.this,"User Has been Registered Successfully!",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(AddChild.this,"Failed to Register User!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(child).addOnCompleteListener(new OnCompleteListener<Void>()
                            {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(AddChild.this,"User Has been Registered Successfully!",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(AddChild.this,"Failed to Register User!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }

                    }
                });
    }
}

