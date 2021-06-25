package com.example.homeschoolplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class SignUpScreen extends AppCompatActivity {
public final static String TAG = "Signup Screen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
    }

    public void setUpAccount(View view) {
        //Get User data
        boolean is_parent = true;
        EditText et_password = (EditText) findViewById(R.id.passWordSignup);
        EditText et_user_name = (EditText) findViewById(R.id.usernameSignup);
        String password = et_password.getText().toString();
        String user_name = et_user_name.getText().toString();
        Log.d(TAG, "Password is: " + password);
        Log.d(TAG, "Username is: " + user_name);

        //Get household data
        String household_name = null;
        ArrayList<String> class_list = null;


        DataBase db = new DataBase();
        String parent_id = db.createParent();
        String household_id = db.createHousehold();

        Household household = new Household(household_name, household_id, class_list);

        User new_user = new User(parent_id, is_parent, password, user_name, household_id,household, db);

        Intent intent = new Intent(this, ParentDashboard.class);
        intent.putExtra("User", new_user);
        startActivity(intent);

    }
}