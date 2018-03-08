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


            //Home select spinner
            final Spinner spnHome = (Spinner) findViewById(R.id.spnHome);
            String[] homes = new String[]{"Viewpark", "Abercorn", "Spring Gardens"};
            final ArrayAdapter<String> homeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, homes);
            spnHome.setAdapter(homeAdapter);

            //Priority select spinner
            final Spinner spnPriority = (Spinner) findViewById(R.id.spnPriority);
            String[] priorities = new String[]{"Low", "Medium", "High", "Urgent"};
            final ArrayAdapter<String> prioritiesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, priorities);
            spnPriority.setAdapter(prioritiesAdapter);
            spnPriority.setSelection(prioritiesAdapter.getPosition("Medium"));

            final EditText txtTitle = (EditText) findViewById(R.id.txtTitle);
            final EditText txtDesc = (EditText) findViewById(R.id.txtDesc);
            final EditText txtNotes = (EditText) findViewById(R.id.txtNotes);
            final TextView txtDateSubmitted = (TextView) findViewById(R.id.txtDateSubmitted);

            spnTasks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedTask = spnTasks.getSelectedItem().toString();
                    StringTokenizer taskTokens = new StringTokenizer(selectedTask, ".");
                    String stringID = taskTokens.nextToken();
                    Task task;

                    task = completedList.find(Integer.parseInt(stringID));

                    int spinnerPosition = homeAdapter.getPosition(task.getHome());
                    spnHome.setSelection(spinnerPosition);
                    txtTitle.setText(task.getTitle());
                    spinnerPosition = prioritiesAdapter.getPosition(task.getPriority());
                    spnPriority.setSelection(spinnerPosition);
                    txtDateSubmitted.setText(task.getDateSubmitted());
                    StringTokenizer dateTokens = new StringTokenizer(task.getDateDue(), "/");
                    int day = Integer.parseInt(dateTokens.nextToken());
                    int month = Integer.parseInt(dateTokens.nextToken()) - 1;
                    int year = Integer.parseInt(dateTokens.nextToken());
                    DatePicker dteDueDate = (DatePicker) findViewById(R.id.dteDateDue);
                    dteDueDate.updateDate(year, month, day);
                    txtDesc.setText(task.getDesc());
                    ImageView imgPicture = (ImageView) findViewById(R.id.imgPicture);
                    //imgPicture.setImageBitmap(task.getPicture());
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