package uk.ac.napier.maintenanceapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.jar.Manifest;

import static uk.ac.napier.maintenanceapp.R.drawable.profile_pic;
import static uk.ac.napier.maintenanceapp.WorkerList.workerList;

public class AccountsPage extends AppCompatActivity {

    private static final int PICK_IMAGE =  100;
    TextView txtID;
    TextView txtName;
    TextView lblEmail;
    TextView lblPhone;
    ImageView imgProfilePic;
    TextView txtEditName;
    TextView txtEditEmail;
    TextView txtEditPhone;
    Button btnSave;
    TextView lblChangePic;
    TextView txtEditPass;
    Button btnEditAcc;
    Spinner spnAccs;
    Bitmap newProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_page);

        setTitle("Accounts");

        spnAccs = (Spinner)findViewById(R.id.spnAccount);
        ArrayList<String> accs = new ArrayList<>();
        for (Worker worker : workerList) {
            accs.add(worker.getId() + ". " + worker.getName());
        }
        ArrayAdapter<String> accsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, accs);
        spnAccs.setAdapter(accsAdapter);
        final WorkerList workerList = new WorkerList();

        txtID = (TextView) findViewById(R.id.txtAccID);
        txtName = (TextView) findViewById(R.id.txtAccName);
        lblEmail = (TextView)findViewById(R.id.lblEmail);
        lblPhone = (TextView)findViewById(R.id.lblPhone);
        imgProfilePic = (ImageView)findViewById(R.id.imgProfilePic);
        txtEditName = (TextView)findViewById(R.id.txtEditName);
        txtEditEmail = (TextView)findViewById(R.id.txtEditEmail);
        txtEditPhone = (TextView)findViewById(R.id.txtEditPhone);
        btnSave = (Button)findViewById(R.id.btnAccSave);
        lblChangePic = (TextView)findViewById(R.id.lblChangePic);
        txtEditPass = (TextView)findViewById(R.id.txtEditPass);

        txtEditPass.setVisibility(View.INVISIBLE);
        lblChangePic.setVisibility(View.INVISIBLE);
        btnSave.setVisibility(View.INVISIBLE);
        txtEditEmail.setVisibility(View.INVISIBLE);
        txtEditName.setVisibility(View.INVISIBLE);
        txtEditPhone.setVisibility(View.INVISIBLE);

        spnAccs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedAcc = spnAccs.getSelectedItem().toString();
                StringTokenizer accTokens = new StringTokenizer(selectedAcc, ".");
                String stringID = accTokens.nextToken();
                Worker worker;

                worker = workerList.find(Integer.parseInt(stringID));

                txtID.setText(""+worker.getId());
                txtName.setText(worker.getName());

                if(worker.getEmail() != null){
                    lblEmail.setText(worker.getEmail());
                }
                if(worker.getPhone() != null){
                    lblPhone.setText(worker.getPhone());
                }
                if(worker.getProfile_pic() != null){
                    imgProfilePic.setImageBitmap(worker.getProfile_pic());
                }else{
                    imgProfilePic.setImageResource(profile_pic);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnEditAcc = (Button)findViewById(R.id.btnEditAcc);
        if(accs.isEmpty()){
            btnEditAcc.setVisibility(View.INVISIBLE);
        }
        btnEditAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AccountsPage.this);
                View alertView = getLayoutInflater().inflate(R.layout.login_dialog, null);
                alertBuilder.setView(alertView);
                final AlertDialog dialog = alertBuilder.create();
                dialog.show();
                final EditText txtPassword = (EditText)alertView.findViewById(R.id.txtDialogPass);
                Button btnLogin = (Button)alertView.findViewById(R.id.btnSubmit);
                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String selectedAcc = spnAccs.getSelectedItem().toString();
                        StringTokenizer accTokens = new StringTokenizer(selectedAcc, ".");
                        String stringID = accTokens.nextToken();
                        Worker worker;

                        worker = workerList.find(Integer.parseInt(stringID));
                        if(txtPassword.getText().toString().isEmpty() || !worker.getPassword().equals(txtPassword.getText().toString())){
                            Toast.makeText(AccountsPage.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        }else{
                            dialog.hide();
                            editAccXML();

                            txtEditName.setText(worker.getName());
                            if (worker.getEmail() != null) {
                                    txtEditEmail.setText(worker.getEmail());
                            }
                            if(worker.getPhone() != null) {
                                txtEditPhone.setText(worker.getPhone());
                            }
                            if(worker.getProfile_pic() != null) {
                                imgProfilePic.setImageBitmap(worker.getProfile_pic());
                            }

                            imgProfilePic.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                    startActivityForResult(photoPickerIntent, PICK_IMAGE);
                                }
                            });

                        }
                    }
                });
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String selectedAcc = spnAccs.getSelectedItem().toString();
                    StringTokenizer accTokens = new StringTokenizer(selectedAcc, ".");
                    String stringID = accTokens.nextToken();
                    Worker worker;

                    worker = workerList.find(Integer.parseInt(stringID));
                    worker.setName(txtEditName.getText().toString());
                    worker.setProfile_pic(newProfilePic);
                    if (!txtEditEmail.getText().toString().isEmpty()) {
                        worker.setEmail(txtEditEmail.getText().toString());
                    }
                    if(!txtEditPhone.getText().toString().isEmpty()){
                        worker.setPhone(txtEditEmail.getText().toString());
                    }
                    if(!txtEditPass.getText().toString().isEmpty()){
                        worker.setPassword(txtEditPass.getText().toString());
                    }
                    Toast.makeText(AccountsPage.this, "Account Amended", Toast.LENGTH_SHORT).show();
                    recreate();
                    try {
                        FileOutputStream fileOutputStream = openFileOutput("workerList", Context.MODE_PRIVATE);
                        ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
                        out.writeObject(workerList);
                        out.close();
                        fileOutputStream.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }catch(Exception exception){
                    Toast.makeText(AccountsPage.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if(resultCode == RESULT_OK && reqCode == PICK_IMAGE && data != null) {
            Uri uriImage = data.getData();
            imgProfilePic = (ImageView) findViewById(R.id.imgProfilePic);
            try {
                newProfilePic = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriImage));
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AccountsPage.this);
                View alertView = getLayoutInflater().inflate(R.layout.dialog_profile_pic, null);
                alertBuilder.setView(alertView);
                final AlertDialog dialog = alertBuilder.create();
                dialog.show();
                final ImageView imgPreview = (ImageView)alertView.findViewById(R.id.imgPicPreview);
                imgPreview.setImageBitmap(newProfilePic);

                Button btnRotate = (Button)alertView.findViewById(R.id.btnRotate);
                btnRotate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Matrix matrix = new Matrix();

                        matrix.postRotate(90);

                        newProfilePic = Bitmap.createBitmap(newProfilePic , 0, 0, newProfilePic.getWidth(), newProfilePic .getHeight(), matrix, true);
                        imgPreview.setImageBitmap(newProfilePic);
                    }
                });

                Button btnSet = (Button)alertView.findViewById(R.id.btnSet);
                btnSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imgProfilePic.setImageBitmap(newProfilePic);
                        dialog.hide();
                    }
                });

            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void editAccXML(){
        btnEditAcc.setVisibility(View.INVISIBLE);
        txtName.setVisibility(View.INVISIBLE);
        lblEmail.setVisibility(View.INVISIBLE);
        lblPhone.setVisibility(View.INVISIBLE);
        spnAccs.setVisibility(View.INVISIBLE);
        txtEditEmail.setVisibility(View.VISIBLE);
        txtEditName.setVisibility(View.VISIBLE);
        txtEditPhone.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.VISIBLE);
        lblChangePic.setVisibility(View.VISIBLE);
        txtEditPass.setVisibility(View.VISIBLE);
    }
}
