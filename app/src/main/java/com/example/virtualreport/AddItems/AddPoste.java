package com.example.virtualreport.AddItems;


import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.virtualreport.Data.Poste;
import com.example.virtualreport.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddPoste extends AppCompatActivity implements View.OnClickListener {

    private androidx.appcompat.widget.Toolbar Toolbar;
    private AutoCompleteTextView DropdownAmbulance;
    private AutoCompleteTextView DropdownRescuer;
    private EditText PosteLocation;
    private EditText NotesTxt;
    private RadioGroup PosteType;
    private RadioButton radioBtn;
    private Button Date1;
    private Button Time1;
    private Button Date2;
    private Button Time2;
    private Button AddBtn;
    private TextView RescuersView;
    private Button SubmitBtn;
    private EditText RegionTxt;
    private RadioButton RdBtn1;
    private RadioButton RdBtn2;

    String date1="", date2="", time1="", time2="", AllRescuers="", buttonSelector="";
    private int Hour=0, Minute=0;

    DatabaseReference databasePoste;
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
        setContentView(R.layout.activity_add_poste);

        Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        databasePoste = FirebaseDatabase.getInstance().getReference("Poste");

        String[] rescuers = getResources().getStringArray(R.array.Secouriste2);
        String[] ambulance = new String[]
                {
                        "216",
                        "219",
                        "260",
                        "261",
                        "262",
                        "264"
                };

        DropdownAmbulance = findViewById(R.id.dropdown_ambulance);
        PosteLocation = findViewById(R.id.PosteLocation);
        PosteType = findViewById(R.id.PosteType);
        Date1 = findViewById(R.id.date1);
        Date2 = findViewById(R.id.date2);
        Time1 = findViewById(R.id.time1);
        SubmitBtn = findViewById(R.id.SubmitBtn);
        NotesTxt = findViewById(R.id.notesTxt);
        Time2 = findViewById(R.id.time2);
        RegionTxt = findViewById(R.id.RegionTxt);
        DropdownRescuer = findViewById(R.id.dropdown_ambulancier);
        AddBtn = findViewById(R.id.addBtn);
        RescuersView = findViewById(R.id.rescuersView);
        RdBtn1=findViewById(R.id.rdbtn1);
        RdBtn2=findViewById(R.id.rdBtn2);

        long today = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(today);
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setSelection(today);
        builder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = builder.build();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_item, ambulance);
        DropdownAmbulance.setAdapter(adapter);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.dropdown_item, rescuers);
        DropdownRescuer.setAdapter(adapter1);

        Date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                buttonSelector="date1";
            }
        });

        Date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                buttonSelector="date2";
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d-M-yyyy", Locale.FRANCE);
                switch (buttonSelector)
                {
                    case ("date1"):
                        date1 = simpleDateFormat.format(materialDatePicker.getSelection());
                        Date1.setText(date1);
                        break;

                    case ("date2"):
                        date2 = simpleDateFormat.format(materialDatePicker.getSelection());
                        Date2.setText(date2);
                        break;
                }


            }
            });

        Time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Hour=c.get(Calendar.HOUR_OF_DAY);
                Minute=c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog=new TimePickerDialog(AddPoste.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar t1=Calendar.getInstance();
                        t1.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        t1.set(Calendar.MINUTE,minute);

                        time1= DateFormat.format("hh:mm a",t1).toString();
                        Time1.setText(time1);
                    }
                },Hour,Minute,false);
                timePickerDialog.show();
            }
        });

        Time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                Hour=c.get(Calendar.HOUR_OF_DAY);
                Minute=c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog=new TimePickerDialog(AddPoste.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar t2=Calendar.getInstance();
                        t2.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        t2.set(Calendar.MINUTE,minute);

                        time2= DateFormat.format("hh:mm a",t2).toString();
                        Time2.setText(time2);
                    }
                },Hour,Minute,false);
                timePickerDialog.show();
            }
        });

        SubmitBtn.setOnClickListener(this);
        AddBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        String ambulance = DropdownAmbulance.getText().toString();
        String region = RegionTxt.getText().toString();
        String posteLocation = PosteLocation.getText().toString();
        String notes = NotesTxt.getText().toString();
        String dropdownRescuer = DropdownRescuer.getText().toString();
        String posteType="";

        int selectedId = PosteType.getCheckedRadioButtonId();
        if(RdBtn1.isChecked()|| RdBtn2.isChecked())
        {
            radioBtn = findViewById(selectedId);
            posteType = radioBtn.getText().toString();
        }
        else
            posteType="";



        switch (v.getId()) {

            case R.id.SubmitBtn:

                if (ambulance.equals("") || posteLocation.equals("") || time1.equals("") || time2.equals("") || date1.equals("") || date2.equals("") || posteType.equals("") || (DropdownRescuer.getText().toString().equals("") && AllRescuers.equals("")) || RegionTxt.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar.make(v, "Please fill all the necessary boxes", Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    snackbar.show();
                } else {
                    String id = databasePoste.push().getKey();
                    String ListOfRescuers;
                    if(dropdownRescuer.equals(""))
                        ListOfRescuers=AllRescuers;
                    else
                        ListOfRescuers=AllRescuers+" - "+dropdownRescuer;

                    Poste poste = new Poste(id,currentDate, currentTime, ambulance, region, posteLocation, posteType, date1, time1, date2, time2, ListOfRescuers, notes);

                    databasePoste.child(id).setValue(poste);

                    Toast.makeText(this, "Poste added successfully", Toast.LENGTH_SHORT).show();

                    DropdownAmbulance.setText("");
                    PosteLocation.setText("");
                    PosteType.clearCheck();
                    NotesTxt.setText("");
                    RegionTxt.setText("");
                    RescuersView.setText("");
                    DropdownRescuer.setText("");
                    posteType="";
                    AllRescuers="";
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



