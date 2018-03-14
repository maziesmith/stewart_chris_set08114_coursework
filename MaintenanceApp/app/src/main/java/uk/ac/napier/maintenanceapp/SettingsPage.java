package uk.ac.napier.maintenanceapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import static uk.ac.napier.maintenanceapp.TaskList.taskList;
import static uk.ac.napier.maintenanceapp.WorkerList.workerList;

public class SettingsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_page);

        setTitle("Settings");

        Button btnAccounts = (Button)findViewById(R.id.btnAccounts);
        btnAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showAccountsPage = new Intent(SettingsPage.this, AccountsPage.class);
                startActivity(showAccountsPage);
            }
        });

        Button btnResetAccs = (Button)findViewById(R.id.btnResetAccs);
        btnResetAccs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workerList.clear();
                Toast.makeText(SettingsPage.this, "All Worker Accounts Deleted", Toast.LENGTH_SHORT).show();
                try {
                    FileOutputStream fileOutputStream = openFileOutput("workerList", Context.MODE_PRIVATE);
                    ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
                    out.writeObject(workerList);
                    out.close();
                    fileOutputStream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnResetTasks = (Button)findViewById(R.id.btnResetTasks);
        btnResetTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskList.clear();
                Toast.makeText(SettingsPage.this, "All Outstanding Tasks Deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
