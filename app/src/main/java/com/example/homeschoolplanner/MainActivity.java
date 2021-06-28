package com.example.homeschoolplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = "Sign In Screen";
    private EditText editTextEmail, editTextPassword;
    private Button signIn;

    private TextView singUp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        singUp = findViewById(R.id.signUp);
        singUp.setOnClickListener(this);

        signIn = findViewById(R.id.logIn);
        signIn.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.userName);
        editTextPassword = (EditText) findViewById(R.id.passWord);

        mAuth = FirebaseAuth.getInstance();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUp:
                startActivity(new Intent(this, SignUpScreen.class));
                break;
            case R.id.logIn:
                userLogin();
                break;

        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6) {
            editTextPassword.setError("Min Password Length is should be 6 Characters!");
            editTextPassword.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Full name is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please Provide Valid email!");
            editTextEmail.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // redirect to user profile
                    startActivity(new Intent(MainActivity.this, ParentDashboard.class));
                }else{
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your crendentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    /*
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
    }*/
    }