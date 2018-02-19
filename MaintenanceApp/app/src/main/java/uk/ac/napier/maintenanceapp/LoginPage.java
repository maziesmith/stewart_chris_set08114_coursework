package uk.ac.napier.maintenanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page2);

        Button btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent showRegisterPage = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(showRegisterPage);
            }
        });


        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                WorkerList workerList = new WorkerList();
                Worker worker;

                EditText txtLoginId = (EditText)findViewById(R.id.txtLoginId);

                worker = workerList.find(Integer.parseInt(txtLoginId.getText().toString()));
                Toast.makeText(LoginPage.this, ""+worker.getId(), Toast.LENGTH_SHORT).show();

                Intent showTaskPage = new Intent(LoginPage.this, TaskPage.class);
                startActivity(showTaskPage);
            }
        });

    }
}
