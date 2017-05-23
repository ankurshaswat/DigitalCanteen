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

public class MenuAdapter extends ArrayAdapter {
    private static final String TAG = "MenuAdapter";
    //    private final int layoutResource;
//    private final LayoutInflater layoutInflater;
    private List<menuItem> items = new ArrayList<>();
    Context con;


    public MenuAdapter(Context context, int resource, List<menuItem> items) {
        super(context, resource);
//        this.layoutResource = resource;
//        this.layoutInflater = LayoutInflater.from(context);
        this.items = items;
        con = context;

    }


//    public MenuAdapter(@NonNull Context context, @LayoutRes int resource, int layoutResource, LayoutInflater layoutInflater, List<menuItem> items) {
//        super(context, resource);
//        this.layoutResource = layoutResource;
//        this.layoutInflater = layoutInflater;
//        this.items = items;
//    }

    @Override
    public int getCount() {
        return items.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.activity_layout_menu, null, true);

        TextView txt1 = (TextView) row.findViewById(R.id.name);
        TextView txt2 = (TextView) row.findViewById(R.id.price);

        txt1.setText(items.get(position).getName());
        txt2.setText(items.get(position).getPrice());


        return row;

    }


//    private class ViewHolder {
//        private TextView name;
//        private TextView price;
//
//        ViewHolder(View v) {
//            this.name = (TextView) v.findViewById(R.id.name);
//            this.price = (TextView) v.findViewById(R.id.price);
//

}
