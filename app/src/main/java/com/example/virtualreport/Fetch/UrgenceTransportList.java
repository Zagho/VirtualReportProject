package com.example.virtualreport.Fetch;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.virtualreport.Data.Urgences;
import com.example.virtualreport.R;

import java.util.List;

public class UrgenceTransportList extends ArrayAdapter<Urgences> {
    private Activity context;
    private List<Urgences> urgencesList;

    public UrgenceTransportList(Activity context, List<Urgences> urgencesList){
        super(context, R.layout.list_layout,urgencesList);
        this.context=context;
        this.urgencesList=urgencesList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.list_layout,null,true);

        TextView PatientName=listViewItem.findViewById(R.id.PatientName);
        TextView PatientAge=listViewItem.findViewById(R.id.PatientAge);
        TextView Time=listViewItem.findViewById(R.id.Time);
        TextView Nationality=listViewItem.findViewById(R.id.Nationality);
        TextView Ssp=listViewItem.findViewById(R.id.Ssp);
        TextView ToLbl=listViewItem.findViewById(R.id.ToLbl);
        TextView Avp=listViewItem.findViewById(R.id.Avp);
        TextView PatientCase=listViewItem.findViewById(R.id.PatientCase);
        TextView From=listViewItem.findViewById(R.id.From);
        TextView To=listViewItem.findViewById(R.id.To);
        TextView Ambulancier=listViewItem.findViewById(R.id.Ambulancier);
        TextView Cm=listViewItem.findViewById(R.id.Cm);
        TextView Secouriste1=listViewItem.findViewById(R.id.Secouriste1);
        TextView Secouriste2=listViewItem.findViewById(R.id.Secouriste2);
        TextView Ambulance=listViewItem.findViewById(R.id.Ambulance);

        Urgences urgences=urgencesList.get(position);

        PatientName.setText(urgences.getPatientName());
        PatientAge.setText(" "+urgences.getPatientAge()+" ");
        Time.setText(urgences.getCurrentTime());
        Nationality.setText(urgences.getPatientNationality());

        Ssp.setText(" "+urgences.getSSP()+" ");
        if(urgences.getSSP()!=null) {
            if (urgences.getSSP().equals("")) {
                Ssp.setVisibility(View.INVISIBLE);
                ToLbl.setVisibility(View.VISIBLE);
            } else {
                Ssp.setVisibility(View.VISIBLE);
                ToLbl.setVisibility(View.INVISIBLE);
            }
        }else{
            Ssp.setVisibility(View.INVISIBLE);
        }

        Avp.setText(" "+urgences.getAVP()+" ");
        if(urgences.getAVP()!=null)
        {
            if(urgences.getAVP().equals("AVP"))
            {
                Avp.setVisibility(View.VISIBLE);
            }
            else
            {
                Avp.setVisibility(View.INVISIBLE);
            }
        }else{
            Avp.setVisibility(View.INVISIBLE);
        }


        PatientCase.setText(urgences.getPatientCase());
        From.setText(urgences.getFrom());
        To.setText(urgences.getTo());
        Ambulance.setText(" "+urgences.getAmbulance()+" ");
        Ambulancier.setText(urgences.getAmbulancier());
        Cm.setText(urgences.getChefDeMission());
        Secouriste1.setText(urgences.getSecouriste1());
        Secouriste2.setText(urgences.getSecouriste2());

        return listViewItem;
    }
}
