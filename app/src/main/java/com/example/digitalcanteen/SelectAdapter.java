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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.digitalcanteen.MainPage.items;

/**
 * Created by Saransh Verma on 23-05-2017.
 */

public class SelectAdapter extends ArrayAdapter {
    private static final String TAG = "SelectedItems";
    private static List<selectedItems> order = new ArrayList<>();
    Context con;
    private Button remove;

    public SelectAdapter(Context context, int resource, List<selectedItems> order) {
        super(context, resource);
        SelectAdapter.order = order;
        con = context;
    }

    @Override
    public int getCount() {
        return order.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View batman = inflater.inflate(R.layout.activity_selected, null, true);
        Button remove = (Button) batman.findViewById(R.id.remove);
        TextView txt1 = (TextView) batman.findViewById(R.id.selectedName);
        TextView txt2 = (TextView) batman.findViewById(R.id.selectedPrice);
        TextView txt3 = (TextView) batman.findViewById(R.id.selectedQuantity);

        View.OnClickListener rm = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int x = 0; x < items.size(); x += 1) {
                    if (items.get(x).getName().equals(order.get(position).getName())) {
                        Log.d(TAG, "onClick: " + items.get(x).getName().equals(order.get(position).getName()));
                        Log.d(TAG, items.get(x).getName() + "      " + order.get(position).getName());
                        items.get(x).setQuantity(0);

                        break;
                    }
                }
                order.remove(position);
                MainPage.selectedItemsAdapter.notifyDataSetChanged();
                MainPage.renderMenuAdapter.notifyDataSetChanged();
            }
        };
        remove.setOnClickListener(rm);

        txt1.setText(order.get(position).getName());
        txt2.setText(order.get(position).getPrice());
        txt3.setText(order.get(position).getQuantity());

        return batman;


    }
}
