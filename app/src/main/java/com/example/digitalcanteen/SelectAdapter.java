package com.example.digitalcanteen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saransh Verma on 23-05-2017.
 */

public class SelectAdapter extends ArrayAdapter {
    private static final String TAG = "SelectedItems";
    private static List<selectedItems> order = new ArrayList<>();
    Context con;

    public SelectAdapter(Context context, int resource, List<selectedItems> order) {
        super(context, resource);
        this.order = order;
        con = context;
    }

    @Override
    public int getCount() {
        return order.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View batman = inflater.inflate(R.layout.activity_selected, null, true);

        TextView txt1 = (TextView) batman.findViewById(R.id.selectedName);
        TextView txt2 = (TextView) batman.findViewById(R.id.selectedPrice);
        TextView txt3 = (TextView) batman.findViewById(R.id.selectedQuantity);

        txt1.setText(order.get(position).getName());
        txt2.setText(order.get(position).getPrice());
        txt3.setText(order.get(position).getQuantity());

        return batman;


    }
}
