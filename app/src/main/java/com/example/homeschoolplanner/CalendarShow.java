package com.example.homeschoolplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarShow extends AppCompatActivity {
    private CalendarView calendarView;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Takes userId from ChildProfile class
        Intent idIntent = getIntent();
        String userId = idIntent.getStringExtra("userId");
        String parentId = idIntent.getStringExtra("parentId");
        String description = idIntent.getStringExtra("description");
        String title = idIntent.getStringExtra("title");
        String class_name = idIntent.getStringExtra("class_name");
        int assignmentIndex = idIntent.getIntExtra("assignmentIndex", 0);
        boolean repeating = idIntent.getBooleanExtra("repeating", false);
        boolean cameFromEditAssignment = idIntent.getBooleanExtra("cameFromEditAssignment", false);

        intent = (cameFromEditAssignment) ? new Intent(CalendarShow.this, EditAssignment.class) : new Intent(CalendarShow.this, AddNewAssignment.class);
        intent.putExtra("childId", userId);
        intent.putExtra("parentId", parentId);
        intent.putExtra("description", description);
        intent.putExtra("title", title);
        intent.putExtra("class_name", class_name);
        intent.putExtra("repeating", repeating);
        intent.putExtra("assignmentIndex", assignmentIndex);

        setContentView(R.layout.activity_calendar_show);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        //shows calendarview screen
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //might edit date format later
                String fullDate = dayOfMonth + "/" + month + "/" + year;
                intent.putExtra("date", fullDate);
                startActivity(intent);
            }
        });
    }
}
