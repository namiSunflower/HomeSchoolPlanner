//loop with children
//childprofile
package com.example.homeschoolplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChildProfile extends AppCompatActivity implements ChildProfileInterface {
    private TextView childProfileName;
    private String parentId, userId;
    private HomeworkAdapter hwkAdapter;
    private RecyclerView tasks;
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<Assignment> assignments;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);
        childProfileName = (TextView) findViewById(R.id.childProfileName);
        firebaseDatabase= FirebaseDatabase.getInstance();
        assignments = new ArrayList<>();
        tasks = findViewById(R.id.tasks);


        String selectedChild = "Child name not set";
        Intent intent = getIntent();
        userId = intent.getStringExtra("childId");
        parentId = intent.getStringExtra("parentId");
        selectedChild = intent.getStringExtra("childName");
        //Log.d("chicken", "go " + userId);
        populateList();
        tasks.setLayoutManager(new LinearLayoutManager(this));
        user = new User(userId);
        Intent editTransferIntent =  new Intent(ChildProfile.this, EditAssignment.class);
        hwkAdapter = new HomeworkAdapter(user, parentId, editTransferIntent, assignments, this);
        tasks.setAdapter(hwkAdapter);

        childProfileName.setText("Hello! " +  selectedChild);
        updateDate();
    }

    public void populateList(){
        firebaseDatabase.getReference().child(userId).child("assignments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                assignments.clear();
                ArrayList<HashMap<String, String>> hashAssignments = (ArrayList<HashMap<String, String>>) snapshot.getValue();
                //The server gives us the data as a hash map. This converts each assignment hashmap into an assignment class.
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
                    hwkAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateDate(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.now();
            String currentDate = datePattern.format(localDate);
            //Log.d("date", "Today is: " + currentDate);
            firebaseDatabase.getReference().child("date").setValue(currentDate);
            //firebaseDatabase.getReference().child(parentUserId).child("children").setValue(children);
        }
    }


    public void addNewAssignment(View v){
        Intent assignmentScreen = new Intent(ChildProfile.this, AddNewAssignment.class);
        assignmentScreen.putExtra("childId", userId);
        assignmentScreen.putExtra("parentId", parentId);
        startActivity(assignmentScreen);
    }



    @Override
    public void onLongItemClick(int position) {
        Toast.makeText(this, "hi", Toast.LENGTH_SHORT).show();
    }
}



