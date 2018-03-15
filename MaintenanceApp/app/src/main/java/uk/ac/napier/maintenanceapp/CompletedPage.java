package uk.ac.napier.maintenanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

import static uk.ac.napier.maintenanceapp.CompletedList.completedTasks;

public class CompletedPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_page);

        try {

            final Spinner spnTasks = (Spinner) findViewById(R.id.spnTasks);
            final ArrayList<String> tasks = new ArrayList<>();
            for (Task task : completedTasks) {
                tasks.add(task.getId() + ". " + task.getTitle());
            }
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tasks);
            spnTasks.setAdapter(adapter);
            final CompletedList completedList = new CompletedList();


            //Home select
            final TextView lblHome = (TextView)findViewById(R.id.lblHome);

            //Priority select spinner
            final TextView txtTitle = (TextView)findViewById(R.id.txtTitle);
            final TextView txtDesc = (TextView) findViewById(R.id.txtDesc);
            final TextView txtPriorityShow = (TextView)findViewById(R.id.txtPriorityShow);
            final TextView txtNotes = (TextView) findViewById(R.id.txtNotes);
            final TextView txtDateSubmitted = (TextView) findViewById(R.id.txtDateSubmitted);
            final TextView txtDateDue = (TextView)findViewById(R.id.txtDateDueShow);

            spnTasks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedTask = spnTasks.getSelectedItem().toString();
                    StringTokenizer taskTokens = new StringTokenizer(selectedTask, ".");
                    String stringID = taskTokens.nextToken();
                    Task task;

                    task = completedList.find(Integer.parseInt(stringID));

                    lblHome.setText(task.getHome());
                    txtTitle.setText(task.getTitle());
                    txtPriorityShow.setText(task.getPriority());
                    txtDesc.setText(task.getDesc());
                    txtDateSubmitted.setText(task.getDateSubmitted());
                    txtDateDue.setText(task.getDateDue());
                    ImageView imgPicture = (ImageView) findViewById(R.id.imgPicture);
                    imgPicture.setImageBitmap(task.getPicture());
                    txtNotes.setText(task.getNotes());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        Button btnHome = (Button)findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(CompletedPage.this, FrontPage.class);
                startActivity(home);
            }
        });
    }
}