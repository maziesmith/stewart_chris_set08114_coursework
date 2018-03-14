package uk.ac.napier.maintenanceapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;

import static uk.ac.napier.maintenanceapp.TaskList.taskList;

public class TaskPage extends AppCompatActivity {

    //Button btnComplete = (Button) findViewById(R.id.btnCompleted);
    Bitmap bitmapPicture;
    SensorManager sensorManager;
    //current acceleration value and gravity
    float accelerateVal;
    //last acceleration value and gravity
    float accelerateLast;
    //difference in acceleration value from gravity
    float shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_page2);

        try {

            sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
            sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
            accelerateVal = SensorManager.GRAVITY_EARTH;
            accelerateLast = SensorManager.GRAVITY_EARTH;
            shake = 0.00f;


            final Spinner spnTasks = (Spinner) findViewById(R.id.spnTasks);
            final ArrayList<String> tasks = new ArrayList<>();
            for (Task task : taskList) {
                tasks.add(task.getId() + ". " + task.getTitle());
            }
            final ArrayAdapter<String> taskAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, tasks);
            spnTasks.setAdapter(taskAdapter);
            final TaskList list = new TaskList();

            //Home select spinner
            final Spinner spnHome = (Spinner) findViewById(R.id.spnHome);
            String[] homes = new String[]{"Viewpark", "Abercorn", "Spring Gardens"};
            final ArrayAdapter<String> homeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, homes);
            spnHome.setAdapter(homeAdapter);

            //Priority select spinner
            final Spinner spnPriority = (Spinner) findViewById(R.id.spnPriority);
            String[] priorities = new String[]{"Low", "Medium", "High", "Urgent"};
            final ArrayAdapter<String> prioritiesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, priorities);
            spnPriority.setAdapter(prioritiesAdapter);
            spnPriority.setSelection(prioritiesAdapter.getPosition("Medium"));

            final EditText txtTitle = (EditText) findViewById(R.id.txtTitle);
            final EditText txtDesc = (EditText) findViewById(R.id.txtDesc);
            final EditText txtNotes = (EditText) findViewById(R.id.txtNotes);
            final TextView txtDateSubmitted = (TextView) findViewById(R.id.txtDateSubmitted);

            spnTasks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedTask = spnTasks.getSelectedItem().toString();
                    StringTokenizer taskTokens = new StringTokenizer(selectedTask, ".");
                    String stringID = taskTokens.nextToken();
                    Task task;

                    task = list.find(Integer.parseInt(stringID));

                    int spinnerPosition = homeAdapter.getPosition(task.getHome());
                    spnHome.setSelection(spinnerPosition);
                    txtTitle.setText(task.getTitle());
                    spinnerPosition = prioritiesAdapter.getPosition(task.getPriority());
                    spnPriority.setSelection(spinnerPosition);
                    txtDateSubmitted.setText(task.getDateSubmitted());
                    StringTokenizer dateTokens = new StringTokenizer(task.getDateDue(), "/");
                    int day = Integer.parseInt(dateTokens.nextToken());
                    int month = Integer.parseInt(dateTokens.nextToken()) - 1;
                    int year = Integer.parseInt(dateTokens.nextToken());
                    DatePicker dteDueDate = (DatePicker) findViewById(R.id.dteDateDue);
                    dteDueDate.updateDate(year, month, day);
                    txtDesc.setText(task.getDesc());
                    ImageView imgPicture = (ImageView) findViewById(R.id.imgPicture);
                    imgPicture.setImageBitmap(task.getPicture());
                    txtNotes.setText(task.getNotes());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            final Button btnPicture = (Button) findViewById(R.id.btnAddPicture);
            btnPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                    btnPicture.setText("Update Picture");
                }
            });

            Button btnDelete = (Button) findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedTask = spnTasks.getSelectedItem().toString();
                    StringTokenizer taskTokens = new StringTokenizer(selectedTask, ".");
                    String stringID = taskTokens.nextToken();

                    list.remove(Integer.parseInt(stringID));

                    txtDateSubmitted.setText("");
                    txtDesc.setText("");
                    txtNotes.setText("");
                    txtTitle.setText("");
                    ImageView imgPicture = (ImageView) findViewById(R.id.imgPicture);
                    imgPicture.setImageResource(android.R.color.transparent);
                    taskAdapter.notifyDataSetChanged();

                    write();
                }
            });

            Button btnComplete = (Button) findViewById(R.id.btnCompleted);
            btnComplete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (spnTasks.getSelectedItem() != null) {
                        String selectedTask = spnTasks.getSelectedItem().toString();
                        StringTokenizer taskTokens = new StringTokenizer(selectedTask, ".");
                        String stringID = taskTokens.nextToken();

                        Task task;
                        task = list.find(Integer.parseInt(stringID));
                        task.setCompleted(true);

                        Calendar completeDate = Calendar.getInstance();
                        int submitDay = completeDate.get(Calendar.DATE);
                        int submitMonth = completeDate.get(Calendar.MONTH) + 1;
                        int submitYear = completeDate.get(Calendar.YEAR);
                        String dateCompleteString = submitDay + "/" + submitMonth + "/" + submitYear;
                        task.setCompleteDate(dateCompleteString);
                        spnTasks.setSelection(0);
                        list.complete(task.getId());

                        txtDateSubmitted.setText("");
                        txtDesc.setText("");
                        txtNotes.setText("");
                        txtTitle.setText("");
                        ImageView imgPicture = (ImageView) findViewById(R.id.imgPicture);
                        imgPicture.setImageResource(android.R.color.transparent);
                        Intent showFront = new Intent(TaskPage.this, FrontPage.class);
                        startActivity(showFront);

                        tasks.remove(selectedTask);
                        taskAdapter.notifyDataSetChanged();

                        Toast.makeText(TaskPage.this, "Task completed", Toast.LENGTH_SHORT).show();

                        write();
                    }
                    else {
                        Log.v("NothingSelected", "NOthing");
                    }
                }
            });

            Button btnHome = (Button)findViewById(R.id.btnHome);
            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent home = new Intent(TaskPage.this, FrontPage.class);
                    startActivity(home);
                }
            });

            Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selectedTask = spnTasks.getSelectedItem().toString();
                    StringTokenizer taskTokens = new StringTokenizer(selectedTask, ".");
                    String stringID = taskTokens.nextToken();
                    Task task;
                    task = list.find(Integer.parseInt(stringID));

                    try {
                        //read in due date
                        DatePicker dteDueDate = (DatePicker) findViewById(R.id.dteDateDue);
                        int dueDay = dteDueDate.getDayOfMonth();
                        int dueMonth = dteDueDate.getMonth() + 1;
                        int dueYear = dteDueDate.getYear();
                        String dateDueString = dueDay + "/" + dueMonth + "/" + dueYear;

                        task.setHome(spnHome.getSelectedItem().toString());
                        task.setTitle(txtTitle.getText().toString());
                        task.setPriority(spnPriority.getSelectedItem().toString());
                        task.setDateDue(dateDueString);
                        task.setDesc(txtDesc.getText().toString());
                        task.setNotes(txtNotes.getText().toString());
                        task.setPicture(bitmapPicture);
                        Toast.makeText(TaskPage.this, "Task updated", Toast.LENGTH_SHORT).show();

                        write();
                    }catch(Exception exception){
                        Toast.makeText(TaskPage.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch(Exception exception){
            Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            bitmapPicture = (Bitmap) data.getExtras().get("data");
            ImageView imgPicture = (ImageView) findViewById(R.id.imgPicture);
            imgPicture.setImageBitmap(bitmapPicture);
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            accelerateLast = accelerateVal;
            accelerateVal = (float)Math.sqrt((double) x*x + y*y + z*z);
            float delta = accelerateVal - accelerateLast;
            shake = shake * 0.09f + delta;

            if(shake > 12){
                Button btnComplete = (Button) findViewById(R.id.btnCompleted);
                btnComplete.performClick();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public void write(){
        try {
            FileOutputStream fileOutputStream = openFileOutput("taskList", Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(taskList);
            out.close();
            fileOutputStream.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
