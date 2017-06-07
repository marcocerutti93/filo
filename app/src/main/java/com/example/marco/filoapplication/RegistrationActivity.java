package com.example.marco.filoapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "Login";

    private EditText mEmailField, mPasswordField, mNameField, mSurnameField;
    private Button registrationButton;
    private String name,surname,email,password;

    private View mProgressView;
    private View focusView = null;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mAuth = FirebaseAuth.getInstance();

        // Views
        mNameField = (EditText) findViewById(R.id.field_name);
        mSurnameField = (EditText) findViewById(R.id.field_surname);
        mEmailField = (EditText) findViewById(R.id.field_registration_email);
        mPasswordField = (EditText) findViewById(R.id.field_registration_password);
        mProgressView = findViewById(R.id.registration_progress_bar);

        // Button
        registrationButton = (Button) findViewById(R.id.registration_button);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = mNameField.getText().toString();
                surname = mSurnameField.getText().toString();
                email = mEmailField.getText().toString();
                password = mPasswordField.getText().toString();
                if (checkDataForRegistration()){
                    focusView.requestFocus();
                } else {
                    mProgressView.setVisibility(View.VISIBLE);
                    InputMethodManager imm = (InputMethodManager)getSystemService(RegistrationActivity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    createAccount();
                }
            }
        });
    }

    private void createAccount() {
        Log.d(TAG, "createAccount:" + email);
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Intent intent = new Intent(RegistrationActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        // [END create_user_with_email]
    }

    public boolean checkDataForRegistration() {

        boolean check = false;
        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()){
            if (password.isEmpty()) {
                mPasswordField.setError(getString(R.string.error_field_required));
                focusView = mPasswordField;
            }
            if (email.isEmpty()) {
                mEmailField.setError(getString(R.string.error_field_required));
                focusView = mEmailField;
            }
            if (surname.isEmpty()) {
                mSurnameField.setError(getString(R.string.error_field_required));
                focusView = mSurnameField;
            }
            if (name.isEmpty()) {
                mNameField.setError(getString(R.string.error_field_required));
                focusView = mNameField;
            }
            check = true;
        } else if (!isEmailValid(email)){
            mEmailField.setError(getString(R.string.error_invalid_email));
            focusView = mEmailField;
            check = true;
        } else if (!isPasswordValid(password)) {
            mPasswordField.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordField;
            Toast.makeText(RegistrationActivity.this, getString(R.string.type_of_password), Toast.LENGTH_SHORT).show();
            check = true;
        }
        return check;
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private boolean isEmailValid(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 6;
    }
}
