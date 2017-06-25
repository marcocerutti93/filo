package com.example.marco.filoapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class FirstLoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView userData;
    private Spinner fvsSpinner;
    private ArrayAdapter<Integer> fvsSpinnerAdapter;
    private static TextView dob;
    private static String dateOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);

        //auth = FirebaseAuth.getInstance();
        //userData = (TextView) findViewById(R.id.name_surname);
        //userData.setText(auth.getCurrentUser().getEmail());

        dob = (TextView) findViewById(R.id.date_of_birth_text2);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        dateOfBirth = String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
        dob.setText(dateOfBirth);
        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        fvsSpinner = (Spinner) findViewById(R.id.left_ventricular_function_spinner);
        Integer[] perc = new Integer[101];
        for (int i = 0; i < perc.length; ++i) {
            perc[i] = i;
        }
        fvsSpinnerAdapter = new ArrayAdapter<>(this,R.layout.spinner_item, perc);
        fvsSpinner.setAdapter(fvsSpinnerAdapter);
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            dateOfBirth = String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year);
            dob.setText(dateOfBirth);
        }
    }

}
