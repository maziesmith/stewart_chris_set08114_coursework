package uk.ac.napier.maintenanceapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Calendar;

public class ReportPage extends AppCompatActivity {


    Bitmap bitmapPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);

        //Home select spinner
        final Spinner spnHome = (Spinner) findViewById(R.id.spnHome);
        String[] homes = new String[]{"Viewpark", "Abercorn", "Spring Gardens"};
        ArrayAdapter<String> homeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, homes);
        spnHome.setAdapter(homeAdapter);

        //Priority select spinner
        final Spinner spnPriority = (Spinner) findViewById(R.id.spnPriority);
        String[] priorities = new String[]{"Low", "Medium", "High", "Urgent"};
        ArrayAdapter<String> prioritiesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, priorities);
        spnPriority.setAdapter(prioritiesAdapter);
        spnPriority.setSelection(prioritiesAdapter.getPosition("Medium"));

        final EditText txtTitle = (EditText)findViewById(R.id.txtTitle);
        final EditText txtDesc = (EditText)findViewById(R.id.txtDesc);
        final EditText txtNotes = (EditText)findViewById(R.id.txtNotes);
        final TextView txtDateSubmitted = (TextView)findViewById(R.id.txtDateSubmitted);

        Calendar dateSubmit = Calendar.getInstance();
        int submitDay = dateSubmit.get(Calendar.DATE);
        int submitMonth = dateSubmit.get(Calendar.MONTH) + 1;
        int submitYear = dateSubmit.get(Calendar.YEAR);
        final String dateSubmitString = submitDay + "/" + submitMonth + "/" + submitYear;

        txtDateSubmitted.setText(dateSubmitString);
        final TaskList taskList = new TaskList();

        final Button btnPicture = (Button)findViewById(R.id.btnAddPicture);
        btnPicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
                btnPicture.setText("Update Picture");
                }
        });

        Button btnSend = (Button) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Task task = new Task();
                task.setHome(spnHome.getSelectedItem().toString());
                task.setTitle(txtTitle.getText().toString());
                task.setDateSubmitted(dateSubmitString);
                task.setPriority(spnPriority.getSelectedItem().toString());

                //read in due date
                DatePicker dteDueDate = (DatePicker)findViewById(R.id.dteDateDue);
                int dueDay = dteDueDate.getDayOfMonth();
                int dueMonth = dteDueDate.getMonth() + 1;
                int dueYear = dteDueDate.getYear();
                String dateDueString = dueDay + "/" + dueMonth + "/" + dueYear;

                task.setDateDue(dateDueString);
                task.setDesc(txtDesc.getText().toString());
                task.setNotes(txtNotes.getText().toString());
                //task.setPicture(bitmapPicture);

                int listSizeBefore = taskList.size();
                taskList.add(task);
                if(listSizeBefore < taskList.size()){
                    Toast.makeText(ReportPage.this, "Your task has been sent successfully.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ReportPage.this, "ERROR: Task not added", Toast.LENGTH_LONG).show();
                }

                try {
                    FileOutputStream fileOutputStream = openFileOutput("taskList", Context.MODE_PRIVATE);
                    ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
                    out.writeObject(taskList);
                    out.close();
                    fileOutputStream.close();

                }catch(Exception e){
                    e.printStackTrace();
                }
                if(task.getPriority().contentEquals("Urgent")){
                    createNotification(task);
                }
                Intent showFrontPage = new Intent(ReportPage.this, FrontPage.class);
                startActivity(showFrontPage);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bitmapPicture = (Bitmap)data.getExtras().get("data");
        ImageView imgPicture = (ImageView)findViewById(R.id.imgPicture);
        imgPicture.setImageBitmap(bitmapPicture);
    }

    private void createNotification(Task task){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        Intent showLogin = new Intent(this, LoginPage.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int)System.currentTimeMillis(), showLogin, 0);

        builder
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("New Urgent Task")
                .setContentText(task.getTitle())
                .setContentIntent(pendingIntent);

        Notification urgentNotification = builder.build();
        NotificationManagerCompat.from(this).notify(0, urgentNotification);
    }

}
