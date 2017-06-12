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
import com.example.digitalcanteen.dataObjects.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saransh Verma on 12-06-2017.
 */

public class BalanceAdapter extends ArrayAdapter {

    private static final String TAG="BalanceAdapter";
    private static List<Employee> eBalance = new ArrayList<>();
    Context con;
    private TextView name=null;
    private TextView balance = null;

    public BalanceAdapter(@NonNull Context context, @LayoutRes int resource,List<Employee> eBalance)  {
        super(context, resource);
        this.eBalance=eBalance;
        this.con=context;

    }

    @Override
    public int getCount() {
        return eBalance.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View raven = inflater.inflate(R.layout.template_balance,null,true);
        TextView name=(TextView) raven.findViewById(R.id.balanceName);
        TextView balance = (TextView) raven.findViewById(R.id.balanceBalance);

        name.setText(eBalance.get(position).getEmployee_name());
        balance.setText(String.valueOf(eBalance.get(position).getBalance()));
        return raven;
    }
}
