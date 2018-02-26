package uk.ac.napier.maintenanceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import static uk.ac.napier.maintenanceapp.TaskList.taskList;

public class TaskPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_page2);

        ListView lstTaskView = (ListView)findViewById(R.id.lstTaskView);
        String[] tasks = new String[]{};
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(tasks));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_task_page2,R.id.lstTaskView,arrayList);
        lstTaskView.setAdapter(adapter);
        for (String s:TaskList.getListTitlesID())
        {
            arrayList.add(s);
            adapter.notifyDataSetChanged();
        }
       // String[] tasks = TaskList.getListTitlesID();
    }
}
