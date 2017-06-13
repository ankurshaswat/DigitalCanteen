package com.example.digitalcanteen.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.digitalcanteen.R;
import com.example.digitalcanteen.activity.EditMenu;
import com.example.digitalcanteen.dataObjects.menuItem;
import com.example.digitalcanteen.database.MenuDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saransh Verma on 27-05-2017.
 */

public class EditAdapter extends ArrayAdapter {
    public static final String TAG = "EditAdapter";
    private static List<menuItem> eItems = new ArrayList<>();
    public TextView name;
    Context con;
    private TextView price;
    private Button edit;
    private Button rem;
    private MenuDatabase db;
    public EditAdapter(@NonNull Context context, @LayoutRes int resource, List<menuItem> Items) {
        super(context, resource);
        EditAdapter.eItems = Items;
        con = context;

    }

    @Override
    public int getCount() {
        return eItems.size();


    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        Log.d(TAG, "getView: i was here");
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View ww = inflater.inflate(R.layout.edititem, null, true);
        TextView name = (TextView) ww.findViewById(R.id.currName);
        TextView price = (TextView) ww.findViewById(R.id.currPrice);
        Button edit = (Button) ww.findViewById(R.id.buttonEdit);
        Button rem = (Button) ww.findViewById(R.id.buttonRemove);
        db = new MenuDatabase(con);
        name.setText(eItems.get(position).getName());
        price.setText(eItems.get(position).getPrice());

        View.OnClickListener rm = new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                db.deleteItem(eItems.get(position).getName());
//attach testing to deleted or not.......
                EditMenu.eItems.remove(position);
                EditMenu.editItemsAdapter.notifyDataSetChanged();

////////IMPORTANT TODO notify update to adapter
            }
        };
        rem.setOnClickListener(rm);

        View.OnClickListener editI = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder qBuilder = new AlertDialog.Builder(getContext());
                LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View qView = li.inflate(R.layout.dialog_edit_menu_item, null);

                final EditText newPrIce = (EditText) qView.findViewById(R.id.newVal);
                TextView enterName = (TextView) qView.findViewById(R.id.enterName);
                enterName.setText("Write new Price for " + eItems.get(position).getName());

                Button OK = (Button) qView.findViewById(R.id.OKbutton);
                Button cancel = (Button) qView.findViewById(R.id.cancelButton);

                qBuilder.setView(qView);
                final AlertDialog dialog = qBuilder.create();
                dialog.show();


                OK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        db.editItem(eItems.get(position).getId(), eItems.get(position).getName(), Double.parseDouble(newPrIce.getText().toString()));

                        EditMenu.eItems.get(position).setPrice(newPrIce.getText().toString());
                        EditMenu.editItemsAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }

                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
//                Intent redirectToMM = new Intent(con, ChangeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                redirectToMM.putExtra("name", eItems.get(position).getName());
//                redirectToMM.putExtra("price", eItems.get(position).getPrice());
//                redirectToMM.putExtra("id", eItems.get(position).getId());
//                con.startActivity(redirectToMM);
            }
        };
        edit.setOnClickListener(editI);

        return ww;
    }
}
