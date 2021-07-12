package com.example.homeschoolplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class AddChildPopup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child_popup);
    }
}
/**
confirmSubject.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        String subject = editTextSubject.getText().toString();
        firebaseDatabase.getReference().child(parentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
@Override
public void onDataChange(@NonNull DataSnapshot snapshot) {
        ArrayList<String> db_subjects = (ArrayList<String>) snapshot.child("subjects").getValue();
        ArrayList<String> subjects = (db_subjects != null) ? db_subjects : new ArrayList();
        firebaseDatabase.getReference().child(parentUserId).child("subjects").addListenerForSingleValueEvent(new ValueEventListener() {
@Override
public void onDataChange(@NonNull DataSnapshot snapshot) {
        for (DataSnapshot subjectItem:snapshot.getChildren()){
        String itemValue = (String)subjectItem.getValue();
        if (subject != itemValue){
        subjects.add(subject);
        firebaseDatabase.getReference().child(parentUserId).child("subjects").setValue(subjects);
        }
        else{
        editTextSubject.setError(subject + "is already in subject list");
        editTextPassword.requestFocus();
        return;
        }
        }
        }

@Override
public void onCancelled(@NonNull DatabaseError error) {

        }
        });
        }

@Override
public void onCancelled(@NonNull DatabaseError error) {

        }
        });
        }
        });
 */