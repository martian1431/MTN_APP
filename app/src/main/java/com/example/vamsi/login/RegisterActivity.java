package com.example.vamsi.login;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vamsi.login.models.Client;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.analytics.FirebaseAnalytics;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        openHelper = new DatabaseHelper(this);
        //firebase analytics instance
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        dataBase = FirebaseFirestore.getInstance();


        registerBtn = findViewById(R.id.btnRegLogin);
        gotoLoginBtn = findViewById(R.id.btnGotoLogin);
        regName = findViewById(R.id.etRegName);
        regPhone = findViewById(R.id.etRegPhone);
        regGmail = findViewById(R.id.etRegGmail);
        regPassword = findViewById(R.id.etRegPassword);

        register();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = openHelper.getWritableDatabase();
                String fname = regName.getText().toString().trim();
                String fPhone = regPhone.getText().toString().trim();
                String fGmail = regGmail.getText().toString().trim();
                String fPassword = regPassword.getText().toString().trim();
                if (fname.isEmpty() || fPassword.isEmpty() || fGmail.isEmpty() || fPhone.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                } else {
                insertData(fname,fPhone,fGmail,fPassword);
                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
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

    private void register(){
        Map<String, Object> client = new HashMap<>();
        client.put("fullName", "Velile Vamba");
        client.put("username", "Velly");
        client.put("email", "velly.vamba@gmail.com");
        client.put("cellnumber", "0783420562");

        dataBase.collection("Clients").add(client).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("FireStore", "DocumentSnapshot added with ID: " + documentReference.getId());
            }
        });
    }
    public void insertData(String fname,String fPhone,String fGmail,String fPassword){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_2,fname);
        contentValues.put(DatabaseHelper.COL_3,fPhone);
        contentValues.put(DatabaseHelper.COL_4,fGmail);
        contentValues.put(DatabaseHelper.COL_5,fPassword);

        long id = db.insert(DatabaseHelper.TABLE_NAME,null,contentValues);
    }
}
