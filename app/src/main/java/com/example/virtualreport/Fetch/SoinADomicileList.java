package com.example.virtualreport.Fetch;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.virtualreport.Data.SoinADomicile;
import com.example.virtualreport.Data.Urgences;
import com.example.virtualreport.R;

import java.util.List;

public class SoinADomicileList extends ArrayAdapter<SoinADomicile> {
    private Activity context;
    private List<SoinADomicile> soinADomicileList;

    public SoinADomicileList(Activity context, List<SoinADomicile> soinADomicileList) {
        super(context, R.layout.list_layout_soin_a_domicile, soinADomicileList);
        this.context = context;
        this.soinADomicileList = soinADomicileList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout_soin_a_domicile, null, true);

        TextView PatientName = listViewItem.findViewById(R.id.PatientName);
        TextView PatientAge = listViewItem.findViewById(R.id.PatientAge);
        TextView Time = listViewItem.findViewById(R.id.Time);
        TextView Nationality = listViewItem.findViewById(R.id.Nationality);
        TextView PatientCase = listViewItem.findViewById(R.id.PatientCase);
        TextView Location = listViewItem.findViewById(R.id.Location);
        TextView Ambulancier = listViewItem.findViewById(R.id.Ambulancier);
        TextView Cm = listViewItem.findViewById(R.id.Cm);
        TextView Secouriste1 = listViewItem.findViewById(R.id.Secouriste1);
        TextView Secouriste2 = listViewItem.findViewById(R.id.Secouriste2);
        TextView Ambulance = listViewItem.findViewById(R.id.Ambulance);

        SoinADomicile soinADomicile = soinADomicileList.get(position);

        PatientName.setText(soinADomicile.getPatientName());
        PatientAge.setText(soinADomicile.getPatientAge());
        Time.setText(soinADomicile.getCurrentTime());
        Nationality.setText(soinADomicile.getPatientNationality());
        PatientCase.setText(soinADomicile.getPatientCase());
        Location.setText(soinADomicile.getLocation());
        Ambulance.setText(soinADomicile.getAmbulance());
        Ambulancier.setText(soinADomicile.getAmbulancier());
        Cm.setText(soinADomicile.getChefDeMission());
        Secouriste1.setText(soinADomicile.getSecouriste1());
        Secouriste2.setText(soinADomicile.getSecouriste2());

        return listViewItem;
    }
}
