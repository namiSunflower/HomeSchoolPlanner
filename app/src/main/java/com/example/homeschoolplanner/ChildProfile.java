package com.example.homeschoolplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChildProfile extends AppCompatActivity {
    private TextView childProfileName;
    private String parentId, userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);
        childProfileName = (TextView) findViewById(R.id.childProfileName);

        String selectedChild = "Child name not set";
        Intent intent = getIntent();
        userId = intent.getStringExtra("childId");
        parentId = intent.getStringExtra("parentId");
        selectedChild = intent.getStringExtra("childName");
        Log.d("chicken", "go " + userId);

        childProfileName.setText("Hello! " +  selectedChild);
    }
    public void addNewAssignment(View v){
        Intent assignmentScreen = new Intent(ChildProfile.this, AddNewAssignment.class);
        assignmentScreen.putExtra("childId", userId);
        assignmentScreen.putExtra("parentId", parentId);
        startActivity(assignmentScreen);
    }
}