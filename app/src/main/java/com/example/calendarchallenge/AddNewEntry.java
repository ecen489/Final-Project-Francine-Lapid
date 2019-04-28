package com.example.calendarchallenge;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNewEntry extends AppCompatActivity {

    Bitmap pictureBitmap;
    Calendar myCalendar = Calendar.getInstance();
    EditText dateInput;

    EditText titleText;
    EditText detailsText;

    String eventTitle;
    String eventDetails;

    Button addEntryButton;

    public AddNewEntry() {
        this.myCalendar = myCalendar;
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateInput.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_entry);

        Intent camIntent = getIntent();
        pictureBitmap = camIntent.getParcelableExtra("picture");

        ImageView picture = findViewById(R.id.pictureView);
        picture.setImageBitmap(pictureBitmap);

        dateInput = (EditText) findViewById(R.id.date);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        dateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddNewEntry.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        addEntryButton = (Button) findViewById(R.id.addEntry);
        addEntryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEntryFunction();
            }
        });
    }

    private void addEntryFunction() {
        Intent goBackToCalendarIntent = new Intent(this, CalendarActivity.class);
        startActivity(goBackToCalendarIntent);
    }
}
