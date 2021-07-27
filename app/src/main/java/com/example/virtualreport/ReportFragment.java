package com.example.virtualreport;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.virtualreport.AddItems.AddPoste;
import com.example.virtualreport.AddItems.AddSoinADomicile;
import com.example.virtualreport.AddItems.AddSoinAuCentre;
import com.example.virtualreport.AddItems.AddTransportMalade;
import com.example.virtualreport.AddItems.AddUrgence;
import com.example.virtualreport.Data.Poste;
import com.example.virtualreport.Data.SoinADomicile;
import com.example.virtualreport.Data.SoinAuCentre;
import com.example.virtualreport.Data.Transports;
import com.example.virtualreport.Data.Urgences;
import com.example.virtualreport.Fetch.PosteList;
import com.example.virtualreport.Fetch.SoinADomicileList;
import com.example.virtualreport.Fetch.SoinAuCentreList;
import com.example.virtualreport.Fetch.UrgenceTransportList;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class ReportFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Button DayBackBtn;
    private Button DayFutureBtn;
    private Button UrgenceBtn;
    private Button TransportBtn;
    private Button SacBtn;
    private Button SadBtn;
    private Button PosteBtn;
    private Button DatePickerBtn;
    private ProgressDialog progressDialog;
    private LinearLayout LayoutUrgTrans, LayoutSAC, LayoutSAD, LayoutPoste;
    private FloatingActionButton fab_main, fab1_urgence, fab2_tm, fab3_sac, fab4_sad, fab5_poste;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    private View view;
    TextView textview_urgence, textview_tm, textview_sac, textview_sad, textview_poste;

    String date, identifier = "";
    String buttonChoosed = "Urgence", itemId;
    Boolean isOpen = false;
    int day = 0, month = 0, year = 0;

    ListView listViewRapport;
    List<Urgences> urgencesList;
    List<Transports> transportsList;
    List<SoinAuCentre> soinAuCentreList;
    List<SoinADomicile> soinADomicileList;
    List<Poste> posteList;

    Calendar calendar = Calendar.getInstance();

    DatabaseReference databaseMission;

    public void close_fab() {
        textview_urgence.setVisibility(View.INVISIBLE);
        textview_tm.setVisibility(View.INVISIBLE);
        textview_sac.setVisibility(View.INVISIBLE);
        textview_sad.setVisibility(View.INVISIBLE);
        textview_poste.setVisibility(View.INVISIBLE);
        fab1_urgence.startAnimation(fab_close);
        fab2_tm.startAnimation(fab_close);
        fab3_sac.startAnimation(fab_close);
        fab4_sad.startAnimation(fab_close);
        fab5_poste.startAnimation(fab_close);
        fab_main.startAnimation(fab_anticlock);
        fab1_urgence.setClickable(false);
        fab2_tm.setClickable(false);
        fab3_sac.setClickable(false);
        fab4_sad.setClickable(false);
        fab5_poste.setClickable(false);
        view.setClickable(false);
        isOpen = false;
    }

    public void open_fab() {
        textview_urgence.setVisibility(View.VISIBLE);
        textview_tm.setVisibility(View.VISIBLE);
        textview_sac.setVisibility(View.VISIBLE);
        textview_sad.setVisibility(View.VISIBLE);
        textview_poste.setVisibility(View.VISIBLE);
        fab1_urgence.startAnimation(fab_open);
        fab2_tm.startAnimation(fab_open);
        fab3_sac.startAnimation(fab_open);
        fab4_sad.startAnimation(fab_open);
        fab5_poste.startAnimation(fab_open);
        fab_main.startAnimation(fab_clock);
        fab1_urgence.setClickable(true);
        fab2_tm.setClickable(true);
        fab3_sac.setClickable(true);
        fab4_sad.setClickable(true);
        fab5_poste.setClickable(true);
        view.setClickable(true);
        isOpen = true;
    }

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d-M-yyyy");
    String currentDate = simpleDateFormat.format(calendar.getTime());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_report, container, false);

        UrgenceBtn = view1.findViewById(R.id.UrgenceBtn);
        TransportBtn = view1.findViewById(R.id.TransportBtn);
        SadBtn = view1.findViewById(R.id.SadBtn);
        SacBtn = view1.findViewById(R.id.SacBtn);
        PosteBtn = view1.findViewById(R.id.PosteBtn);
        DayBackBtn = view1.findViewById(R.id.dayBackBtn);
        DayFutureBtn = view1.findViewById(R.id.dayFutureBtn);
        view = view1.findViewById(R.id.view);
        listViewRapport = view1.findViewById(R.id.listViewRapport);
        LayoutSAC = view1.findViewById(R.id.LayoutSAC);
        LayoutUrgTrans = view1.findViewById(R.id.LayoutUrgTrans);
        LayoutSAD = view1.findViewById(R.id.LayoutSAD);
        LayoutPoste = view1.findViewById(R.id.LayoutPoste);
        DatePickerBtn = view1.findViewById(R.id.date_picker_btn);
        fab_main = view1.findViewById(R.id.fab);
        fab1_urgence = view1.findViewById(R.id.fab1);
        fab2_tm = view1.findViewById(R.id.fab2);
        fab3_sac = view1.findViewById(R.id.fab3);
        fab4_sad = view1.findViewById(R.id.fab4);
        fab5_poste = view1.findViewById(R.id.fab5);
        textview_urgence = view1.findViewById(R.id.textview_urgence);
        textview_tm = view1.findViewById(R.id.textview_tm);
        textview_sac = view1.findViewById(R.id.textview_sac);
        textview_sad = view1.findViewById(R.id.textview_sad);
        textview_poste = view1.findViewById(R.id.textview_poste);

        databaseMission = FirebaseDatabase.getInstance().getReference();

        urgencesList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();

        long today = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(today);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d-M-yyyy");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("d", Locale.FRANCE);
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("M", Locale.FRANCE);
        SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat("yyyy", Locale.FRANCE);

        day = Integer.parseInt(simpleDateFormat2.format(calendar.getTime()));
        month = Integer.parseInt(simpleDateFormat3.format(calendar.getTime()));
        year = Integer.parseInt(simpleDateFormat4.format(calendar.getTime()));
        String currentDate = simpleDateFormat.format(calendar.getTime());

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setSelection(today);
        builder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = builder.build();

        DatePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen)
                    close_fab();
                materialDatePicker.show(getFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d-M-yyyy", Locale.FRANCE);
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("d", Locale.FRANCE);
                SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("M", Locale.FRANCE);
                SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat("yyyy", Locale.FRANCE);

                day = Integer.parseInt(simpleDateFormat2.format(materialDatePicker.getSelection()));
                month = Integer.parseInt(simpleDateFormat3.format(materialDatePicker.getSelection()));
                year = Integer.parseInt(simpleDateFormat4.format(materialDatePicker.getSelection()));

                date = simpleDateFormat.format(materialDatePicker.getSelection());
                DatePickerBtn.setText(date);

                changeDate();
                if (isOpen)
                    close_fab();

            }
        });


        DatePickerBtn.setText(currentDate);
        date = currentDate.toString();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading data...");
        progressDialog.show();
        Query query = databaseMission.child("Urgence").orderByChild("currentDate").equalTo(date);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                urgencesList.clear();

                for (DataSnapshot urgenceSnapshot : dataSnapshot.getChildren()) {
                    Urgences urgences = urgenceSnapshot.getValue(Urgences.class);

                    urgencesList.add(urgences);
                }

                UrgenceTransportList adapter = new UrgenceTransportList(getActivity(), urgencesList);
                listViewRapport.setAdapter(adapter);
                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_rotate_anticlock);

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {
                    close_fab();
                } else {
                    open_fab();
                }

            }
        });

        fab1_urgence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), AddUrgence.class);
                startActivity(intent);
                close_fab();

            }
        });

        fab2_tm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent(getActivity(), AddTransportMalade.class);
                startActivity(intent2);
                close_fab();

            }
        });

        fab3_sac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent3 = new Intent(getActivity(), AddSoinAuCentre.class);
                startActivity(intent3);
                close_fab();

            }
        });

        fab4_sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent4 = new Intent(getActivity(), AddSoinADomicile.class);
                startActivity(intent4);
                close_fab();

            }
        });

        fab5_poste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent5 = new Intent(getActivity(), AddPoste.class);
                startActivity(intent5);
                close_fab();

            }
        });


        UrgenceBtn.setOnClickListener(this);
        TransportBtn.setOnClickListener(this);
        SacBtn.setOnClickListener(this);
        SadBtn.setOnClickListener(this);
        PosteBtn.setOnClickListener(this);
        DayBackBtn.setOnClickListener(this);
        DayFutureBtn.setOnClickListener(this);
        listViewRapport.setOnItemClickListener(this);
        view.setOnClickListener(this);

        return view1;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        LayoutInflater inflater = getLayoutInflater();

        if (isOpen)
            close_fab();

        if (buttonChoosed.equals("Urgence") || buttonChoosed.equals("Transport")) {
            Urgences mission = urgencesList.get(position);

            String alert1 = "Ambulance: " + mission.getAmbulance();
            String alert2 = "Region: " + mission.getRegion();
            String alert3 = "Patient name: " + mission.getPatientName();
            String alert4 = "Patient age: " + mission.getPatientAge();
            String alert5 = "Patient case: " + mission.getPatientCase();
            String alert6 = "Nationality: " + mission.getPatientNationality();
            String alert7 = "Companion: " + mission.getCompanion();
            String alert8 = "From: " + mission.getFrom();
            String alert9 = "To: " + mission.getTo();
            String alert10 = "" + mission.getSSP();
            String alert11 = "Ambulancier: " + mission.getAmbulancier();
            String alert12 = "CM: " + mission.getChefDeMission();
            String alert13 = "Secouriste 1: " + mission.getSecouriste1();
            String alert14 = "Secouriste 2: " + mission.getSecouriste2();
            String alert15 = "Report code: " + mission.getWrittenReportCode();
            String alert16 = "Notes: " + mission.getNotes();

            final View dialogView = inflater.inflate(R.layout.dialog_emergency_information, null);
            final TextView Ambulance = dialogView.findViewById(R.id.ambulanceDialog);
            final TextView Region = dialogView.findViewById(R.id.regionDialog);
            final TextView PatientName = dialogView.findViewById(R.id.patientNameDialog);
            final TextView PatientAge = dialogView.findViewById(R.id.patientAgeDialog);
            final TextView PatientCase = dialogView.findViewById(R.id.patientCaseDialog);
            final TextView Nationality = dialogView.findViewById(R.id.nationalityDialog);
            final TextView Companion = dialogView.findViewById(R.id.companionDialog);
            final TextView From = dialogView.findViewById(R.id.fromDialog);
            final TextView To = dialogView.findViewById(R.id.toDialog);
            final TextView Ssp = dialogView.findViewById(R.id.sspDialog);
            final TextView Ambulancier = dialogView.findViewById(R.id.ambulancierDialog);
            final TextView Cm = dialogView.findViewById(R.id.cmDialog);
            final TextView Secouriste1 = dialogView.findViewById(R.id.secouriste1Dialog);
            final TextView Secouriste2 = dialogView.findViewById(R.id.secouriste2Dialog);
            final TextView ReportCode = dialogView.findViewById(R.id.reportCodeDialog);
            final TextView Notes = dialogView.findViewById(R.id.notesDialog);

            if (buttonChoosed.equals("Urgence")) {
                identifier = mission.getId();

                Ambulance.setText(alert1);
                Region.setText(alert2);
                PatientName.setText(alert3);
                PatientAge.setText(alert4);
                PatientCase.setText(alert5);
                Nationality.setText(alert6);
                Companion.setText(alert7);
                From.setText(alert8);
                To.setText(alert9);
                Ssp.setText(alert10);
                Ambulancier.setText(alert11);
                Cm.setText(alert12);
                Secouriste1.setText(alert13);
                Secouriste2.setText(alert14);
                ReportCode.setText(alert15);
                Notes.setText(alert16);

                builder.setView(dialogView);

            } else if (buttonChoosed.equals("Transport")) {
                identifier = mission.getId();

                Ambulance.setText(alert1);
                Region.setText(alert2);
                PatientName.setText(alert3);
                PatientAge.setText(alert4);
                PatientCase.setText(alert5);
                Nationality.setText(alert6);
                Companion.setText(alert7);
                From.setText(alert8);
                To.setText(alert9);
                Ambulancier.setText(alert11);
                Cm.setText(alert12);
                Secouriste1.setText(alert13);
                Secouriste2.setText(alert14);
                ReportCode.setText(alert15);
                Notes.setText(alert16);

                builder.setView(dialogView);

            }
        } else if (buttonChoosed.equals("SAC")) {
            SoinAuCentre mission = soinAuCentreList.get(position);
            String alert1 = "Patient name: " + mission.getPatientName();
            String alert2 = "Patient age: " + mission.getPatientAge();
            String alert3 = "Patient case: " + mission.getPatientCase();
            String alert4 = "Nationality: " + mission.getPatientNationality();
            String alert5 = "Rescuers: " + mission.getRescuers();
            String alert6 = "Notes: " + mission.getNotes();

            final View dialogView = inflater.inflate(R.layout.dialog_emergency_sac, null);
            final TextView PatientName = dialogView.findViewById(R.id.patientNameDialog);
            final TextView PatientAge = dialogView.findViewById(R.id.patientAgeDialog);
            final TextView PatientCase = dialogView.findViewById(R.id.patientCaseDialog);
            final TextView Nationality = dialogView.findViewById(R.id.nationalityDialog);
            final TextView Rescuers = dialogView.findViewById(R.id.rescuersDialog);
            final TextView Notes = dialogView.findViewById(R.id.notesDialog);

            PatientName.setText(alert1);
            PatientAge.setText(alert2);
            PatientCase.setText(alert3);
            Nationality.setText(alert4);
            Rescuers.setText(alert5);
            Notes.setText(alert6);

            builder.setView(dialogView);

            identifier = mission.getId();
        } else if (buttonChoosed.equals("SAD")) {
            SoinADomicile mission = soinADomicileList.get(position);
            String alert1 = "Ambulance: " + mission.getAmbulance();
            String alert2 = "Region: " + mission.getRegion();
            String alert3 = "Patient name: " + mission.getPatientName();
            String alert4 = "Patient age: " + mission.getPatientAge();
            String alert5 = "Patient case: " + mission.getPatientCase();
            String alert6 = "Nationality: " + mission.getPatientNationality();
            String alert7 = "Location: " + mission.getLocation();
            String alert8 = "Ambulancier: " + mission.getAmbulancier();
            String alert9 = "CM: " + mission.getChefDeMission();
            String alert10 = "Secouriste 1: " + mission.getSecouriste1();
            String alert11 = "Secouriste 2: " + mission.getSecouriste2();
            String alert12 = "Notes: " + mission.getNotes();

            final View dialogView = inflater.inflate(R.layout.dialog_emergency_sad, null);
            final TextView Ambulance = dialogView.findViewById(R.id.ambulanceDialog);
            final TextView Region = dialogView.findViewById(R.id.regionDialog);
            final TextView PatientName = dialogView.findViewById(R.id.patientNameDialog);
            final TextView PatientAge = dialogView.findViewById(R.id.patientAgeDialog);
            final TextView PatientCase = dialogView.findViewById(R.id.patientCaseDialog);
            final TextView Nationality = dialogView.findViewById(R.id.nationalityDialog);
            final TextView Location = dialogView.findViewById(R.id.locationDialog);
            final TextView Ambulancier = dialogView.findViewById(R.id.ambulancierDialog);
            final TextView Cm = dialogView.findViewById(R.id.cmDialog);
            final TextView Secouriste1 = dialogView.findViewById(R.id.secouriste1Dialog);
            final TextView Secouriste2 = dialogView.findViewById(R.id.secouriste2Dialog);
            final TextView Notes = dialogView.findViewById(R.id.notesDialog);

            Ambulance.setText(alert1);
            Region.setText(alert2);
            PatientName.setText(alert3);
            PatientAge.setText(alert4);
            PatientCase.setText(alert5);
            Nationality.setText(alert6);
            Location.setText(alert7);
            Ambulancier.setText(alert8);
            Cm.setText(alert9);
            Secouriste1.setText(alert10);
            Secouriste2.setText(alert11);
            Notes.setText(alert12);

            builder.setView(dialogView);

            identifier = mission.getId();
        } else if (buttonChoosed.equals("Poste")) {
            Poste mission = posteList.get(position);
            String alert1 = "Ambulance: " + mission.getAmbulance();
            String alert2 = "Region: " + mission.getRegion();
            String alert3 = "Location: " + mission.getPosteLocation();
            String alert4 = "Type: " + mission.getPosteType();
            String alert5 = "Starting date: " + mission.getDate1();
            String alert6 = "Starting time: " + mission.getTime1();
            String alert7 = "Ending date: " + mission.getDate2();
            String alert8 = "Ending time " + mission.getTime2();
            String alert9 = "Team: " + mission.getTeam();
            String alert10 = "Notes: " + mission.getNotes();

            final View dialogView = inflater.inflate(R.layout.dialog_emergency_poste, null);
            final TextView Ambulance = dialogView.findViewById(R.id.ambulanceDialog);
            final TextView Region = dialogView.findViewById(R.id.regionDialog);
            final TextView Type = dialogView.findViewById(R.id.typeDialog);
            final TextView StartingDate = dialogView.findViewById(R.id.startingDateDialog);
            final TextView StartingTime = dialogView.findViewById(R.id.startingTimeDialog);
            final TextView EndingDate = dialogView.findViewById(R.id.endingDateDialog);
            final TextView EndingTime = dialogView.findViewById(R.id.endingTimeDialog);
            final TextView Location = dialogView.findViewById(R.id.locationDialog);
            final TextView Team = dialogView.findViewById(R.id.teamDialog);
            final TextView Notes = dialogView.findViewById(R.id.notesDialog);

            Ambulance.setText(alert1);
            Region.setText(alert2);
            Location.setText(alert3);
            Type.setText(alert4);
            StartingDate.setText(alert5);
            StartingTime.setText(alert6);
            EndingDate.setText(alert7);
            EndingTime.setText(alert8);
            Team.setText(alert9);
            Notes.setText(alert10);

            builder.setView(dialogView);

            identifier = mission.getId();
        }

        if (buttonChoosed.equals("Urgence")) {
            final Urgences mission = urgencesList.get(position);
            builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    showUpdateDialog(mission.getId(), mission.getCurrentDate(), mission.getCurrentTime(), mission.getAmbulance(), mission.getRegion(), mission.getPatientName(), mission.getPatientAge(), mission.getPatientCase(), mission.getPatientNationality(), mission.getCompanion(), mission.getAVP(), mission.getCasesNumber(), mission.getSSP(), mission.getFrom(), mission.getTo(), mission.getAmbulancier(), mission.getChefDeMission(), mission.getSecouriste1(), mission.getSecouriste2(), mission.getWrittenReportCode(), mission.getNotes());
                }
            });
        } else if (buttonChoosed.equals("Transport")) {
            final Urgences mission = urgencesList.get(position);
            builder.setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String a = "";
                    showUpdateDialog(mission.getId(), mission.getCurrentDate(), mission.getCurrentTime(), mission.getAmbulance(), mission.getRegion(), mission.getPatientName(), mission.getPatientAge(), mission.getPatientCase(), mission.getPatientNationality(), mission.getCompanion(), a, a, a, mission.getFrom(), mission.getTo(), mission.getAmbulancier(), mission.getChefDeMission(), mission.getSecouriste1(), mission.getSecouriste2(), mission.getWrittenReportCode(), mission.getNotes());
                }
            });
        }
        builder.setTitle("More information");
        builder.setPositiveButton("Done", null);
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MaterialAlertDialogBuilder builder2 = new MaterialAlertDialogBuilder(getActivity());
                builder2.setTitle("Delete mission");
                builder2.setMessage("This mission is going to be deleted forever, do you  want to continue?");
                builder2.setNegativeButton("Cancel", null);
                builder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMission(identifier);
                    }
                });
                builder2.show();

            }
        });
        builder.show();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.UrgenceBtn:
                if (isOpen)
                    close_fab();
                buttonChoosed = "Urgence";
                changeDate();
                break;

            case R.id.TransportBtn:
                if (isOpen)
                    close_fab();
                buttonChoosed = "Transport";
                changeDate();
                break;

            case R.id.SacBtn:

                buttonChoosed = "SAC";
                if (isOpen)
                    close_fab();
                changeDate();
                break;

            case R.id.SadBtn:
                buttonChoosed = "SAD";
                if (isOpen)
                    close_fab();
                changeDate();
                break;

            case R.id.PosteBtn:
                buttonChoosed = "Poste";
                if (isOpen)
                    close_fab();
                changeDate();
                break;

            case R.id.dayBackBtn:

                if (isOpen)
                    close_fab();

                if (day > 1)
                    day = day - 1;
                else {
                    if (month > 1) {
                        day = 31;
                        month = month - 1;
                    } else {
                        day = 31;
                        month = 12;
                        year = year - 1;
                    }
                }
                date = Integer.toString(day) + "-" + Integer.toString(month) + "-" + Integer.toString(year);
                DatePickerBtn.setText(date);
                changeDate();
                break;

            case R.id.dayFutureBtn:

                if (isOpen)
                    close_fab();

                if (day < 31)
                    day = day + 1;
                else {
                    if (month < 12) {
                        day = 1;
                        month = month + 1;
                    } else {
                        day = 1;
                        month = 1;
                        year = year + 1;
                    }
                }
                date = Integer.toString(day) + "-" + Integer.toString(month) + "-" + Integer.toString(year);
                DatePickerBtn.setText(date);
                changeDate();
                break;
            case R.id.view:
                if (isOpen)
                    close_fab();
                break;

        }

    }

    public void changeDate() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        if (buttonChoosed.equals("Urgence")) {
            LayoutUrgTrans.setVisibility(View.VISIBLE);
            LayoutSAC.setVisibility(View.INVISIBLE);
            LayoutSAD.setVisibility(View.INVISIBLE);
            LayoutPoste.setVisibility(View.INVISIBLE);


            urgencesList = new ArrayList<>();

            Query query1 = databaseMission.child("Urgence").orderByChild("currentDate").equalTo(date);
            query1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    urgencesList.clear();

                    for (DataSnapshot urgenceSnapshot : dataSnapshot.getChildren()) {
                        Urgences urgences = urgenceSnapshot.getValue(Urgences.class);

                        urgencesList.add(urgences);
                    }

                    UrgenceTransportList adapter = new UrgenceTransportList(getActivity(), urgencesList);
                    listViewRapport.setAdapter(adapter);
                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (buttonChoosed.equals("Transport")) {
            LayoutUrgTrans.setVisibility(View.VISIBLE);
            LayoutSAC.setVisibility(View.INVISIBLE);
            LayoutSAD.setVisibility(View.INVISIBLE);
            LayoutPoste.setVisibility(View.INVISIBLE);

            urgencesList = new ArrayList<>();

            Query query2 = databaseMission.child("Transport").orderByChild("currentDate").equalTo(date);
            query2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    urgencesList.clear();

                    for (DataSnapshot urgenceSnapshot : dataSnapshot.getChildren()) {
                        Urgences urgences = urgenceSnapshot.getValue(Urgences.class);

                        urgencesList.add(urgences);
                    }

                    UrgenceTransportList adapter = new UrgenceTransportList(getActivity(), urgencesList);
                    listViewRapport.setAdapter(adapter);
                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (buttonChoosed.equals("SAC")) {
            LayoutUrgTrans.setVisibility(View.INVISIBLE);
            LayoutSAC.setVisibility(View.VISIBLE);
            LayoutSAD.setVisibility(View.INVISIBLE);
            LayoutPoste.setVisibility(View.INVISIBLE);

            soinAuCentreList = new ArrayList<>();

            Query query3 = databaseMission.child("SAC").orderByChild("currentDate").equalTo(date);
            query3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    soinAuCentreList.clear();

                    for (DataSnapshot soinAuCentreSnapshot : dataSnapshot.getChildren()) {
                        SoinAuCentre soinAuCentre = soinAuCentreSnapshot.getValue(SoinAuCentre.class);

                        soinAuCentreList.add(soinAuCentre);
                    }

                    SoinAuCentreList adapter = new SoinAuCentreList(getActivity(), soinAuCentreList);
                    listViewRapport.setAdapter(adapter);
                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if (buttonChoosed.equals("SAD")) {
            LayoutUrgTrans.setVisibility(View.INVISIBLE);
            LayoutSAC.setVisibility(View.INVISIBLE);
            LayoutSAD.setVisibility(View.VISIBLE);
            LayoutPoste.setVisibility(View.INVISIBLE);

            soinADomicileList = new ArrayList<>();

            Query query3 = databaseMission.child("SAD").orderByChild("currentDate").equalTo(date);
            query3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    soinADomicileList.clear();

                    for (DataSnapshot soinADomicileSnapshot : dataSnapshot.getChildren()) {
                        SoinADomicile soinADomicile = soinADomicileSnapshot.getValue(SoinADomicile.class);

                        soinADomicileList.add(soinADomicile);
                    }

                    SoinADomicileList adapter = new SoinADomicileList(getActivity(), soinADomicileList);
                    listViewRapport.setAdapter(adapter);
                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else if (buttonChoosed.equals("Poste")) {
            LayoutUrgTrans.setVisibility(View.INVISIBLE);
            LayoutSAC.setVisibility(View.INVISIBLE);
            LayoutSAD.setVisibility(View.INVISIBLE);
            LayoutPoste.setVisibility(View.VISIBLE);

            posteList = new ArrayList<>();

            Query query5 = databaseMission.child("Poste").orderByChild("currentDate").equalTo(date);
            query5.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    posteList.clear();

                    for (DataSnapshot posteSnapshot : dataSnapshot.getChildren()) {
                        Poste poste = posteSnapshot.getValue(Poste.class);

                        posteList.add(poste);
                    }

                    PosteList adapter4 = new PosteList(getActivity(), posteList);
                    listViewRapport.setAdapter(adapter4);
                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void showUpdateDialog(final String id, final String currentDate, final String currentTime, final String ambulance, final String region, final String patientName, final String patientAge, final String patientCase, final String patientNationality, final String companion, final String AVP, final String casesNumber, final String SSP, final String from, final String to, final String ambulancier, final String chefDeMission, final String secouriste1, final String secouriste2, final String writtenReportCode, final String notes) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();

        String[] Ambulancier = getResources().getStringArray(R.array.Ambulancier);
        String[] ChefDeMission = getResources().getStringArray(R.array.CM);
        String[] Secouriste1 = getResources().getStringArray(R.array.Secouriste1);
        String[] Secoutiste2 = getResources().getStringArray(R.array.Secouriste2);

        final View dialogView = inflater.inflate(R.layout.activity_update, null);
        final EditText PatientNameTxt = dialogView.findViewById(R.id.PatientNameTxt);
        final EditText FromTxt = dialogView.findViewById(R.id.fromTxt);
        final EditText ToTxt = dialogView.findViewById(R.id.toTxt);
        final EditText PatientAgeTxt = dialogView.findViewById(R.id.PatientAgeTxt);
        final EditText PatientCaseTxt = dialogView.findViewById(R.id.PatientCaseTxt);
        final AutoCompleteTextView DropdownAmbulancier = dialogView.findViewById(R.id.dropdown_ambulancier);
        final AutoCompleteTextView DropdownCm = dialogView.findViewById(R.id.dropdown_cm);
        final AutoCompleteTextView DropdownSecouriste1 = dialogView.findViewById(R.id.dropdown_secouriste1);
        final AutoCompleteTextView DropdownSecouriste2 = dialogView.findViewById(R.id.dropdown_secouriste2);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, Ambulancier);
        DropdownAmbulancier.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, ChefDeMission);
        DropdownCm.setAdapter(adapter2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, Secouriste1);
        DropdownSecouriste1.setAdapter(adapter3);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, Secoutiste2);
        DropdownSecouriste2.setAdapter(adapter4);

        final Button UpdateBtn = dialogView.findViewById(R.id.updateBtn);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Edit");
        dialogBuilder.setIcon(R.drawable.edit_icon);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        PatientNameTxt.setText(patientName);
        PatientAgeTxt.setText(patientAge);
        PatientCaseTxt.setText(patientCase);
        FromTxt.setText(from);
        ToTxt.setText(to);
        DropdownAmbulancier.setText(ambulancier);
        DropdownCm.setText(chefDeMission);
        DropdownSecouriste1.setText(secouriste1);
        DropdownSecouriste2.setText(secouriste2);

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PatientName = PatientNameTxt.getText().toString();
                String PatientAge = PatientAgeTxt.getText().toString();
                String PatientCase = PatientCaseTxt.getText().toString();
                String From = FromTxt.getText().toString();
                String To = ToTxt.getText().toString();
                String Ambulancier = DropdownAmbulancier.getText().toString();
                String Cm = DropdownCm.getText().toString();
                String Secouriste1 = DropdownSecouriste1.getText().toString();
                String Secouriste2 = DropdownSecouriste2.getText().toString();

                if (From.equals("") || PatientName.equals("") || PatientAge.equals("") || PatientCase.equals("") || Ambulancier.equals("") || Cm.equals("")) {
                    Snackbar snackbar = Snackbar.make(v, "Please fill all the necessary boxes", Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    snackbar.show();
                } else {
                    if (buttonChoosed.equals("Urgence")) {
                        updateUrgence(id, currentDate, currentTime, ambulance, region, PatientName, PatientAge, PatientCase, patientNationality, companion, AVP, casesNumber, SSP, From, To, Ambulancier, Cm, Secouriste1, Secouriste2, writtenReportCode, notes);
                        alertDialog.dismiss();
                    } else if (buttonChoosed.equals("Transport")) {
                        updateTransport(id, currentDate, currentTime, ambulance, region, PatientName, PatientAge, PatientCase, patientNationality, companion, From, To, Ambulancier, Cm, Secouriste1, Secouriste2, writtenReportCode, notes);
                        alertDialog.dismiss();
                    }
                }


            }
        });
    }

    private boolean updateUrgence(String id, String currentDate, String currentTime, String ambulance, String region, String patientName, String patientAge, String patientCase, String patientNationality, String companion, String AVP, String casesNumber, String SSP, String from, String to, String ambulancier, String chefDeMission, String secouriste1, String secouriste2, String writtenReportCode, String notes) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Urgence").child(id);
        Urgences urgences = new Urgences(id, currentDate, currentTime, ambulance, region, patientName, patientAge, patientCase, patientNationality, companion, AVP, casesNumber, SSP, from, to, ambulancier, chefDeMission, secouriste1, secouriste2, writtenReportCode, notes);
        databaseReference.setValue(urgences);
        Toast.makeText(getActivity(), "Successfully edited", Toast.LENGTH_SHORT);
        return true;

    }

    private boolean updateTransport(String id, String currentDate, String currentTime, String ambulance, String region, String patientName, String patientAge, String patientCase, String patientNationality, String companion, String from, String to, String ambulancier, String chefDeMission, String secouriste1, String secouriste2, String writtenReportCode, String notes) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Transport").child(id);
        Transports transports = new Transports(id, currentDate, currentTime, ambulance, region, patientName, patientAge, patientCase, patientNationality, companion, from, to, ambulancier, chefDeMission, secouriste1, secouriste2, writtenReportCode, notes);
        databaseReference.setValue(transports);
        Toast.makeText(getActivity(), "Successfully edited", Toast.LENGTH_SHORT);

        return true;
    }

    private void deleteMission(String identifier) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Urgence").child(identifier);

        if (buttonChoosed.equals("Urgence"))
            databaseReference = FirebaseDatabase.getInstance().getReference("Urgence").child(identifier);
        else if (buttonChoosed.equals("Transport"))
            databaseReference = FirebaseDatabase.getInstance().getReference("Transport").child(identifier);
        else if (buttonChoosed.equals("SAC"))
            databaseReference = FirebaseDatabase.getInstance().getReference("SAC").child(identifier);
        else if (buttonChoosed.equals("SAD"))
            databaseReference = FirebaseDatabase.getInstance().getReference("SAD").child(identifier);
        else if (buttonChoosed.equals("Poste"))
            databaseReference = FirebaseDatabase.getInstance().getReference("Poste").child(identifier);

        databaseReference.removeValue();
        Toast.makeText(getActivity(), "Mission deleted", Toast.LENGTH_SHORT).show();
    }

}