package com.example.vamsi.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.MultiFactorResolver;


public class MainActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvRegister;
    private EditText etLoginGmail, etLoginPassword;
//    private Button loginButton;
//
//    private SQLiteDatabase db;
//    private SQLiteOpenHelper openHelper;
//    private Cursor cursor;

    private static final String TAG = "EmailPassword";
//    private ActivityEmailpasswordBinding mBinding;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

//        openHelper = new DatabaseHelper(this);
//        db = openHelper.getReadableDatabase();
        tvRegister = findViewById(R.id.tvRegister);
        etLoginGmail = findViewById(R.id.email);
        etLoginPassword = findViewById(R.id.password);
//        loginButton = findViewById(R.id.btnLogin);


//        loginButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = etLoginGmail.getText().toString().trim();
//                String password = etLoginPassword.getText().toString().trim();
//                if (email.isEmpty() || password.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Enter your Email and Password to login", Toast.LENGTH_SHORT).show();
//                } else {
//                    cursor = db.rawQuery("SELECT *FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_4 + "=? AND " + DatabaseHelper.COL_5 + "=?", new String[]{email, password});
//                    if (cursor != null) {
//                        if (cursor.getCount() > 0) {
//                            startActivity(new Intent(MainActivity.this, LoginSucess.class));
//                            Toast.makeText(getApplicationContext(), "Login sucess", Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Login error", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            }
//        });


//
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
                finish();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        System.out.println("check if user is logged in" + currentUser);
    }

    @Override
    public void onClick(View view) {
        int index = view.getId();
        if (index == R.id.loginButton) {
            System.out.println("login button clicked");
            signIn(etLoginGmail.getText().toString(), etLoginPassword.getText().toString());
        }
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "email:" + email);
        Log.d(TAG, "password:" + password);
        if (!validateForm()) {
            return;
        }

        showProgressBar();

        // [START sign_in_with_email]
//        FIXME fails even though user exists in the database
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
//                    TODO go to dashboard
//                    System.out.println("debug: " + user);
//                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(MainActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
//                    updateUI(null);
                    // [START_EXCLUDE]
                    checkForMultiFactorFailure(task.getException());
                    // [END_EXCLUDE]
                }

                // [START_EXCLUDE]
                if (!task.isSuccessful()) {
                    System.out.println("debug");
//                    mBinding.status.setText(R.string.auth_failed);
                }
                hideProgressBar();
                // [END_EXCLUDE]
            }
        });
    }

    private void checkForMultiFactorFailure(Exception e) {
        // Multi-factor authentication with SMS is currently only available for
        // Google Cloud Identity Platform projects. For more information:
        // https://cloud.google.com/identity-platform/docs/android/mfa
        if (e instanceof FirebaseAuthMultiFactorException) {
            Log.w(TAG, "multiFactorFailure", e);
            Intent intent = new Intent();
            MultiFactorResolver resolver = ((FirebaseAuthMultiFactorException) e).getResolver();
            intent.putExtra("EXTRA_MFA_RESOLVER", resolver);
//            setResult(MultiFactorActivity.RESULT_NEEDS_MFA_SIGN_IN, intent);
            finish();
        }
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = etLoginGmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
//            mBinding.fieldEmail.setError("Required.");
            etLoginGmail.setError("Required");
            valid = false;
        } else {
//            mBinding.fieldEmail.setError(null);
            etLoginGmail.setError(null);
        }

//        String password = mBinding.fieldPassword.getText().toString();
        String password = etLoginPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
//            mBinding.fieldPassword.setError("Required.");
            etLoginPassword.setError("Required");
            valid = false;
        } else {
//            mBinding.fieldPassword.setError(null);
            etLoginPassword.setError(null);
        }

        return valid;
    }
}
