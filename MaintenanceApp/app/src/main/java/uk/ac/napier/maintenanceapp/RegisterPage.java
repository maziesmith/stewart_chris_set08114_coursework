package uk.ac.napier.maintenanceapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static uk.ac.napier.maintenanceapp.WorkerList.workerList;

public class RegisterPage extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener displayDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

            Button btnRegSubmit = (Button) findViewById(R.id.btnRegSubmit);
            btnRegSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        EditText txtName = (EditText) findViewById(R.id.txtRegName);
                        String name = txtName.getText().toString();
                        EditText txtPassword = (EditText) findViewById(R.id.txtRegPass);
                        String password = txtPassword.getText().toString();

                        Worker worker = new Worker();
                        worker.setName(name);
                        worker.setPassword(password);

                        int listBeforeAdd = workerList.size();
                        workerList.add(worker);
                        if (workerList.size() > listBeforeAdd) {
                            Toast.makeText(RegisterPage.this, "Registration Successful. Worker ID: " + worker.getId(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterPage.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                        try {
                            FileOutputStream fileOutputStream = openFileOutput("workerList", Context.MODE_PRIVATE);
                            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
                            out.writeObject(workerList);
                            out.close();
                            fileOutputStream.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Intent showTaskPage = new Intent(RegisterPage.this, TaskPage.class);
                        startActivity(showTaskPage);
                    }catch (Exception exception){
                        Toast.makeText(RegisterPage.this, exception.getMessage(), Toast.LENGTH_SHORT).show();;
                    }
                }
            });
    }
}
