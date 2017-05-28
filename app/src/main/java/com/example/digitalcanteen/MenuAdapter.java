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

import static com.example.digitalcanteen.MainPage.order;


/**
 * Created by Saransh Verma on 23-05-2017.
 */

public class MenuAdapter extends ArrayAdapter {
    private static final String TAG = "MenuAdapter";
    //    private final int layoutResource;
//    private final LayoutInflater layoutInflater;
    private static List<menuItem> items = new ArrayList<>();
    public static int[] numTimesClicked = new int[items.size()];
    public EditText quantity;
    Context con;
    private TextView txt1;
    private TextView txt2;
    private Button btplus;
    private Button btminus;
//    private Button buttonOK;


    public MenuAdapter(Context context, int resource, List<menuItem> items) {
        super(context, resource);
        Log.d(TAG, "MenuAdapter: started");

        int[] arraytest = new int[items.size()];
        Arrays.fill(arraytest, 0);
//        for (int x = 0; x < items.size(); x += 1) {
//            arraytest[x] = items.get(x).getQuantity();
//        }
        numTimesClicked = arraytest;

//        this.layoutResource = resource;
//        this.layoutInflater = LayoutInflater.from(context);
        MenuAdapter.items = items;
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
        Log.d(TAG, "getView: started");
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.activity_layout_menu, null, true);

        TextView txt1 = (TextView) row.findViewById(R.id.name);
        TextView txt2 = (TextView) row.findViewById(R.id.price);
        Button btplus = (Button) row.findViewById(R.id.add);
        Button btminus = (Button) row.findViewById(R.id.sub);
        final EditText quantity = (EditText) row.findViewById(R.id.quantity);

//        for (int x = 0; x < items.size(); x += 1) {
//            numTimesClicked[x] = items.get(x).getQuantity();
//        }
        quantity.setText(numTimesClicked[position] + "");
//        Button buttonOK = (Button) row.findViewById(R.id.OK);
        //numTimesClicked=0;


        View.OnClickListener addition = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String p = quantity.getText().toString();
                numTimesClicked[position] = Integer.parseInt(p);

                numTimesClicked[position] = numTimesClicked[position] + 1;
                String result = "" + numTimesClicked[position] + "";
                quantity.setText(result);
//                Log.d(TAG, "onClick: " + items.get(position).getQuantity());
//                items.get(position).setQuantity(numTimesClicked[position]);
//                Log.d(TAG, "onClick: " + items.get(position).getQuantity());
                //Log.d(TAG, "getView: " + items.get(position).getQuantity());
                if (order.size() > 0) {
                    int flag = 0;
                    for (int z = 0; z < order.size(); z += 1) {
                        Log.d(TAG, "onClick: " + items.get(z).getName());
                        if (items.get(position).getName() == (order.get(z).getName())) {
                            Log.d(TAG, "onClick: fl0 " + items.get(position).getName());
                            order.get(z).setQuantity("" + numTimesClicked[position] + "");

                            int tempPrice;
                            int cpi = Integer.parseInt(items.get(position).getPrice());

                            tempPrice = numTimesClicked[position] * cpi;

                            order.get(z).setPrice("" + tempPrice + "");
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        Log.d(TAG, "onClick: fl " + items.get(position).getName());
                        order.add(new selectedItems(items.get(position).getName(), numTimesClicked[position] + "", ((Integer.parseInt(items.get(position).getPrice())) * numTimesClicked[position]) + ""));
                    }
                } else {
                    Log.d(TAG, "onClick: fl2 " + items.get(position).getName());
                    order.add(new selectedItems(items.get(position).getName(), numTimesClicked[position] + "", ((Integer.parseInt(items.get(position).getPrice())) * numTimesClicked[position]) + ""));
                }

                MainPage.selectedItemsAdapter.notifyDataSetChanged();

            }
        };

        btplus.setOnClickListener(addition);

        View.OnClickListener substraction = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numTimesClicked[position] > 0) {
                    String p = quantity.getText().toString();
                    numTimesClicked[position] = Integer.parseInt(p);

                    numTimesClicked[position] = numTimesClicked[position] - 1;
                    String result = "" + numTimesClicked[position] + "";
                    quantity.setText(result);
//                    items.get(position).setQuantity(numTimesClicked[position]);
//                    Log.d(TAG, "getView: "+items.get(position).getQuantity());
                    int flag = 0;
                    for (int z = 0; z < order.size(); z += 1) {
                        if (items.get(position).getName().equals(order.get(z).getName())) {

                            if (numTimesClicked[position] != 0) {
                                order.get(z).setQuantity("" + numTimesClicked[position] + "");
                                int tempPrice = Integer.parseInt(order.get(z).getPrice());
                                int cpi = Integer.parseInt(items.get(position).getPrice());

                                tempPrice = numTimesClicked[position] * cpi;

                                order.get(z).setPrice("" + tempPrice + "");
                                flag = 1;
                            } else {
                                order.remove(z);
                            }
                            break;
                        }
                    }
//                    if(flag==0){order.add(new selectedItems(items.get(position).getName(),"1",items.get(position).getPrice()));}

                    MainPage.selectedItemsAdapter.notifyDataSetChanged();
                }
            }
        };
        btminus.setOnClickListener(substraction);

//        View.OnClickListener OKK = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name;
//                String price;
//                int price1;
//                int no;
//                int totalPrice;
//                String tP;
//                String q;
//
//                name = items.get(position).getName();
//                price = items.get(position).getPrice();
//                price1 = Integer.parseInt(price);
//                no = items.get(position).getQuantity();
//                q = "" + no + "";
//                totalPrice = no * price1;
//                tP = "" + totalPrice + "";
//                selectedItems thing = new selectedItems("", "", "");
//                thing.setName(name);
//                thing.setPrice(tP);
//                thing.setQuantity(q);
//                order.add(thing);
//                MainPage.selectedItemsAdapter.notifyDataSetChanged();
//                Log.d(TAG, "onClick: " + order.get(position).getPrice());
//                Log.d(TAG, "onClick: thing added to selected items");
//
//
//            }
//        };
//        buttonOK.setOnClickListener(OKK);


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
