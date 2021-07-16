package com.example.homeschoolplanner;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    User(String userId) {
        this.userId = userId;
        loadUserDataFromServer();
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
                email = (String) snapshot.child("email").getValue();
                is_parent = (boolean) snapshot.child("is_parent").getValue();
                password = (String) snapshot.child("password").getValue();
                name = (String) snapshot.child("name").getValue();
                children = (List<String>) snapshot.child("children").getValue();
                ArrayList<HashMap<String, String>> hashAssignments = (ArrayList<HashMap<String, String>>) snapshot.child("assignments").getValue();
                //The server gives us the data as a hash map. This converts each assignment hashmap into an assignment class.
                assignments = new ArrayList<Assignment>();
                hashAssignments = (hashAssignments == null) ? new ArrayList<HashMap<String, String>>() : hashAssignments;

                for (HashMap<String, String> hash : hashAssignments) {

                    String due_date = (hash.containsKey("due_date")) ? hash.get("due_date") : null;
                    String description = (hash.containsKey("description")) ? hash.get("description") : null;
                    String title = (hash.containsKey("title")) ? hash.get("title") : null;
                    String class_name = (hash.containsKey("class_name")) ? hash.get("class_name") : null;
                    Object marked_complete = false;
                    Object confirmed_complete = false;
                    Object repeating = false;
                    if (hash.containsKey("marked_complete")) {
                        marked_complete = hash.get("marked_complete");
                    }
                    if (hash.containsKey("confirmed_complete")) {
                        confirmed_complete = hash.get("confirmed_complete");
                    }
                    if (hash.containsKey("repeating")) {
                        repeating = hash.get("repeating");
                    }
                    String owner = (hash.containsKey("owner")) ? hash.get("owner") : null;

                    Assignment new_assignment = new Assignment(due_date, description, title, class_name, (Boolean) marked_complete, (Boolean) confirmed_complete, (Boolean) repeating, owner);


                    assignments.add(new_assignment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, "Uh Oh");
            }

        });
    }


    public void markAssignmentComplete(int index, boolean is_parent) {
        Assignment temp = this.assignments.get(index);
        temp.setMarked_complete(true);
        temp.setConfirmed_complete(is_parent);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        if (is_parent) {
            this.assignments.remove(index);
        }
        if (is_parent && temp.isRepeating()) {
            String newDueDate = "31/12/2022";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate now = LocalDate.now();
                LocalDate next_week = now.minusDays(-7);
                newDueDate = datePattern.format(next_week);
            }

            Assignment new_assignment = temp.clone();
            new_assignment.setMarked_complete(false);
            new_assignment.setConfirmed_complete(false);
            new_assignment.setDue_date(newDueDate);
            this.assignments.add(new_assignment);
        }
        saveAssignments();
    }

    public void saveAssignments() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child(this.userId).child("assignments").setValue(this.assignments);
    }
}
