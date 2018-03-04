package uk.ac.napier.maintenanceapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static uk.ac.napier.maintenanceapp.WorkerList.workerList;

public class FrontPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

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
            Toast.makeText(this, "DIDNT WORK", Toast.LENGTH_SHORT).show();
            exception.printStackTrace();
        }

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
    }
}
