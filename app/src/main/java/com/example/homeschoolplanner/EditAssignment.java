package com.example.homeschoolplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditAssignment extends AppCompatActivity {
    public static final String TAG = "Add New Assignment";

    private ImageView calendar;
    private EditText date;
    private User user;
    String stringDate;
    String parentId;
    String description;
    String title;
    String class_name;
    boolean repeating;

    int assignmentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);
        calendar = (ImageView) findViewById(R.id.calendar);
        date = (EditText) findViewById(R.id.date);
        //takes intent info from calendarshow class
        Intent intent = getIntent();
        stringDate = intent.getStringExtra("date");
        date.setText(stringDate);

        String userId = intent.getStringExtra("childId");
        user = (userId != null) ? new User(userId) : new User();
        parentId = intent.getStringExtra("parentId");
        assignmentIndex = intent.getIntExtra("assignmentIndex", 0);
        description = intent.getStringExtra("description");
        title = intent.getStringExtra("title");
        class_name = intent.getStringExtra("class_name");
        repeating = intent.getBooleanExtra("repeating", false);

        EditText e_description = findViewById(R.id.assignmentDescription);
        EditText e_title = findViewById(R.id.assignmentName);
        EditText e_class_name = findViewById(R.id.subjectName);
        CheckBox c_check_box = findViewById(R.id.willRepeat);

        e_description.setText(description);
        e_title.setText(title);
        e_class_name.setText(class_name);
        c_check_box.setActivated(repeating);

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditAssignment.this, CalendarShow.class);
                //user id is passed to CalendarShow class
                intent.putExtra("userId", userId);
                intent.putExtra("parentId", parentId);

                EditText e_description = (EditText) findViewById(R.id.assignmentDescription);
                EditText e_title = (EditText) findViewById(R.id.assignmentName);
                EditText e_class_name = (EditText) findViewById(R.id.subjectName);


                String dueDate = stringDate;
                String description = e_description.getText().toString();
                String title = e_title.getText().toString();
                String class_name = e_class_name.getText().toString();
                boolean repeating = ((CheckBox) findViewById(R.id.willRepeat)).isChecked();

                intent.putExtra("description", description);
                intent.putExtra("title", title);
                intent.putExtra("class_name", class_name);
                intent.putExtra("repeating", repeating);
                intent.putExtra("cameFromEditAssignment", true);
                intent.putExtra("assignmentIndex", assignmentIndex);

                startActivity(intent);
            }
        });
    }

    public void addAssignment(View view) {
        EditText e_description = (EditText) findViewById(R.id.assignmentDescription);
        EditText e_title = (EditText) findViewById(R.id.assignmentName);
        EditText e_class_name = (EditText) findViewById(R.id.subjectName);


        String dueDate = stringDate;
        String description = e_description.getText().toString();
        String title = e_title.getText().toString();
        String class_name = e_class_name.getText().toString();
        boolean repeating = ((CheckBox) findViewById(R.id.willRepeat)).isChecked();


        if (dueDate != null && description != null && title != null && class_name != null) {

            Assignment new_assignment = new Assignment(dueDate, description, title, class_name, false, false, repeating, user.userId);

            user.assignments.set(assignmentIndex, new_assignment);

            user.saveAssignments();

            if (parentId != null) {
                Intent parentDashboard = new Intent(EditAssignment.this, ParentDashboard.class);
                parentDashboard.putExtra("userId", parentId);
                startActivity(parentDashboard);
            }
            //if usertype is child, take user to child dashboard
            if (parentId == null) {
                Intent childDashboard = new Intent(EditAssignment.this, ChildProfile.class);
                childDashboard.putExtra("childId", user.userId);
                childDashboard.putExtra("childName", user.name);
                startActivity(childDashboard);
            }

        }
        else {
            Toast.makeText(this, "Please Fill out all data", Toast.LENGTH_SHORT).show();
        }

    }
}