package com.example.digitalcanteen;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saransh Verma on 01-06-2017.
 */

public class acccountAdapter extends ArrayAdapter {
    private static final String TAG="AccountAdapter";
    private static List<EHistory> EmpHistory = new ArrayList<>();
//    private int EId;
    Context con;
    private TextView name;
    private TextView cpi;
    private TextView eId;
    private TextView date;
    private TextView total;
    private TextView quantity;

    public acccountAdapter(@NonNull Context context, @LayoutRes int resource,List<EHistory> EmpHistory) {

        super(context, resource);
        this.con = context;
        acccountAdapter.EmpHistory = EmpHistory;
    }

    @Override
    public int getCount() {
        return EmpHistory.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View damian = inflater.inflate(R.layout.template_accounts,null,true);

        TextView name =(TextView) damian.findViewById(R.id.tempItemName);
        TextView cpi =(TextView) damian.findViewById(R.id.tempCPI);
        TextView eId = (TextView) damian.findViewById(R.id.tempEId);
        TextView date =(TextView) damian.findViewById(R.id.tempDate);
        TextView total=(TextView) damian.findViewById(R.id.tempTotal);
        TextView quantity=(TextView) damian.findViewById(R.id.tempQuantity);

//        int i=EmpHistory.size()-1;
        Log.d(TAG, "getView: " + EmpHistory.get(position).getName());
        name.setText(EmpHistory.get(position).getName());
        cpi.setText("" + EmpHistory.get(position).getCpi() + "");
        eId.setText(EmpHistory.get(position).getEmployeeCode());
        date.setText(EmpHistory.get(position).getDate().toString());
        total.setText("" + EmpHistory.get(position).getTotal() + "");
        quantity.setText(EmpHistory.get(position).getQuantity());

        return damian;
            }
}
