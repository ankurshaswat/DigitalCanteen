package com.example.digitalcanteen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EditMenu extends AppCompatActivity {
    private static final String TAG = "EditMenu";
    public static List<menuItem> eItems = new ArrayList<>();
    public static EditAdapter editItemsAdapter = null;
    private ListView currItems;
    private Button newitem;
    private MenuDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);
        Log.d(TAG, "onCreate: " + MainPage.items.size());

//        eItems=MainPage.items;'
        for (int i = 0; i < MainPage.items.size(); i += 1) {
            Log.d(TAG, "onCreate: " + MainPage.items.get(i).getName());
            eItems.add(new menuItem(MainPage.items.get(i).getName(), MainPage.items.get(i).getPrice(), MainPage.items.get(i).getId()));

        }
        Log.d(TAG, "onCreate: " + eItems.size());

        currItems = (ListView) findViewById(R.id.listView);
        editItemsAdapter = new EditAdapter(EditMenu.this, R.layout.edititem, eItems);
        currItems.setAdapter(editItemsAdapter);


    }
}
