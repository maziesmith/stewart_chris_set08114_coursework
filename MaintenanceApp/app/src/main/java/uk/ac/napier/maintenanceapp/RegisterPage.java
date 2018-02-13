package uk.ac.napier.maintenanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        try{
            Button btnRegSubmit = (Button) findViewById(R.id.btnRegSubmit);
            btnRegSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void OnClick(View view) {

                    EditText txtName = (EditText) findViewById(R.id.txtRegName);
                    String name = txtName.getText().toString();
                    EditText txtDOB = (EditText) findViewById(R.id.txtRegDOB);
                    String dOB = txtDOB.getText().toString();
                    EditText txtPassword = (EditText) findViewById(R.id.txtRegPass);
                    String password = txtPassword.getText().toString();

                    Worker worker = new Worker();
                    worker.setName(name);
                    worker.setdOB(dOB);
                    worker.setPassword(password);

                    WorkerList workerList = new WorkerList();
                    workerList.add(worker);
                }
            });
        }
        catch(Exception exception)
        {
            throw new IllegalArgumentException(exception);
        }
        }
    }
}
