package com.example.digitalcanteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ListView listItems;
    private ListView selectedThings;
    private List<menuItem> items = new ArrayList<>();
    public static List<selectedItems> order = new ArrayList<>();


    private Button btnExit = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);


        btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToLogin = new Intent(MainPage.this, LoginActivity.class);
                startActivity(redirectToLogin);
                finish();
            }
        });

        listItems = (ListView) findViewById(R.id.lstMenu);
        selectedThings = (ListView) findViewById(R.id.lstCart);


//       order.add(new selectedItems("pizza", "2", "600"));
        items.add(new menuItem("pizza", "300"));
        items.add(new menuItem("burger", "100"));

        selectedThings.setAdapter(new SelectAdapter(MainPage.this, R.layout.activity_selected, order));

        //Log.d(TAG, "onCreate: i am batman");


        listItems.setAdapter(new MenuAdapter(MainPage.this, R.layout.activity_layout_menu, items));




    }
}
