package com.example.homeschoolplanner;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class User implements Serializable {
    public static final String TAG = "User Class";

    public String userId;
    public String email;
    public boolean is_parent;
    public String password;
    public String name;
    public List<String> children;
    public ArrayList<Assignment> assignments;


    User() {
        this.userId = null;
        this.is_parent = true;
        this.password = null;
        this.name = null;
    }

    User(String userId, boolean is_parent, String password, String email, String name) {
        this.userId = userId;
        this.is_parent = is_parent;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    String getUserId() {
        return userId;
    }

    Boolean getIsParent() {
        return is_parent;
    }

    String getPassword() {
        return password;
    }

    //String getEmail() {  return email;  }

    void setPassword(String new_password) {
        password = new_password;
    }

    String getUserName() {
        return name;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }

    public void addAssignment(Assignment assignment) {
        this.assignments = (this.assignments == null) ? new ArrayList<Assignment>() : this.assignments;
        this.assignments.add(assignment);
    }

    public void loadUserDataFromServer() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child(this.userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userId = (String) snapshot.child("userId").getValue();
                email = (String) snapshot.child("email").getValue();
                is_parent = (boolean) snapshot.child("is_parent").getValue();
                password = (String) snapshot.child("password").getValue();
                name = (String) snapshot.child("name").getValue();
                children = (List<String>) snapshot.child("children").getValue();
                assignments = (ArrayList<Assignment>) snapshot.child("assignments").getValue();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Uh Oh");
            }

        });
    }


    public void saveAssignments() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child(this.userId).child("assignments").setValue(this.assignments);
    }
}
