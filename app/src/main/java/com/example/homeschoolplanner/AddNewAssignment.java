package com.example.homeschoolplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddNewAssignment extends AppCompatActivity {
    private ImageView calendar;
    private EditText date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_assignment);
        calendar = (ImageView)findViewById(R.id.calendar);
        date = (EditText)findViewById(R.id.date);

        Intent dateIntent = getIntent();
        String stringDate = dateIntent.getStringExtra("date");
        date.setText(stringDate);

        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddNewAssignment.this, CalendarShow.class);
                startActivity(intent);
            }
        });
    }
}