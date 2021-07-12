package com.example.homeschoolplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ChildProfile extends AppCompatActivity {
    private TextView childProfileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);
        childProfileName = (TextView) findViewById(R.id.childProfileName);

        String selectedChild = "Child name not set";
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            selectedChild = extras.getString("childName");
        }
        childProfileName.setText("Hello! " +  selectedChild);
    }
    public void addNewAssignment(View v){
        Intent assignmentScreen = new Intent(ChildProfile.this, AddNewAssignment.class);
        startActivity(assignmentScreen);
    }
}