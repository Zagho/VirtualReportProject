package com.example.virtualreport;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virtualreport.Data.Urgences;
import com.example.virtualreport.Fetch.RescuersList;
import com.example.virtualreport.Fetch.UrgenceTransportList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class RescuerFragment extends Fragment implements View.OnClickListener {

    private RecyclerView ReservisteRecycler, RescuerRecycler, LongReservisteRecycler;
    private DatabaseReference ReservisteData;
    private FloatingActionButton FabMain, FabReserviste, FabRescuer;
    private TextView TextviewReserviste, TextviewRescuer;
    private View myView;
    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
    private LinearLayout Lin1,Lin2,Lin3;

    Boolean isOpen = false;
    List<String> RescuerNameList = new ArrayList<>();
    List<String> ReservisteNameList = new ArrayList<>();
    String MyName = "", MyFullName = "", MyAmbulancier = "", MyCm = "", MyDossard = "", MyLocation = "", MyPhoneNumber = "", MName = "", MFullName = "", MAmbulancier = "", MCm = "", MDossard = "", MLocation = "", MPhoneNumber = "", MIsCurrentlyReserviste = "";

    Integer MyDay = 1, MyMonth = 1, MyYear = 2020, MyRenewDay = 1, MyRenewMonth = 1, MyRenewYear = 2021, MyRemainingReserviste = 3, MyPromo = 1, TodayDay = 0, TodayMonth = 0, TodayYear = 0, MDay = 1, MMonth = 1, MYear = 2020, MRenewDay = 1, MRenewMonth = 1, MRenewYear = 2021, MRemainingReserviste = 3, MPromo = 1;

    public void close_fab() {
        TextviewRescuer.setVisibility(View.INVISIBLE);
        TextviewReserviste.setVisibility(View.INVISIBLE);
        FabRescuer.startAnimation(fab_close);
        FabReserviste.startAnimation(fab_close);
        FabMain.startAnimation(fab_anticlock);
        FabReserviste.setClickable(false);
        FabRescuer.setClickable(false);
        myView.setClickable(false);
        isOpen = false;
    }

    public void open_fab() {
        TextviewRescuer.setVisibility(View.VISIBLE);
        TextviewReserviste.setVisibility(View.VISIBLE);
        FabRescuer.startAnimation(fab_open);
        FabReserviste.startAnimation(fab_open);
        FabMain.startAnimation(fab_clock);
        FabReserviste.setClickable(true);
        FabRescuer.setClickable(true);
        myView.setClickable(true);
        isOpen = true;
    }

    Calendar calendar = Calendar.getInstance();

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d-M-yyyy");
    String currentDate = simpleDateFormat.format(calendar.getTime());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rescuer, container, false);

        ReservisteData = FirebaseDatabase.getInstance().getReference().child("Rescuers");
        ReservisteData.keepSynced(true);

        ReservisteRecycler = view.findViewById(R.id.recycler_reserviste);
        RescuerRecycler = view.findViewById(R.id.recycler_rescuer);
        LongReservisteRecycler = view.findViewById(R.id.recycler_long_reserviste);
        FabMain = view.findViewById(R.id.fab);
        FabRescuer = view.findViewById(R.id.fab1);
        FabReserviste = view.findViewById(R.id.fab2);
        TextviewRescuer = view.findViewById(R.id.textview_rescuer);
        TextviewReserviste = view.findViewById(R.id.textview_reserviste);
        myView = view.findViewById(R.id.view);

        fab_close = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.fab_rotate_anticlock);

        myView.setVisibility(View.INVISIBLE);

        ReservisteRecycler.setHasFixedSize(true);
        ReservisteRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        RescuerRecycler.setHasFixedSize(true);
        RescuerRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        LongReservisteRecycler.setHasFixedSize(true);
        LongReservisteRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();

        long today = MaterialDatePicker.todayInUtcMilliseconds();
        calendar.setTimeInMillis(today);

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("d", Locale.FRANCE);
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("M", Locale.FRANCE);
        SimpleDateFormat simpleDateFormat4 = new SimpleDateFormat("yyyy", Locale.FRANCE);

        TodayDay = Integer.parseInt(simpleDateFormat2.format(calendar.getTime()));
        TodayMonth = Integer.parseInt(simpleDateFormat3.format(calendar.getTime()));
        TodayYear = Integer.parseInt(simpleDateFormat4.format(calendar.getTime()));


        fetch();

        FabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {
                    close_fab();
                    myView.setVisibility(View.INVISIBLE);
                } else {
                    open_fab();
                    myView.setVisibility(View.VISIBLE);
                }

            }
        });

        FabRescuer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddRescuer();
                close_fab();

            }
        });

        FabReserviste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddReserviste();
                close_fab();

            }
        });

        myView.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.view:
                if (isOpen)
                    close_fab();
                break;
        }
    }

    private void AddRescuer() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();

        String[] Rescuer = getResources().getStringArray(R.array.Secouriste2);

        final View dialogView = inflater.inflate(R.layout.add_rescuer, null);
        final AutoCompleteTextView RescuerName = dialogView.findViewById(R.id.rescuer_name);
        final EditText Location = dialogView.findViewById(R.id.address);
        final EditText PhoneNumber = dialogView.findViewById(R.id.phone_number);
        final EditText Promo = dialogView.findViewById(R.id.promo);
        final EditText FullName = dialogView.findViewById(R.id.full_name);
        final CheckBox Ambulancier = dialogView.findViewById(R.id.ambulancier_chk);
        final CheckBox Cm = dialogView.findViewById(R.id.cm_chk);
        final CheckBox Dossard = dialogView.findViewById(R.id.dossard_chk);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, Rescuer);
        RescuerName.setAdapter(adapter);

        final Button AddRescuerBtn = dialogView.findViewById(R.id.add_rescuer_btn);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Add rescuer");
        dialogBuilder.setIcon(R.drawable.black_add_icon);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        AddRescuerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String RescuerNameTxt = RescuerName.getText().toString();
                final Integer DayTxt = 0;
                final Integer MonthTxt = 0;
                final Integer YearTxt = 0;
                final Integer RenewDayTxt = 0;
                final Integer RenewMonthTxt = 0;
                final Integer RenewYearTxt = 0;
                final Integer ReservisteRemainingTxt = 3;
                final String IsCurrentlyReservisteTxt = "false";
                final String IsLongReservisteTxt = "false";
                final String LocationTxt = Location.getText().toString();
                final String PhoneNumberTxt = PhoneNumber.getText().toString();
                final String FullNameTxt = FullName.getText().toString();
                final Integer PromoTxt = Integer.parseInt(Promo.getText().toString());
                final String AmbulancierTxt;
                final String CmTxt;
                final String DossardTxt;
                if (Ambulancier.isChecked())
                    AmbulancierTxt = "true";
                else
                    AmbulancierTxt = "false";

                if (Cm.isChecked())
                    CmTxt = "true";
                else
                    CmTxt = "false";

                if (Dossard.isChecked())
                    DossardTxt = "true";
                else
                    DossardTxt = "false";

                if (RescuerNameTxt.equals("") || LocationTxt.equals("") || PhoneNumberTxt.equals("") || PromoTxt.equals("") || FullNameTxt.equals("")) {
                    Snackbar snackbar = Snackbar.make(v, "Please fill all the necessary boxes", Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    snackbar.show();

                } else if (RescuerNameList.contains(RescuerNameTxt)) {
                    Snackbar snackbar = Snackbar.make(v, "This rescuer already exists", Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    snackbar.show();
                } else {

                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Rescuers").child(RescuerNameTxt);

                    RescuersList rescuersList = new RescuersList(RescuerNameTxt, DayTxt, RenewDayTxt, MonthTxt, RenewMonthTxt, YearTxt, RenewYearTxt, ReservisteRemainingTxt, IsCurrentlyReservisteTxt, IsLongReservisteTxt, LocationTxt, PhoneNumberTxt, PromoTxt, FullNameTxt, AmbulancierTxt, CmTxt, DossardTxt);
                    reference.setValue(rescuersList);

                    Toast.makeText(getActivity(), "Added successfully", Toast.LENGTH_SHORT).show();

                    alertDialog.dismiss();

                }
            }
        });

    }

    private void AddReserviste() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();

        final String[] Rescuer = RescuerNameList.toArray(new String[0]);
        final String[] Reserviste = ReservisteNameList.toArray(new String[0]);

        final View dialogView = inflater.inflate(R.layout.add_reserviste, null);
        final AutoCompleteTextView RescuerName = dialogView.findViewById(R.id.rescuer_name);
        final Button AddReservisteBtn = dialogView.findViewById(R.id.add_reserviste_btn);
        final EditText ReservisteDate = dialogView.findViewById(R.id.reserviste_date);
        final RadioButton LongReserviste = dialogView.findViewById(R.id.long_reserviste_rdb);
        final RadioButton SmallReserviste = dialogView.findViewById(R.id.small_reserviste_rdb);
        final Button CheckBtn = dialogView.findViewById(R.id.check_btn);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, Rescuer);
        RescuerName.setAdapter(adapter);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Add reserviste");
        dialogBuilder.setIcon(R.drawable.black_add_icon);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        CheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (RescuerName.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar.make(v, "Please fill all the name box", Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    snackbar.show();
                } else {


                    Query query = FirebaseDatabase.getInstance().getReference().child("Rescuers").child(RescuerName.getText().toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            RescuersList rescuersList = dataSnapshot.getValue(RescuersList.class);
                            MyName = rescuersList.getRescuerName();
                            MyDay = rescuersList.getDay();
                            MyMonth = rescuersList.getMonth();
                            MyYear = rescuersList.getYear();
                            MyRenewDay = rescuersList.getRenewDay();
                            MyRenewMonth = rescuersList.getRenewMonth();
                            MyRenewYear = rescuersList.getRenewYear();
                            MyRemainingReserviste = rescuersList.getReservisteRemaining();
                            MyPromo = rescuersList.getPromo();
                            MyFullName = rescuersList.getFullName();
                            MyAmbulancier = rescuersList.getAmbulancier();
                            MyCm = rescuersList.getCm();
                            MyDossard = rescuersList.getDossard();
                            MyLocation = rescuersList.getLocation();
                            MyPhoneNumber = rescuersList.getPhoneNumber();
                            if (MyRemainingReserviste == 0) {
                                Snackbar snackbar = Snackbar.make(v, "This rescuer has no remaining reserviste", Snackbar.LENGTH_LONG);
                                snackbar.setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                                snackbar.show();
                            } else if (Arrays.asList(Reserviste).contains(RescuerName.getText().toString())) {
                                Snackbar snackbar = Snackbar.make(v, "This rescuer is currently reserviste", Snackbar.LENGTH_LONG);
                                snackbar.setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                                snackbar.show();
                            } else if (!Arrays.asList(Rescuer).contains(RescuerName.getText().toString())) {
                                Snackbar snackbar = Snackbar.make(v, "Create this rescuer before adding a reserviste to him", Snackbar.LENGTH_LONG);
                                snackbar.setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                                snackbar.show();
                            } else {
                                ReservisteDate.setEnabled(true);
                                LongReserviste.setEnabled(true);
                                SmallReserviste.setEnabled(true);
                                AddReservisteBtn.setEnabled(true);
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        AddReservisteBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String RescuerNameTxt = RescuerName.getText().toString();
                final Integer DayTxt;
                final Integer MonthTxt;
                final Integer YearTxt;
                final Integer RenewDayTxt;
                final Integer RenewMonthTxt;
                final Integer RenewYearTxt;
                final Integer ReservisteRemainingTxt;
                final String IsCurrentlyReservisteTxt;
                final String IsLongReservisteTxt;

                Date date;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                try {
                    if (!ReservisteDate.equals(""))
                        date = simpleDateFormat.parse(ReservisteDate.getText().toString());
                    else {
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.DAY_OF_MONTH, 1);
                        cal.set(Calendar.MONTH, 1);
                        cal.set(Calendar.YEAR, 2020);
                        date = cal.getTime();
                    }
                } catch (ParseException ex) {
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    cal.set(Calendar.MONTH, 1);
                    cal.set(Calendar.YEAR, 2020);
                    date = cal.getTime();
                }


                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                Integer day = calendar.get(Calendar.DAY_OF_MONTH);
                Integer month = calendar.get(Calendar.MONTH);
                Integer year = calendar.get(Calendar.YEAR);

                if (RescuerNameTxt.equals("") || ReservisteDate.getText().equals("") || (!LongReserviste.isChecked() && !SmallReserviste.isChecked())) {
                    Snackbar snackbar = Snackbar.make(v, "Please fill all the necessary boxes", Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    snackbar.show();
                } else {

                    DayTxt = day;
                    if (month == 11) {
                        YearTxt = year + 1;
                        MonthTxt = 1;
                    } else {
                        MonthTxt = month + 2;
                        YearTxt = year;
                    }
                    ReservisteRemainingTxt = MyRemainingReserviste - 1;
                    if (MyRenewDay == 0) {
                        RenewDayTxt = day;
                        RenewMonthTxt = month + 1;
                        RenewYearTxt = year + 1;

                    } else {
                        RenewDayTxt = MyRenewDay;
                        RenewMonthTxt = MyRenewMonth;
                        RenewYearTxt = MyRenewYear;
                    }


                    if (LongReserviste.isChecked()) {
                        IsLongReservisteTxt = "true";
                        IsCurrentlyReservisteTxt = "false";
                    } else {
                        IsLongReservisteTxt = "false";
                        IsCurrentlyReservisteTxt = "true";
                    }


                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Rescuers").child(RescuerNameTxt);
                    RescuersList rescuersList = new RescuersList(RescuerNameTxt, DayTxt, RenewDayTxt, MonthTxt, RenewMonthTxt, YearTxt, RenewYearTxt, ReservisteRemainingTxt, IsCurrentlyReservisteTxt, IsLongReservisteTxt, MyLocation, MyPhoneNumber, MyPromo, MyFullName, MyAmbulancier, MyCm, MyDossard);
                    reference.setValue(rescuersList);
                    Toast.makeText(getActivity(), "Added successfully", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();


                }
            }
        });

    }

    public static class RescuersListViewHolder extends RecyclerView.ViewHolder {
        View view;

        public RescuersListViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setName(String name) {
            TextView RescuerName = view.findViewById(R.id.rescuer_name);
            RescuerName.setText(name);
        }

        public void setRemaining(String remaining) {
            TextView NumberRemaining = view.findViewById(R.id.number_remaining);
            NumberRemaining.setText(remaining);
        }

        public void setEndingDate(String endingDate) {
            TextView EndingDate = view.findViewById(R.id.ending_date);
            EndingDate.setText(endingDate);
        }

        public void setBeginningDate(String endingDate) {
            TextView EndingDate = view.findViewById(R.id.beginning_date);
            EndingDate.setText(endingDate);
        }

        public void setFullName(String endingDate) {
            TextView EndingDate = view.findViewById(R.id.full_name_lbl);
            EndingDate.setText(endingDate);
        }

        public void setPromo(String endingDate) {
            TextView EndingDate = view.findViewById(R.id.promo_txt);
            EndingDate.setText(endingDate);
        }

        public void setPhone(String endingDate) {
            TextView EndingDate = view.findViewById(R.id.phone_number_lbl);
            EndingDate.setText(endingDate);
        }

        public void setLocation(String endingDate) {
            TextView EndingDate = view.findViewById(R.id.address_lbl);
            EndingDate.setText(endingDate);
        }

        public void setRenewDate(String endingDate) {
            TextView EndingDate = view.findViewById(R.id.renew_date);
            EndingDate.setText(endingDate);
        }

        public void setAmbulancier(String endingDate) {
            ImageView Ambulancier = view.findViewById(R.id.ambulancier_icon);
            if (endingDate.equals("true"))
                Ambulancier.setVisibility(View.VISIBLE);

        }

        public void setCm(String endingDate) {
            ImageView Cm = view.findViewById(R.id.cm_icon);
            if (endingDate.equals("true"))
                Cm.setVisibility(View.VISIBLE);
        }

        public void setDossard(String endingDate) {
            ImageView Dossard = view.findViewById(R.id.dossard_icon);
            if (endingDate.equals("true"))
                Dossard.setVisibility(View.VISIBLE);
        }

    }

    private void fetch() {

        RescuerNameList.clear();
        ReservisteNameList.clear();

        Query query = FirebaseDatabase.getInstance().getReference().child("Rescuers").orderByChild("isCurrentlyReserviste").equalTo("true");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                    RescuersList rescuersList = snapshot1.getValue(RescuersList.class);
                    MName = rescuersList.getRescuerName();
                    MDay = rescuersList.getDay();
                    MMonth = rescuersList.getMonth();
                    MYear = rescuersList.getYear();
                    MRenewDay = rescuersList.getRenewDay();
                    MRenewMonth = rescuersList.getRenewMonth();
                    MRenewYear = rescuersList.getRenewYear();
                    MRemainingReserviste = rescuersList.getReservisteRemaining();
                    MPromo = rescuersList.getPromo();
                    MFullName = rescuersList.getFullName();
                    MAmbulancier = rescuersList.getAmbulancier();
                    MCm = rescuersList.getCm();
                    MDossard = rescuersList.getDossard();
                    MLocation = rescuersList.getLocation();
                    MPhoneNumber = rescuersList.getPhoneNumber();

                    if (TodayYear > MRenewYear && TodayMonth>MRenewMonth && TodayDay>MRenewDay) {
                        MRemainingReserviste = 3;
                        MRenewYear = 0;
                        MRenewMonth = 0;
                        MRenewDay = 0;
                    }

                    if (TodayYear > MYear && MMonth == 1 && TodayDay > MDay) {
                        MIsCurrentlyReserviste = "false";
                        MYear = 0;
                        MMonth = 0;
                        MDay = 0;
                    } else if (TodayMonth > MMonth && TodayDay > MDay) {
                        MIsCurrentlyReserviste = "false";
                        MYear = 0;
                        MMonth = 0;
                        MDay = 0;
                    } else
                        MIsCurrentlyReserviste = "true";


                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Rescuers").child(MName);
                    RescuersList rescuersList2 = new RescuersList(MName, MDay, MRenewDay, MMonth, MRenewMonth, MYear, MRenewYear, MRemainingReserviste, MIsCurrentlyReserviste, "false", MLocation, MPhoneNumber, MPromo, MFullName, MAmbulancier, MCm, MDossard);
                    reference.setValue(rescuersList2);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Query query1 = FirebaseDatabase.getInstance().getReference().child("Rescuers");
        Query query2 = FirebaseDatabase.getInstance().getReference().child("Rescuers").orderByChild("isCurrentlyReserviste").equalTo("true");
        Query query3 = FirebaseDatabase.getInstance().getReference().child("Rescuers").orderByChild("isLongReserviste").equalTo("true");

        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RescuerNameList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String MyName = snapshot1.child("rescuerName").getValue().toString();
                    RescuerNameList.add(MyName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReservisteNameList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String MyName = snapshot1.child("rescuerName").getValue().toString();
                    ReservisteNameList.add(MyName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        query3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String MyName = snapshot1.child("rescuerName").getValue().toString();
                    ReservisteNameList.add(MyName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseRecyclerOptions<RescuersList> options1 = new FirebaseRecyclerOptions.Builder<RescuersList>()
                .setQuery(query1, RescuersList.class).build();
        FirebaseRecyclerOptions<RescuersList> options2 = new FirebaseRecyclerOptions.Builder<RescuersList>()
                .setQuery(query2, RescuersList.class).build();
        FirebaseRecyclerOptions<RescuersList> options3 = new FirebaseRecyclerOptions.Builder<RescuersList>()
                .setQuery(query3, RescuersList.class).build();

        FirebaseRecyclerAdapter<RescuersList, RescuersListViewHolder> firebaseRecyclerAdapter2 = new FirebaseRecyclerAdapter<RescuersList, RescuersListViewHolder>
                (options1) {
            @Override
            protected void onBindViewHolder(@NonNull RescuersListViewHolder rescuersListViewHolder, int i, @NonNull RescuersList rescuersList) {
                rescuersListViewHolder.setName(rescuersList.getRescuerName());
                rescuersListViewHolder.setRemaining(rescuersList.getReservisteRemaining().toString());
                if (rescuersList.getRenewDay().equals(0))
                    rescuersListViewHolder.setRenewDate("No date yet");
                else
                    rescuersListViewHolder.setRenewDate(rescuersList.getRenewDay().toString() + "-" + rescuersList.getRenewMonth().toString() + "-" + rescuersList.getRenewYear().toString());
                rescuersListViewHolder.setPromo(rescuersList.getPromo().toString());
                rescuersListViewHolder.setPhone(rescuersList.getPhoneNumber());
                rescuersListViewHolder.setAmbulancier(rescuersList.getAmbulancier());
                rescuersListViewHolder.setCm(rescuersList.getCm());
                rescuersListViewHolder.setDossard(rescuersList.getDossard());
                rescuersListViewHolder.setFullName(rescuersList.getFullName());
                rescuersListViewHolder.setLocation(rescuersList.getLocation());
            }

            @NonNull
            @Override
            public RescuersListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.rescuer_card, parent, false);

                return new RescuersListViewHolder(view1);
            }
        };
        FirebaseRecyclerAdapter<RescuersList, RescuersListViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RescuersList, RescuersListViewHolder>
                (options2) {
            @Override
            protected void onBindViewHolder(@NonNull RescuersListViewHolder rescuersListViewHolder, int i, @NonNull RescuersList rescuersList) {
                rescuersListViewHolder.setName(rescuersList.getRescuerName());
                rescuersListViewHolder.setRemaining(rescuersList.getReservisteRemaining().toString());
                rescuersListViewHolder.setEndingDate(rescuersList.getDay().toString() + "-" + rescuersList.getMonth().toString() + "-" + rescuersList.getYear().toString());
            }

            @NonNull
            @Override
            public RescuersListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.reserviste_card, parent, false);

                return new RescuersListViewHolder(view1);
            }
        };
        FirebaseRecyclerAdapter<RescuersList, RescuersListViewHolder> firebaseRecyclerAdapter3 = new FirebaseRecyclerAdapter<RescuersList, RescuersListViewHolder>
                (options3) {
            @Override
            protected void onBindViewHolder(@NonNull RescuersListViewHolder rescuersListViewHolder, int i, @NonNull RescuersList rescuersList) {
                rescuersListViewHolder.setName(rescuersList.getRescuerName());
                rescuersListViewHolder.setBeginningDate(rescuersList.getDay().toString() + "-" + (Integer.parseInt(rescuersList.getMonth().toString()) - 1) + "-" + rescuersList.getYear().toString());
            }

            @NonNull
            @Override
            public RescuersListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.long_reserviste_card, parent, false);

                return new RescuersListViewHolder(view1);
            }
        };

        RescuerRecycler.setAdapter(firebaseRecyclerAdapter2);
        ReservisteRecycler.setAdapter(firebaseRecyclerAdapter);
        LongReservisteRecycler.setAdapter(firebaseRecyclerAdapter3);
        firebaseRecyclerAdapter.startListening();
        firebaseRecyclerAdapter2.startListening();
        firebaseRecyclerAdapter3.startListening();


    }

}