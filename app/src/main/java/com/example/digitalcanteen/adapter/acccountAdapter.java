package com.example.digitalcanteen.adapter;

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

import com.example.digitalcanteen.R;
import com.example.digitalcanteen.dataObjects.Sale;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saransh Verma on 01-06-2017.
 */

public class acccountAdapter extends ArrayAdapter {
    private static final String TAG="AccountAdapter";
    private static List<Sale> sales = new ArrayList<>();
//    private int EId;
    Context con;
//    private TextView name;
//    private TextView cpi;
//    private TextView eId;
//    private TextView date;
//    private TextView total;
//    private TextView quantity;

    public acccountAdapter(@NonNull Context context, @LayoutRes int resource, List<Sale> EmpHistory) {

        super(context, resource);
        this.con = context;
        acccountAdapter.sales = EmpHistory;
        Log.d(TAG, "acccountAdapter: ");
    }

    @Override
    public int getCount() {
        return sales.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d(TAG, "getView: " + sales.get(position).toString());
//        Integer size = AccountsActivity.salesSize();
//        Log.d(TAG, "getView: "+size);
        Log.d(TAG, "getView:" + TAG);
//        return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View damian = inflater.inflate(R.layout.accounttemplate, null, true);

        TextView name =(TextView) damian.findViewById(R.id.tempItemName);
        TextView cpi =(TextView) damian.findViewById(R.id.tempCPI);
//        TextView eId = (TextView) damian.findViewById(R.id.tempEId);
//        TextView date =(TextView) damian.findViewById(R.id.tempDate);
        TextView total=(TextView) damian.findViewById(R.id.tempTotal);
        TextView quantity=(TextView) damian.findViewById(R.id.tempQuantity);

//        int i=EmpHistory.size()-1;
//        Log.d(TAG, "getView: " + EmpHistory.get(position).getName());
        name.setText(sales.get(position).getName());
        cpi.setText("" + sales.get(position).getCpi() + "");
//        eId.setText(.get(position).getEmployeeCode());
//        date.setText(EmpHistory.get(position).getDate());
//        Log.d(TAG, "getView: " + EmpHistory.get(position).getDate());
        total.setText("" + sales.get(position).getTotal() + "");
//        Log.d(TAG, "getView: "+EmpHistory.get(position).getQuantity());
        quantity.setText(String.valueOf(sales.get(position).getQuan()));

        return damian;
            }
}
