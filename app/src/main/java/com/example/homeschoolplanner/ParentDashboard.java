package com.example.homeschoolplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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
        //this will hold the child names
        ArrayList<String> cList = new ArrayList<>();
        //this will hold both the childId and child name
        //when displaying the data in the recyclerview (childid won't be shown)
        List<ChildModel> childNames = new ArrayList<>();
        //recyclerview list in xml file
        recyclerView =findViewById(R.id.recyclerView);
        //toolbar is the text that says "Children" on top of the screen
        toolbar = findViewById(R.id.toolbar);

        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle("");

        //this chunk of code is mostly for setting the layout and design
        //of how the items will appear in listview (still might need to improve design)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //if .VERTICAL then it allows vertical scrolling (might change it to
        //horizontal scrolling later on)
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
        DividerItemDecoration.VERTICAL));

        firebaseDatabase= FirebaseDatabase.getInstance();
        //looks for current user node then children node
        firebaseDatabase.getReference().child(userId).child("children").addListenerForSingleValueEvent(new ValueEventListener() {
            //loops through every child under children node to get child name value
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot child:snapshot.getChildren()){
                    String childId = (String) child.getValue();
                    firebaseDatabase.getReference().child(childId).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String childName = (String) snapshot.getValue();
                            cList.add(childName);
                            /** Just to test if it's showing child name value
                             Log.d("NameChild", childName);
                             */
                            for (String n:cList){
                                //ChildModel needs childId and childName as parameters
                                ChildModel childModel = new ChildModel(childId, n);
                                childNames.add(childModel);
                            }
                             setOnClickListener();
                             //android studio needs adapter when showing items in listviews,
                             //recyclerviews etc.
                             //recycler view takes the list of objects containing childId & name
                             //listener is taken in order to add click functionality when user selects
                             //childName in parent dashboard
                             childAdapter = new RecyclerViewAdapter(childNames,listener);
                             recyclerView.setAdapter(childAdapter);
                             //need to clear child name arraylist because it will keep the same
                             //data as before and keep adding the same children on top of the new ones
                             cList.clear();
                        }

                        //this will get selected child's name and id when user clicks on them
                        private void setOnClickListener() {
                             listener = new RecyclerViewAdapter.SelectedChild() {
                                 @Override
                                 public void onClick(View v, int position) {
                                     Intent intent = new Intent(getApplicationContext(), ChildProfile.class);
                                     intent.putExtra("childName", childNames.get(position).getChildName());
                                     intent.putExtra("childId", childNames.get(position).getChildId());
                                     intent.putExtra("parentId", userId);
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
    //when user clicks add children button
    public void addChildScreen(View view){
        Intent intent = new Intent(ParentDashboard.this, AddChild.class);
        intent.putExtra("userId", this.userId);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ParentDashboard.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

}
