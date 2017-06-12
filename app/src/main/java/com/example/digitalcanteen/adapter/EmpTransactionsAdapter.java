package com.example.digitalcanteen.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.digitalcanteen.R;
import com.example.digitalcanteen.dataObjects.EHistory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saransh Verma on 13-06-2017.
 */

public class EmpTransactionsAdapter extends ArrayAdapter {
    private static final String TAG="AccountAdapter";
    private static List<EHistory> EmpHistory = new ArrayList<>();
    //    private int EId;
    Context con;
    private TextView name;
    private TextView cpi;

    private TextView date;
    private TextView total;
    private TextView quantity;

    public EmpTransactionsAdapter(@NonNull Context context, @LayoutRes int resource, List<EHistory> EmpHistory) {

        super(context, resource);
        this.con = context;
        this.EmpHistory = EmpHistory;
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
        View damian = inflater.inflate(R.layout.empaccounttemplate, null, true);

        TextView name =(TextView) damian.findViewById(R.id.tempItemName2);
        TextView cpi =(TextView) damian.findViewById(R.id.tempCPI2);
//        TextView eId = (TextView) damian.findViewById(R.id.tempEId);
        TextView date =(TextView) damian.findViewById(R.id.tempDate2);
        TextView total=(TextView) damian.findViewById(R.id.tempTotal2);
        TextView quantity=(TextView) damian.findViewById(R.id.tempQuantity2);

//        int i=EmpHistory.size()-1;
//        Log.d(TAG, "getView: " + EmpHistory.get(position).getName());
        name.setText(EmpHistory.get(position).getName());
        cpi.setText("" + EmpHistory.get(position).getCpi() + "");
//        eId.setText(EmpHistory.get(position).getEmployeeCode());
        date.setText(EmpHistory.get(position).getDate());
//        Log.d(TAG, "getView: " + EmpHistory.get(position).getDate());
        total.setText("" + EmpHistory.get(position).getTotal() + "");
//        Log.d(TAG, "getView: "+EmpHistory.get(position).getQuantity());
        quantity.setText(String.valueOf(EmpHistory.get(position).getQuantity()));

        return damian;
    }
}
