package com.example.virtualreport;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virtualreport.Data.Poste;
import com.example.virtualreport.Data.SoinADomicile;
import com.example.virtualreport.Data.SoinAuCentre;
import com.example.virtualreport.Data.Urgences;
import com.example.virtualreport.Fetch.RescuersList;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.example.virtualreport.Fetch.TasksList;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TasksFragment extends Fragment {

    private RecyclerView TasksRecycler;
    private DatabaseReference TasksData;
    private FloatingActionButton FabMain;
    private Button DoneBtn;

    Integer TodayDay = 0, TodayMonth = 0, TodayYear = 0;

    Calendar calendar = Calendar.getInstance();

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d-M-yyyy");
    String currentDate = simpleDateFormat.format(calendar.getTime());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        TasksData = FirebaseDatabase.getInstance().getReference().child("Tasks");
        TasksData.keepSynced(true);

        TasksRecycler = view.findViewById(R.id.recycler_tasks);
        FabMain=view.findViewById(R.id.fab);
        DoneBtn=view.findViewById(R.id.done_btn);

        TasksRecycler.setHasFixedSize(true);
        TasksRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,1));

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

                AddTask();

            }
        });

        return view;
    }

    private void AddTask() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();

        String[] Rescuer = getResources().getStringArray(R.array.Secouriste2);

        final View dialogView = inflater.inflate(R.layout.add_task, null);
        final AutoCompleteTextView RescuerName = dialogView.findViewById(R.id.rescuer_name_txt);
        final Button AddTaskBtn = dialogView.findViewById(R.id.add_task_btn);
        final EditText Deadline = dialogView.findViewById(R.id.deadline);
        final EditText Title = dialogView.findViewById(R.id.title_txt);
        final EditText Text = dialogView.findViewById(R.id.text_txt);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.dropdown_item, Rescuer);
        RescuerName.setAdapter(adapter);

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Add task");
        dialogBuilder.setIcon(R.drawable.black_add_icon);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        AddTaskBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String RescuerNameTxt = RescuerName.getText().toString();
                final String DeadlineTxt=Deadline.getText().toString();
                final String CurrentDateTxt=currentDate;
                final String TitleTxt=Title.getText().toString();
                final String TextTxt= Text.getText().toString();

                if (RescuerNameTxt.equals("") || DeadlineTxt.equals("") || TitleTxt.equals("")||TextTxt.equals("")) {
                    Snackbar snackbar = Snackbar.make(v, "Please fill all the necessary boxes", Snackbar.LENGTH_LONG);
                    snackbar.setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    snackbar.show();
                } else {

                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tasks");
                    String IdTxt=reference.push().getKey();
                    TasksList tasksList = new TasksList(TitleTxt, TextTxt, RescuerNameTxt, DeadlineTxt, CurrentDateTxt,IdTxt);
                    reference.child(IdTxt).setValue(tasksList);
                    Toast.makeText(getActivity(), "Added successfully", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();


                }
            }
        });


    }

    private void fetch() {

        Query query = FirebaseDatabase.getInstance().getReference().child("Tasks");

        FirebaseRecyclerOptions<TasksList> options = new FirebaseRecyclerOptions.Builder<TasksList>()
                .setQuery(query, TasksList.class).build();

        FirebaseRecyclerAdapter<TasksList, TasksListViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<TasksList, TasksListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TasksListViewHolder tasksListViewHolder, int i, @NonNull TasksList tasksList) {
                tasksListViewHolder.setName(tasksList.getRescuerName());
                tasksListViewHolder.setText(tasksList.getText());
                tasksListViewHolder.setDate(tasksList.getDate());
                tasksListViewHolder.setTitle(tasksList.getTitle());
            }

            @NonNull
            @Override
            public TasksListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_card, parent, false);

                return new TasksListViewHolder(view1);
            }
        };


        TasksRecycler.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();


    }

    public static class TasksListViewHolder extends RecyclerView.ViewHolder {

        View view;

        public TasksListViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setTitle(String title) {
            TextView Title = view.findViewById(R.id.task_title);
            Title.setText(title);
        }

        public void setText(String text) {
            TextView Text = view.findViewById(R.id.tasks_text);
            Text.setText(text);
        }

        public void setName(String name) {
            TextView RescuerName = view.findViewById(R.id.rescuer_task_name);
            RescuerName.setText(name);
        }

        public void setDate(String date) {
            TextView Date = view.findViewById(R.id.task_date);
            Date.setText(date);
        }
    }
}