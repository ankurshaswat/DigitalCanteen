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
import com.example.digitalcanteen.dataObjects.Collection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saransh Verma on 22-06-2017.
 */

public class collectionAdapter extends ArrayAdapter {
    private static final String TAG = "CollectionAdapter";
    private static List<Collection> collectionList = new ArrayList<>();
    //    private int EId;
    Context con;

    public collectionAdapter(@NonNull Context context, @LayoutRes int resource, List<Collection> collectionList) {
//        super(context, resource, objects);
        super(context, resource);
        this.con = context;
        collectionAdapter.collectionList = collectionList;
    }

    @Override
    public int getCount() {
        return collectionList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View bruce = inflater.inflate(R.layout.collection_template, null, true);
        TextView colDate = (TextView) bruce.findViewById(R.id.collectionsDate);
        TextView colTotal = (TextView) bruce.findViewById(R.id.collectionsTotal);

        colDate.setText(collectionList.get(position).getDate());
        colTotal.setText(String.valueOf(collectionList.get(position).getCollection()));

        return bruce;

    }
}
