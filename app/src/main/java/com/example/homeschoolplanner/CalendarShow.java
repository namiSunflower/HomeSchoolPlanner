package com.example.homeschoolplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CalendarView;

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
        intent = new Intent(CalendarShow.this, AddNewAssignment.class);
        intent.putExtra("childId", userId);
        intent.putExtra("parentId", parentId);

        setContentView(R.layout.activity_calendar_show);
        calendarView = (CalendarView)findViewById(R.id.calendarView);
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