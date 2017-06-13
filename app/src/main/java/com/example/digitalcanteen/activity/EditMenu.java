package com.example.digitalcanteen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.digitalcanteen.R;
import com.example.digitalcanteen.adapter.EditAdapter;
import com.example.digitalcanteen.dataObjects.menuItem;
import com.example.digitalcanteen.database.MenuDatabase;

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
//        Log.d(TAG, "onCreate: " + MainPage.items.size());
        db = new MenuDatabase(this);
//        db.insertItem("pizza",89.0);
//        db.insertItem("burger",89.0);
        eItems = db.getAll();
//        eItems=MainPage.items;'
//        for (int i = 0; i < MainPage.items.size(); i += 1) {
//            Log.d(TAG, "onCreate: " + MainPage.items.get(i).getName());
//            eItems.add(new menuItem(MainPage.items.get(i).getName(), MainPage.items.get(i).getPrice(), MainPage.items.get(i).getId()));

//        }
        Log.d(TAG, "onCreate: " + eItems.size());

        currItems = (ListView) findViewById(R.id.listView);
        editItemsAdapter = new EditAdapter(EditMenu.this, R.layout.edititem, eItems);
        currItems.setAdapter(editItemsAdapter);

        newitem = (Button) findViewById(R.id.addItem);

        View.OnClickListener toAdd = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(EditMenu.this,AddItem.class);
                startActivity(add);
                finish();
            }
        };
        newitem.setOnClickListener(toAdd);


    }
}
