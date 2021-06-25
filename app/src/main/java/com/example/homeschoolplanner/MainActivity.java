package com.example.homeschoolplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "Sign In Screen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toSignUpScreen(View view) {
        Intent intent = new Intent(this, SignUpScreen.class);
        startActivity(intent);
    }

    public void signIn(View view) {
        String user_name = findViewById(R.id.usernameSignup).toString();
        String password = findViewById(R.id.passWordSignup).toString();
        Log.d(TAG, "Username is: " + user_name);
        Log.d(TAG, "Password is: " + password);


        DataBase db = new DataBase();
        User user = db.retrieveUserData(user_name, password);

        Intent intent = user.is_parent ? new Intent(this, ParentDashboard.class) : new Intent(this, ChildrenList.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }
}