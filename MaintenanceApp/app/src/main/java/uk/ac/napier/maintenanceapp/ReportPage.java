package uk.ac.napier.maintenanceapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReportPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);

        //Home select spinner
        final Spinner spnHome = (Spinner) findViewById(R.id.spnHome);
        String[] homes = new String[]{"Viewpark", "Abercorn", "Spring Gardens"};
        ArrayAdapter<String> homeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, homes);
        spnHome.setAdapter(homeAdapter);

        //Priority select spinner
        Spinner spnPriority = (Spinner) findViewById(R.id.spnPriority);
        String[] priorities = new String[]{"Low", "Medium", "High", "Urgent"};
        ArrayAdapter<String> prioritiesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, priorities);
        spnPriority.setAdapter(prioritiesAdapter);
        spnPriority.setSelection(prioritiesAdapter.getPosition("Medium"));

        final EditText txtTitle = (EditText)findViewById(R.id.txtTitle);
        final EditText txtDesc = (EditText)findViewById(R.id.txtDesc);
        final EditText txtNotes = (EditText)findViewById(R.id.txtNotes);
        final TextView txtDateSubmitted = (TextView)findViewById(R.id.txtDateSubmitted);

        Calendar dateSubmit = Calendar.getInstance();
        int submitDay = dateSubmit.get(Calendar.DATE);
        int submitMonth = dateSubmit.get(Calendar.MONTH) + 1;
        int submitYear = dateSubmit.get(Calendar.YEAR);
        final String dateSubmitString = submitDay + "/" + submitMonth + "/" + submitYear;

        txtDateSubmitted.setText(dateSubmitString);
        final TaskList taskList = new TaskList();

        Button btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Task task = new Task();
                task.setHome(spnHome.getSelectedItem().toString());
                task.setTitle(txtTitle.getText().toString());
                task.setDateSubmitted(dateSubmitString);

                //read in due date
                DatePicker dteDueDate = (DatePicker)findViewById(R.id.dteDateDue);
                int dueDay = dteDueDate.getDayOfMonth();
                int dueMonth = dteDueDate.getMonth() + 1;
                int dueYear = dteDueDate.getYear();
                String dateDueString = dueDay + "/" + dueMonth + "/" + dueYear;

                task.setDateDue(dateDueString);
                task.setDesc(txtDesc.getText().toString());
                task.setNotes(txtNotes.getText().toString());

                int listSizeBefore = taskList.size();
                taskList.add(task);
                if(listSizeBefore < taskList.size()){
                    Toast.makeText(ReportPage.this, "Your task has been sent successfully.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ReportPage.this, "ERROR: Task not added", Toast.LENGTH_LONG).show();
                }

                Intent showFrontPage = new Intent(ReportPage.this, FrontPage.class);
                startActivity(showFrontPage);

            }
        });
    }
}
