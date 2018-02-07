package uk.ac.napier.maintenanceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FrontPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

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

    }
}
