package uk.ac.napier.maintenanceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static uk.ac.napier.maintenanceapp.TaskList.taskList;

public class TaskPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_page2);


        ListView lstTaskView = (ListView)findViewById(R.id.lstTaskView);
        ArrayList<String> taskIds = new ArrayList<>();

        for (Task task:taskList) {
           // lstTaskView.add;
        }


    }
}
