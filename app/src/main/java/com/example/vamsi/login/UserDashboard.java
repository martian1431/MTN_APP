package com.example.vamsi.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserDashboard extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
        //
        mAuth = FirebaseAuth.getInstance();
         sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String returnId = (sharedpreferences.getString("user_id", ""));
        Toast.makeText(UserDashboard.this, "Wrong username or password.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        System.out.println("check if user is logged in" + currentUser);
        //
        if(currentUser == null){
            startActivity(new Intent(UserDashboard.this,MainActivity.class));
            finish();
        }
    }
}