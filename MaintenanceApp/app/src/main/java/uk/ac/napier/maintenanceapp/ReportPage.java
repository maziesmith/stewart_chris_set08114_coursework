package uk.ac.napier.maintenanceapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import static android.R.attr.startYear;

public class ReportPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);

        //Home select spinner
        Spinner spnHome = (Spinner)findViewById(R.id.spnHome);
        String[] homes = new String[]{"Viewpark", "Abercorn", "Spring Gardens"};
        ArrayAdapter<String> homeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, homes);
        spnHome.setAdapter(homeAdapter);

        //Priority select spinner
        Spinner spnPriority = (Spinner)findViewById(R.id.spnPriority);
        String[] priorities = new String[]{"Low", "Medium", "High", "Urgent"};
        ArrayAdapter<String> prioritiesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, priorities);
        spnPriority.setAdapter(prioritiesAdapter);
        spnPriority.setSelection(prioritiesAdapter.getPosition("Medium"));

        DatePicker dteDateSubmitted = (DatePicker)findViewById(R.id.dteDateSubmitted);

        Button btnSend = (Button)findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent showFrontPage = new Intent(ReportPage.this, FrontPage.class);
                startActivity(showFrontPage);

                Task task = new Task();


                //TODO: toast will appear at end
                Toast.makeText(ReportPage.this, "Your task has been sent successfully.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
