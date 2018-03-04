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

        try{
            final TextView txtRegDisplay = (TextView)findViewById(R.id.txtregDisplay);
            txtRegDisplay.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Calendar cal = Calendar.getInstance();
                    int year = cal.get(Calendar.YEAR);
                    int month = cal.get(Calendar.MONTH);
                    int day = cal.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dateDialog = new DatePickerDialog(RegisterPage.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, displayDateSetListener, year, month, day);
                    dateDialog.show();
                }
            });

            displayDateSetListener = new DatePickerDialog.OnDateSetListener()
            {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day){
                    month += 1;
                    Log.d("ReportPage", "onDateSet: dd/MM/yyyy: " + year + "/" + month + "/" + day);
                    String date = day + "/" + month + "/" + year;
                    txtRegDisplay.setText(date);
                    txtRegDisplay.setBackgroundColor(00);
                }

            };

            Button btnRegSubmit = (Button) findViewById(R.id.btnRegSubmit);
            btnRegSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    EditText txtName = (EditText) findViewById(R.id.txtRegName);
                    String name = txtName.getText().toString();
                    EditText txtPassword = (EditText) findViewById(R.id.txtRegPass);
                    String password = txtPassword.getText().toString();
                    String dOB = txtRegDisplay.getText().toString();

                    Worker worker = new Worker();
                    worker.setName(name);
                    worker.setPassword(password);
                    worker.setdOB(dOB);

                    int listBeforeAdd = workerList.size();
                    workerList.add(worker);
                    if(workerList.size() > listBeforeAdd)
                    {
                        Toast.makeText(RegisterPage.this, "Registration Successful. Worker ID: " + worker.getId(), Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(RegisterPage.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        FileOutputStream fileOutputStream = openFileOutput("C:\\Users\\stech\\Desktop\\New folder\\workerList.txt", Context.MODE_PRIVATE);
                        ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
                        out.writeObject(workerList);
                        out.close();
                        fileOutputStream.close();

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    Intent showTaskPage = new Intent(RegisterPage.this, TaskPage.class);
                    startActivity(showTaskPage);
                }
            });
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }
}
