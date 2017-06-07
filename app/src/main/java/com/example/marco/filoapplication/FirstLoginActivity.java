package com.example.marco.filoapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class FirstLoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);

        auth = FirebaseAuth.getInstance();
        userData = (TextView) findViewById(R.id.name_surname);
        userData.setText(auth.getCurrentUser().getEmail());
    }


}
