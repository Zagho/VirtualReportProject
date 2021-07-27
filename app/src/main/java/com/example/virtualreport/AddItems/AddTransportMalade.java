package com.example.virtualreport.AddItems;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualreport.Data.Transports;
import com.example.virtualreport.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class AddTransportMalade extends AppCompatActivity implements View.OnClickListener {

    private androidx.appcompat.widget.Toolbar Toolbar;
    private AutoCompleteTextView DropdownAmbulance;
    private TextInputLayout CasesLayout;
    private EditText FromTxt;
    private EditText ToTxt;
    private TextInputLayout ToLayout;
    private CheckBox ReportChk;
    private TextView RandomTxt;
    private EditText NotesTxt;
    private EditText PatientNameTxt;
    private EditText PatientAgeTxt;
    private EditText PatientCaseTxt;
    private EditText RegionTxt;
    private EditText NationalityTxt;
    private AutoCompleteTextView DropdownAmbulancier;
    private AutoCompleteTextView DropdownCm;
    private AutoCompleteTextView DropdownSecouriste1;
    private AutoCompleteTextView DropdownSecouriste2;
    private EditText CompanionTxt;
    private TextInputLayout CompanionLayout;

    String ReportCode="";

    DatabaseReference databaseTransport;
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
        setContentView(R.layout.activity_add_transport_malade);

        Toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseTransport = FirebaseDatabase.getInstance().getReference("Transport");

        final Random myRandom = new Random();

        String[] ambulance =new String[]
                {
                        "216",
                        "219",
                        "260",
                        "261",
                        "262",
                        "264"
                };

        String[] ambulancier=getResources().getStringArray(R.array.Ambulancier);
        String[] chefDeMission=getResources().getStringArray(R.array.CM);
        String[] secouriste1=getResources().getStringArray(R.array.Secouriste1);
        String[] secoutiste2=getResources().getStringArray(R.array.Secouriste2);

        DropdownAmbulance=findViewById(R.id.dropdown_ambulance);
        CasesLayout=findViewById(R.id.casesLayout);
        FromTxt = findViewById(R.id.fromTxt);
        ToTxt = findViewById(R.id.toTxt);
        ToLayout=findViewById(R.id.layoutTo);
        RandomTxt = findViewById(R.id.RandomCode);
        ReportChk = findViewById(R.id.chkReport);
        final Button submitBtn = findViewById(R.id.SubmitBtn);
        NotesTxt = findViewById(R.id.notesTxt);
        PatientNameTxt = findViewById(R.id.PatientNameTxt);
        PatientAgeTxt = findViewById(R.id.PatientAgeTxt);
        PatientCaseTxt = findViewById(R.id.PatientCaseTxt);
        RegionTxt = findViewById(R.id.RegionTxt);
        NationalityTxt = findViewById(R.id.PatientNationalityTxt);
        DropdownAmbulancier = findViewById(R.id.dropdown_ambulancier);
        DropdownCm = findViewById(R.id.dropdown_cm);
        DropdownSecouriste1 = findViewById(R.id.dropdown_secouriste1);
        DropdownSecouriste2 = findViewById(R.id.dropdown_secouriste2);
        CompanionTxt = findViewById(R.id.CompanionTxt);
        CompanionLayout=findViewById(R.id.layoutCompanion);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, ambulance);
        DropdownAmbulance.setAdapter(adapter);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.dropdown_item, ambulancier);
        DropdownAmbulancier.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.dropdown_item, chefDeMission);
        DropdownCm.setAdapter(adapter2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, R.layout.dropdown_item, secouriste1);
        DropdownSecouriste1.setAdapter(adapter3);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, R.layout.dropdown_item, secoutiste2);
        DropdownSecouriste2.setAdapter(adapter4);

        ReportChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    RandomTxt.setVisibility(View.VISIBLE);
                    ReportCode=String.valueOf(myRandom.nextInt(1000));
                    RandomTxt.setText("Please note this number on your sheet: "+ReportCode);
                } else {
                    RandomTxt.setVisibility(View.INVISIBLE);
                }
            }
        });

        submitBtn.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        String ambulance = DropdownAmbulance.getText().toString();
        String region = RegionTxt.getText().toString();
        String patientName = PatientNameTxt.getText().toString();
        String patientAge = PatientAgeTxt.getText().toString();
        String patientCase = PatientCaseTxt.getText().toString();
        String patientNationality = NationalityTxt.getText().toString();
        String companion = CompanionTxt.getText().toString();
        String from = FromTxt.getText().toString();
        String to = ToTxt.getText().toString();
        String ambulancier = DropdownAmbulancier.getText().toString();
        String chefDeMission = DropdownCm.getText().toString();
        String secouriste1 = DropdownSecouriste1.getText().toString();
        String secouriste2 = DropdownSecouriste2.getText().toString();
        String writtenReportCode = "";
        if (ReportChk.isChecked()) {
            writtenReportCode = RandomTxt.getText().toString();
        }
        String notes = NotesTxt.getText().toString();

        if (DropdownAmbulance.getText().toString().equals("") || FromTxt.getText().toString().equals("") || PatientNameTxt.getText().toString().equals("") || PatientAgeTxt.getText().toString().equals("") || PatientCaseTxt.getText().toString().equals("") || NationalityTxt.getText().toString().equals("") || RegionTxt.getText().toString().equals("") || DropdownAmbulancier.getText().toString().equals("") || DropdownCm.getText().toString().equals("")) {
            Snackbar snackbar= Snackbar.make(v, "Please fill all the necessary boxes", Snackbar.LENGTH_LONG);
            snackbar.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            snackbar.show();
        } else {
            String id = databaseTransport.push().getKey();

            Transports transports = new Transports(id,currentDate, currentTime, ambulance, region, patientName, patientAge, patientCase, patientNationality, companion, from, to, ambulancier, chefDeMission, secouriste1, secouriste2, ReportCode, notes);

            databaseTransport.child(id).setValue(transports);

            Toast.makeText(this, "Transport added successfully", Toast.LENGTH_SHORT).show();

            DropdownAmbulance.setText("");
            FromTxt.setText("");
            ToTxt.setText("");
            RandomTxt.setText("");
            ReportChk.setChecked(false);
            NotesTxt.setText("");
            PatientNameTxt.setText("");
            PatientAgeTxt.setText("");
            PatientCaseTxt.setText("");
            RegionTxt.setText("");
            NationalityTxt.setText("");
            DropdownAmbulancier.setText("");
            DropdownCm.setText("");
            DropdownSecouriste1.setText("");
            DropdownSecouriste2.setText("");
            CompanionTxt.setText("");
        }

    }

}



