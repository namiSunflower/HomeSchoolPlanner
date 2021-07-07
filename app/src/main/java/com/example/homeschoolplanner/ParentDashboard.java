package com.example.homeschoolplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ParentDashboard extends AppCompatActivity {
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
    }
    public void addChildScreen(View view){
        Intent intent = new Intent(ParentDashboard.this, AddChild.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
}