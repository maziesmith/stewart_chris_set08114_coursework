package uk.ac.napier.maintenanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static uk.ac.napier.maintenanceapp.TaskList.taskList;
import static uk.ac.napier.maintenanceapp.WorkerList.workerList;

public class FrontPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);


        readWorker();
        readTasklist();

        Button btnReport = (Button) findViewById(R.id.btnReport);
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent showReportPage = new Intent(FrontPage.this, ReportPage.class);
                startActivity(showReportPage);
            }
        });

        Button btnTask = (Button) findViewById(R.id.btnTasks);
        btnTask.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent showLoginPage = new Intent (FrontPage.this, LoginPage.class);
                startActivity(showLoginPage);
            }

        });

        Button btnCompleted = (Button)findViewById(R.id.btnCompleted);
        btnCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showCompleted = new Intent(FrontPage.this, CompletedPage.class);
                startActivity(showCompleted);
            }
        });

        ImageView imgSettings = (ImageView)findViewById(R.id.imgFPSettings);
        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showSettings = new Intent(FrontPage.this, SettingsPage.class);
                startActivity(showSettings);
            }
        });

    }

    public void readWorker(){
        try{
            FileInputStream inputStream = openFileInput("workerList");
            ObjectInputStream in = new ObjectInputStream(inputStream);
            int sizeBefore = workerList.size();
            workerList = (ArrayList<Worker>)in.readObject();
            int sizeAfter = workerList.size();
            Worker worker = new Worker();
            worker.setLast_id(worker.getLast_id()+(sizeAfter - sizeBefore)-1);
            in.close();
            inputStream.close();

        }catch(Exception exception){
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void readTasklist(){
        try{

            FileInputStream inputStreamTask = openFileInput("taskList");
            ObjectInputStream inTask = new ObjectInputStream(inputStreamTask);
            int sizeBefore = taskList.size();
            taskList = (ArrayList<Task>)inTask.readObject();
            int sizeAfter = taskList.size();
            Task task = new Task();
            task.setLast_id(task.getLast_id()+(sizeAfter - sizeBefore)-1);
            inTask.close();
            inputStreamTask.close();

        }catch(Exception exception){
            Log.v("TaskReadError",exception.getLocalizedMessage());
                    Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
            exception.printStackTrace();
        }
    }
}
