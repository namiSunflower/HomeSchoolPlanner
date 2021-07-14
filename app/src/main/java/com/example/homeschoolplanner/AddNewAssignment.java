package com.example.homeschoolplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AddNewAssignment extends AppCompatActivity {
    public static final String TAG = "Add New Assignment";

    private ImageView calendar;
    private EditText date;
    private User user;
    String stringDate;
    String parentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_assignment);
        calendar = (ImageView)findViewById(R.id.calendar);
        date = (EditText)findViewById(R.id.date);
        //takes intent info from calendarshow class
        Intent intent = getIntent();
        stringDate = intent.getStringExtra("date");
        stringDate = (stringDate == null) ? "16-07-2021" : stringDate;
        date.setText(stringDate);

        String userId = intent.getStringExtra("childId");
        user = (userId != null) ? new User(userId) : new User();
        parentId = intent.getStringExtra("parentId");

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewAssignment.this, CalendarShow.class);
                //user id is passed to CalendarShow class
                intent.putExtra("userId", userId);
                intent.putExtra("parentId", parentId);
                startActivity(intent);
            }
        });
    }

    public void addAssignment(View view) {
        EditText e_description = (EditText)findViewById(R.id.assignmentDescription);
        EditText e_title = (EditText)findViewById(R.id.assignmentName);
        EditText e_class_name = (EditText)findViewById(R.id.subjectName);

        String dueDate = stringDate;
        String description = e_description.getText().toString();
        String title = e_title.getText().toString();
        String class_name = e_class_name.getText().toString();


        if (dueDate != null && description != null && title != null && class_name!= null ) {

                Assignment new_assignment = new Assignment(dueDate, description, title, class_name, false, false, user.userId);

                user.addAssignment(new_assignment);
                user.saveAssignments();

                if(parentId != null){
                    Intent parentDashboard = new Intent(AddNewAssignment.this, ParentDashboard.class);
                    parentDashboard.putExtra("userId", parentId);
                    startActivity(parentDashboard);
                }
                //if usertype is child, take user to child dashboard
                if(parentId == null){
                    Intent childDashboard = new Intent(AddNewAssignment.this, ChildProfile.class);
                    childDashboard.putExtra("childId", user.userId);
                    childDashboard.putExtra("childName", user.name);
                    startActivity(childDashboard);
                }

        } else {
            Toast.makeText(this, "Please Fill out all data", Toast.LENGTH_SHORT);
        }

    }
}