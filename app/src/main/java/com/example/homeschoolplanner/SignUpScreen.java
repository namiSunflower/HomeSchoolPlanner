package com.example.homeschoolplanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class SignUpScreen extends AppCompatActivity implements View.OnClickListener {
    public final static String TAG = "Signup Screen";
    private  Button submitSignup;
    private EditText editTextFullname, editTextPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        submitSignup = (Button)findViewById(R.id.submitSignup);
        submitSignup.setOnClickListener(this);

        editTextFullname = (EditText) findViewById(R.id.usernameSignup);
        editTextPassword = (EditText) findViewById(R.id.passWordSignup);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.submitSignup:
                setUpAccount();
                break;
        }
    }

    public void setUpAccount() {
        String username = editTextFullname.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(username.isEmpty()){
            editTextFullname.setError("Username is required!");
            editTextFullname.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            editTextFullname.setError("Username must be a Valid email!");
            editTextFullname.requestFocus();
            return;
        }
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
        mAuth.createUserWithEmailAndPassword(username,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User2 user = new User2(username,password);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpScreen.this,"User Has been Registered Successfully!",Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(SignUpScreen.this,"Failed to Register User!",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }

                    }
                });


       /* //Get User data
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
        startActivity(intent);*/

    }

}