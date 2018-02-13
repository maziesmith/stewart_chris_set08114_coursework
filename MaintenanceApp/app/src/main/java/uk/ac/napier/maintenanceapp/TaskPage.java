package uk.ac.napier.maintenanceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class TaskPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_page2);

        TaskList taskList = new TaskList();

        ListView lstTaskView = (ListView)findViewById(R.id.lstTaskView);
        ArrayList<String> taskTitles = new ArrayList<>();

        //for (Task task:taskList) {

        //}


    }
}
