package com.example.digitalcanteen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
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
    private TextView txt1;
    private TextView txt2;
    private EditText quantity;
    private Button btplus;
    private Button btminus;
    private Button buttonOK;
    public int[] numTimesClicked = new int[items.size()];


    public MenuAdapter(Context context, int resource, List<menuItem> items) {
        super(context, resource);
        int[] arraytest = new int[items.size()];
        Arrays.fill(arraytest, 0);
        this.numTimesClicked = arraytest;

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
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.activity_layout_menu, null, true);

        TextView txt1 = (TextView) row.findViewById(R.id.name);
        TextView txt2 = (TextView) row.findViewById(R.id.price);
        Button btplus = (Button) row.findViewById(R.id.add);
        Button btminus = (Button) row.findViewById(R.id.sub);
        final EditText quantity = (EditText) row.findViewById(R.id.quantity);
        Button buttonOk = (Button) row.findViewById(R.id.OK);
        //numTimesClicked=0;


        View.OnClickListener addition = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String p = quantity.getText().toString();
                numTimesClicked[position] = Integer.parseInt(p);

                numTimesClicked[position] = numTimesClicked[position] + 1;
                String result = "" + numTimesClicked[position] + "";
                quantity.setText(result);
                items.get(position).setQuantity(numTimesClicked[position]);
                Log.d(TAG, "getView: " + items.get(position).getQuantity());


            }
        };

        View.OnClickListener substraction = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numTimesClicked[position] > 0) {
                    String p = quantity.getText().toString();
                    numTimesClicked[position] = Integer.parseInt(p);

                    numTimesClicked[position] = numTimesClicked[position] - 1;
                    String result = "" + numTimesClicked[position] + "";
                    quantity.setText(result);
                    items.get(position).setQuantity(numTimesClicked[position]);
//                    Log.d(TAG, "getView: "+items.get(position).getQuantity());


                }
            }
        };
        btplus.setOnClickListener(addition);
        btminus.setOnClickListener(substraction);
        View.OnClickListener OKK = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namee;
                String pricee;
                int price1;
                int no;
                int totalPrice;
                String tP;
                String q;

                namee = items.get(position).getName();
                pricee = items.get(position).getPrice();
                price1 = Integer.parseInt(pricee);
                no = items.get(position).getQuantity();
                q = "" + no + "";
                totalPrice = no * price1;
                tP = "" + totalPrice + "";
                selectedItems thing = new selectedItems(namee, q, tP);
                SelectAdapter.order.add(thing);


            }
        };
        buttonOK.setOnClickListener(OKK);


        txt1.setText(items.get(position).getName());
        txt2.setText(items.get(position).getPrice());
//        String p=quantity.getText().toString();
//        int i= Integer.parseInt(p);
//        items.get(position).setQuantity(i);
//        Log.d(TAG, "getView: "+items.get(position).getQuantity());

//        Log.d(TAG, "getView:"+position);
//        Log.d(TAG, "getView:"+numTimesClicked[position]);
//


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
