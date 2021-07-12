package com.example.homeschoolplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParentDashboard extends AppCompatActivity{
    private String userId;
    private FirebaseDatabase firebaseDatabase;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter childAdapter;
    private Toolbar toolbar;
    private RecyclerViewAdapter.SelectedChild listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dashboard);
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        Log.d("parentId", userId);
        ArrayList<String> cList = new ArrayList<>();
        List<ChildModel> childNames = new ArrayList<>();
        recyclerView =findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.toolbar);

        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
        DividerItemDecoration.VERTICAL));

        firebaseDatabase= FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child(userId).child("children").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child:snapshot.getChildren()){
                    String childId = (String) child.getValue();
                    firebaseDatabase.getReference().child(childId).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String childName = (String) snapshot.getValue();
                            cList.add(childName);
                             Log.d("NameChild", childName);
                            for (String n:cList){
                                if (n != childName){}
                                ChildModel childModel = new ChildModel(n);
                                childNames.add(childModel);
                                for(int i = 0; i < cList.size(); i++)
                                {
                                    Log.d("popeye", cList.get(i));
                                }
                            }
                             setOnClickListener();
                             childAdapter = new RecyclerViewAdapter(childNames,listener);
                             recyclerView.setAdapter(childAdapter);
                             cList.clear();
                        }

                        private void setOnClickListener() {
                             listener = new RecyclerViewAdapter.SelectedChild() {
                                 @Override
                                 public void onClick(View v, int position) {
                                     Intent intent = new Intent(getApplicationContext(), ChildProfile.class);
                                     intent.putExtra("childName", childNames.get(position).getChildName());
                                     startActivity(intent);
                                 }
                             };
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addChildScreen(View view){
        Intent intent = new Intent(ParentDashboard.this, AddChild.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    public void addAssignmentScreen(View view){
        Intent intent = new Intent(ParentDashboard.this, AddNewAssignment.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
}