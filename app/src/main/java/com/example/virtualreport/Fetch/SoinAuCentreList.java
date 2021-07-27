package com.example.virtualreport.Fetch;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.virtualreport.Data.SoinAuCentre;
import com.example.virtualreport.R;

import java.util.List;

public class SoinAuCentreList extends ArrayAdapter<SoinAuCentre> {
    private Activity context;
    private List<SoinAuCentre> soinAuCentreList;

    public SoinAuCentreList(Activity context, List<SoinAuCentre> soinAuCentreList){
        super(context, R.layout.list_layout_soin_au_centre, soinAuCentreList);
        this.context=context;
        this.soinAuCentreList = soinAuCentreList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.list_layout_soin_au_centre,null,true);

        TextView PatientName=listViewItem.findViewById(R.id.PatientName);
        TextView PatientAge=listViewItem.findViewById(R.id.PatientAge);
        TextView Time=listViewItem.findViewById(R.id.Time);
        TextView Nationality=listViewItem.findViewById(R.id.Nationality);
        TextView Rescuers=listViewItem.findViewById(R.id.Rescuers);
        TextView PatientCase=listViewItem.findViewById(R.id.PatientCase);

        SoinAuCentre soinAuCentre= soinAuCentreList.get(position);

        PatientName.setText(soinAuCentre.getPatientName());
        PatientAge.setText(soinAuCentre.getPatientAge());
        Time.setText(soinAuCentre.getCurrentTime());
        Nationality.setText(soinAuCentre.getPatientNationality());
        Rescuers.setText(soinAuCentre.getRescuers());
        PatientCase.setText(soinAuCentre.getPatientCase());

        return listViewItem;
    }
}
