package com.example.vamsi.login;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vamsi.login.models.Client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private Button registerBtn,gotoLoginBtn;

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private EditText regName,regPhone,regGmail,regPassword;
    //firebase
    private FirebaseAnalytics firebaseAnalytics; //for analytics
    private FirebaseFirestore dataBase;
    //firebase auth
    private FirebaseAuth mAuth;

    private String userId = "";
    public static final String MyPREFERENCES = "USER_PREFERENCE";
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        openHelper = new DatabaseHelper(this);
        //firebase analytics instance
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        dataBase = FirebaseFirestore.getInstance();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    //get data from register form
        registerBtn = findViewById(R.id.btnRegLogin);
        gotoLoginBtn = findViewById(R.id.btnGotoLogin);
        regName = findViewById(R.id.etRegName);
        regPhone = findViewById(R.id.etRegPhone);
        regGmail = findViewById(R.id.etRegGmail);
        regPassword = findViewById(R.id.etRegPassword);



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = regName.getText().toString().trim();
                String fPhone = regPhone.getText().toString().trim();
                String fGmail = regGmail.getText().toString().trim();
                String fPassword = regPassword.getText().toString().trim();
                //
                if (fname.isEmpty() || fPassword.isEmpty() || fGmail.isEmpty() || fPhone.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                } else {

                String returnId = register( fname,  fGmail,  fPhone,  fPassword);

                if(!returnId.equals("")){
                    registerAuth(fGmail, fPassword);
                    //set session
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("user_id", returnId);
                    editor.commit();
                    startActivity(new Intent(RegisterActivity.this,UserDashboard.class));
                    finish();
                }
            }
            }
        });

        gotoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //if user is signed in
        if(currentUser != null){
            startActivity(new Intent(this,UserDashboard.class)); //must redirect to dashboard
        }
    }

    /**
     *
     * @param email
     * @param password
     */
    private void registerAuth(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("USER_CREATED", "createUserWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("USER_NOT_CREATED", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     *
     * @param fname
     * @param fGmail
     * @param fPhone
     * @param fPassword
     * @return
     */
    private String register(String fname, String fGmail, String fPhone, String fPassword){
        Map<String, Object> client = new HashMap<>();
        client.put("fullName", fname);
        client.put("email", fGmail);
        client.put("cellnumber", fPhone);
        client.put("password", fPassword);

        dataBase.collection("Clients").add(client).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("FireStore", "DocumentSnapshot added with ID: " + documentReference.getId());
                userId = documentReference.getId();
            }
        });
        return userId;
    }


}
