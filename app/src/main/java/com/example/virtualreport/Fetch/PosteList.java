package com.example.virtualreport.Fetch;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.virtualreport.Data.Poste;
import com.example.virtualreport.Data.Urgences;
import com.example.virtualreport.R;

import java.util.List;

public class PosteList extends ArrayAdapter<Poste> {
    private Activity context;
    private List<Poste> posteList;

    public PosteList(Activity context, List<Poste> posteList){
        super(context, R.layout.list_layout_poste, posteList);
        this.context=context;
        this.posteList = posteList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.list_layout_poste,null,true);

        TextView PosteLocation=listViewItem.findViewById(R.id.PosteLocation);
        TextView PosteType=listViewItem.findViewById(R.id.PosteType);
        TextView Time=listViewItem.findViewById(R.id.Time);
        TextView Date1=listViewItem.findViewById(R.id.Date1);
        TextView Time1=listViewItem.findViewById(R.id.Time1);
        TextView Date2=listViewItem.findViewById(R.id.Date2);
        TextView Time2=listViewItem.findViewById(R.id.Time2);
        TextView Team=listViewItem.findViewById(R.id.Team);
        TextView Ambulance=listViewItem.findViewById(R.id.Ambulance);

        Poste poste= posteList.get(position);

        PosteLocation.setText(poste.getPosteLocation());
        PosteType.setText(poste.getPosteType());
        Time.setText(poste.getCurrentTime());
        Date1.setText(poste.getDate1());
        Time1.setText(poste.getTime1());
        Date2.setText(poste.getDate2());
        Time2.setText(poste.getTime2());
        Team.setText(poste.getTeam());
        Ambulance.setText(poste.getAmbulance());

        return listViewItem;
    }
}
