package uk.ac.napier.maintenanceapp;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import static android.R.attr.startYear;

public class ReportPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);

        Spinner spnHome = (Spinner)findViewById(R.id.spnHome);
        String[] homes = new String[]{"Viewpark", "Abercorn", "Spring Gardens"};
        ArrayAdapter<String> homeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, homes);
        spnHome.setAdapter(homeAdapter);

        DatePicker dteDateSubmitted = (DatePicker)findViewById(R.id.dteDateSubmitted);

    }
}
