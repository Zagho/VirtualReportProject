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

import com.example.virtualreport.Data.Urgences;
import com.example.virtualreport.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class AddUrgence extends AppCompatActivity implements View.OnClickListener{

    private androidx.appcompat.widget.Toolbar Toolbar;
    private AutoCompleteTextView DropdownAmbulance;
    private CheckBox avpChk;
    private TextInputLayout CasesLayout;
    private EditText CasesTxt;
    private CheckBox sspChk;
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

    DatabaseReference databaseUrgence;
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
        setContentView(R.layout.activity_add_urgence);

        Toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databaseUrgence = FirebaseDatabase.getInstance().getReference("Urgence");

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
        avpChk = findViewById(R.id.chkAvp);
        CasesTxt = findViewById(R.id.AvpTxt);
        CasesLayout=findViewById(R.id.casesLayout);
        sspChk = findViewById(R.id.chkSsp);
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

        avpChk.setEnabled(true);
        CasesTxt.setEnabled(true);

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

        avpChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    CasesLayout.setVisibility(View.VISIBLE);
                } else {
                    CasesLayout.setVisibility(View.INVISIBLE);
                    CasesTxt.setText("");
                }
            }
        });

        sspChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    ToLayout.setVisibility(View.INVISIBLE);
                    CompanionLayout.setVisibility(View.INVISIBLE);
                } else {
                    ToLayout.setVisibility(View.VISIBLE);
                    CompanionLayout.setVisibility(View.VISIBLE);
                }
            }
        });

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
        String AVP = "";
        if (avpChk.isChecked()) {
            AVP = "AVP";
        }
        String casesNumber = CasesTxt.getText().toString();
        String SSP = "";
        if (sspChk.isChecked()) {
            SSP = "SSP";
        }
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


        if (!avpChk.isChecked() || CasesTxt.getText().toString().equals("1")) {
            if (DropdownAmbulance.getText().toString().equals("") || FromTxt.getText().toString().equals("") || PatientNameTxt.getText().toString().equals("") || PatientAgeTxt.getText().toString().equals("") || PatientCaseTxt.getText().toString().equals("") || NationalityTxt.getText().toString().equals("") || RegionTxt.getText().toString().equals("") || DropdownAmbulancier.getText().toString().equals("") || DropdownCm.getText().toString().equals("")) {
                Snackbar snackbar= Snackbar.make(v, "Please fill all the necessary boxes", Snackbar.LENGTH_LONG);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                snackbar.show();
            } else {
                String id = databaseUrgence.push().getKey();

                Urgences urgences = new Urgences(id,currentDate, currentTime, ambulance, region, patientName, patientAge, patientCase, patientNationality, companion, AVP, casesNumber, SSP, from, to, ambulancier, chefDeMission, secouriste1, secouriste2, ReportCode, notes);

                databaseUrgence.child(id).setValue(urgences);

                Toast.makeText(this, "Urgence added successfully", Toast.LENGTH_SHORT).show();

                avpChk.setEnabled(true);
                CasesTxt.setEnabled(true);
                DropdownAmbulance.setText("");
                avpChk.setChecked(false);
                CasesTxt.setText("");
                sspChk.setChecked(false);
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
        } else if (CasesTxt.getText().toString().trim().length() == 0) {
            CasesTxt.setError("Enter the number of cases or uncheck the AVP box");
            avpChk.setEnabled(true);
            CasesTxt.setEnabled(true);
        } else if (avpChk.isChecked() && CasesTxt.getText().toString().trim().length() > 0) {
            if (DropdownAmbulance.getText().toString().equals("") || FromTxt.getText().toString().equals("") || PatientNameTxt.getText().toString().equals("") || PatientAgeTxt.getText().toString().equals("") || PatientCaseTxt.getText().toString().equals("") || NationalityTxt.getText().toString().equals("") || RegionTxt.getText().toString().equals("") || DropdownAmbulancier.getText().toString().equals("") || DropdownCm.getText().toString().equals("")) {
                Toast.makeText(this, "Please fill all the necessary boxes", Toast.LENGTH_LONG).show();
            } else {
                String value = CasesTxt.getText().toString();
                int Number = Integer.parseInt(value);

                String id = databaseUrgence.push().getKey();

                Urgences urgences = new Urgences(id,currentDate, currentTime, ambulance, region, patientName, patientAge, patientCase, patientNationality, companion, AVP, casesNumber, SSP, from, to, ambulancier, chefDeMission, secouriste1, secouriste2, ReportCode, notes);

                databaseUrgence.child(id).setValue(urgences);


                avpChk.setEnabled(false);
                CasesTxt.setEnabled(false);
                sspChk.setChecked(false);
                ToTxt.setText("");
                RandomTxt.setText("");
                ReportChk.setChecked(false);
                NotesTxt.setText("");
                PatientNameTxt.setText("");
                PatientAgeTxt.setText("");
                PatientCaseTxt.setText("");
                NationalityTxt.setText("");
                CompanionTxt.setText("");
                PatientNameTxt.requestFocus();

                Number = Number - 1;
                String n = Integer.toString(Number);
                CasesTxt.setText(n);

                Toast.makeText(this, "Urgence added successfully, you still have " + n + " cases", Toast.LENGTH_SHORT).show();
            }
        }

    }

}
