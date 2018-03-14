package uk.ac.napier.maintenanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page2);
        setTitle("Login");

        Button btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent showRegisterPage = new Intent(LoginPage.this, RegisterPage.class);
                startActivity(showRegisterPage);
            }
        });

        ImageView imgSettings = (ImageView)findViewById(R.id.imgLPSettings);
        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showSettings = new Intent(LoginPage.this, SettingsPage.class);
                startActivity(showSettings);
            }
        });

        Button btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                try{

                    WorkerList workerList = new WorkerList();
                    Worker worker;

                    EditText txtLoginId = (EditText)findViewById(R.id.txtLoginId);
                    EditText txtLoginPass = (EditText)findViewById(R.id.txtLoginPass);

                    if(txtLoginId.getText().toString().isEmpty() || txtLoginPass.getText().toString().isEmpty()){
                        Toast.makeText(LoginPage.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    }

                    if((workerList.find(Integer.parseInt(txtLoginId.getText().toString())))==null){
                        Toast.makeText(LoginPage.this, "Worker not found. Please try again", Toast.LENGTH_SHORT).show();
                        txtLoginId.setText("");
                        txtLoginPass.setText("");

                    }else{
                        worker = workerList.find(Integer.parseInt(txtLoginId.getText().toString()));
                        if(!txtLoginPass.getText().toString().equals(worker.getPassword())){
                            Toast.makeText(LoginPage.this, "Password incorrect. Please try again", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent showTaskPage = new Intent(LoginPage.this, TaskPage.class);
                            startActivity(showTaskPage);
                        }
                    }
                }catch(Exception exception){
                    exception.getMessage();
                }
            }
        });
    }
}
