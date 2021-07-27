package com.example.virtualreport.AddItems;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualreport.Data.SoinAuCentre;
import com.example.virtualreport.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddSoinAuCentre extends AppCompatActivity implements View.OnClickListener {

    private androidx.appcompat.widget.Toolbar Toolbar;
    private EditText NotesTxt;
    private EditText PatientNameTxt;
    private EditText PatientAgeTxt;
    private EditText PatientCaseTxt;
    private EditText NationalityTxt;
    private AutoCompleteTextView DropdownRescuer;
    private Button AddBtn;
    private TextView RescuersView;
    private Button SubmitBtn;

    String AllRescuers="";

    DatabaseReference databaseSoinAuCentre;
    Calendar calendar = Calendar.getInstance();
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d-M-yyyy");
    String currentDate = simpleDateFormat.format(calendar.getTime());

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");
    String currentTime = simpleTimeFormat.format(calendar.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sac);

        Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseSoinAuCentre = FirebaseDatabase.getInstance().getReference("SAC");

        String[] rescuers = getResources().getStringArray(R.array.Secouriste2);

        SubmitBtn = findViewById(R.id.SubmitBtn);
        NotesTxt = findViewById(R.id.notesTxt);
        PatientNameTxt = findViewById(R.id.PatientNameTxt);
        PatientAgeTxt = findViewById(R.id.PatientAgeTxt);
        PatientCaseTxt = findViewById(R.id.PatientCaseTxt);
        NationalityTxt = findViewById(R.id.PatientNationalityTxt);
        DropdownRescuer = findViewById(R.id.dropdown_ambulancier);
        AddBtn = findViewById(R.id.addBtn);
        RescuersView = findViewById(R.id.rescuersView);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.dropdown_item, rescuers);
        DropdownRescuer.setAdapter(adapter1);

        AddBtn.setOnClickListener(this);
        SubmitBtn.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        String patientName = PatientNameTxt.getText().toString();
        String patientAge = PatientAgeTxt.getText().toString();
        String patientCase = PatientCaseTxt.getText().toString();
        String patientNationality = NationalityTxt.getText().toString();
        String dropdownRescuer = DropdownRescuer.getText().toString();
        String notes = NotesTxt.getText().toString();

        switch (v.getId()) {
            case R.id.SubmitBtn:
                if (PatientNameTxt.getText().toString().equals("") || PatientAgeTxt.getText().toString().equals("") || PatientCaseTxt.getText().toString().equals("") || NationalityTxt.getText().toString().equals("") || (DropdownRescuer.getText().toString().equals("")&&AllRescuers.equals(""))) {
                    Snackbar snackbar = Snackbar.make(v, "Please fill all the necessary boxes", Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    snackbar.show();
                } else {
                    String id = databaseSoinAuCentre.push().getKey();
                    String ListOfRescuers;
                    if(dropdownRescuer.equals(""))
                        ListOfRescuers=AllRescuers;
                    else
                        ListOfRescuers=AllRescuers+" - "+dropdownRescuer;

                    SoinAuCentre soinAuCentre = new SoinAuCentre(id, currentDate, currentTime, patientName, patientAge, patientCase, patientNationality, ListOfRescuers, notes);

                    databaseSoinAuCentre.child(id).setValue(soinAuCentre);

                    Toast.makeText(this, "Soin au centre added successfully", Toast.LENGTH_SHORT).show();

                    NotesTxt.setText("");
                    PatientNameTxt.setText("");
                    PatientAgeTxt.setText("");
                    PatientCaseTxt.setText("");
                    NationalityTxt.setText("");
                    AllRescuers="";
                    RescuersView.setText("");
                    DropdownRescuer.setText("");
                }
                break;

            case R.id.addBtn:
                if(dropdownRescuer.equals(""))
                {
                    Snackbar snackbar=Snackbar.make(v, "Please enter a rescuer first",Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    snackbar.show();
                }
                else
                {
                    if(AllRescuers.equals(""))
                        AllRescuers=dropdownRescuer;
                    else
                        AllRescuers=AllRescuers+" - "+dropdownRescuer;
                    RescuersView.setText(AllRescuers);
                    DropdownRescuer.setText("");
                }
                break;


        }

    }
}



