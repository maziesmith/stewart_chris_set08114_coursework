package uk.ac.napier.maintenanceapp;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;

import static android.R.attr.startYear;

public class ReportPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);

        DatePicker dteDateSubmitted = (DatePicker)findViewById(R.id.dteDateSubmitted);

    }
}
