package com.example.homeschoolplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ChildProfile extends AppCompatActivity {
    private TextView childProfileName;
    private String parentId, userId;
    private RecyclerViewAdapter tasks;
    private FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);
        childProfileName = (TextView) findViewById(R.id.childProfileName);
        firebaseDatabase= FirebaseDatabase.getInstance();

        String selectedChild = "Child name not set";
        Intent intent = getIntent();
        userId = intent.getStringExtra("childId");
        parentId = intent.getStringExtra("parentId");
        selectedChild = intent.getStringExtra("childName");
        Log.d("chicken", "go " + userId);

        childProfileName.setText("Hello! " +  selectedChild);
        updateDate();
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
    
    public void toDoList(){

    }

    public void addNewAssignment(View v){
        Intent assignmentScreen = new Intent(ChildProfile.this, AddNewAssignment.class);
        assignmentScreen.putExtra("childId", userId);
        assignmentScreen.putExtra("parentId", parentId);
        startActivity(assignmentScreen);
    }
}