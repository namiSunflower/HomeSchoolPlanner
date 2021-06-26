package com.example.homeschoolplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
        EditText et_password = (EditText) findViewById(R.id.passWord);
        EditText et_user_name = (EditText) findViewById(R.id.userName);
        String password = et_password.getText().toString();
        String user_name = et_user_name.getText().toString();
        Log.d(TAG, "Username is: " + user_name);
        Log.d(TAG, "Password is: " + password);


        DataBase db = new DataBase();
        User user = db.retrieveUserData(user_name, password);

        //Intent intent =  new Intent(this, ParentDashboard.class);
        Intent intent = user.is_parent ? new Intent(this, ParentDashboard.class) : new Intent(this, ChildrenList.class);
        intent.putExtra("User", user);
        startActivity(intent);
    }
}