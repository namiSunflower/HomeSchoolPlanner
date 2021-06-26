package com.example.homeschoolplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AddChild extends AppCompatActivity {
Dialog dialog;
ImageView exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);
    }
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
    public void viewSubjects(View view){
        Intent intent = new Intent(this, SubjectsList.class);
        startActivity(intent);
    }
    public void parentDashboardScreen(View view){
        Intent intent = new Intent(this, ParentDashboard.class);
        startActivity(intent);
    }
}