package com.example.digitalcanteen.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.digitalcanteen.R;
import com.example.digitalcanteen.adapter.collectionAdapter;
import com.example.digitalcanteen.dataObjects.Collection;
import com.example.digitalcanteen.database.CollectionDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Collections extends AppCompatActivity {
    private static final String TAG = "CollectionsClass";
    public collectionAdapter CollectionAdapter;
    int currDay, currMonth, currYear;
    int endDay, endMonth, endYear;
    int strtDay, strtMonth, strtYear;
    private List<Collection> collectionList = new ArrayList<>();
    private CollectionDatabase collectionDb;
    private ListView collectionsView = null;
    private TextView strtView;
    private TextView endView;
    //    private Button
    private Button btnStartDate;
    private Button btnEndDate;
    private String strtDate, endDate;
    private Button btnGenerate;
    private Double TOTAL = 0.0;
    private TextView showTotal;
    private Button exitCollection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        collectionsView = (ListView) findViewById(R.id.collectionList);
        strtView = (TextView) findViewById(R.id.collectionStartText);
        endView = (TextView) findViewById(R.id.collectionsEndText);
        btnStartDate = (Button) findViewById(R.id.collectionStart);
        btnEndDate = (Button) findViewById(R.id.collectionEnd);
        btnGenerate = (Button) findViewById(R.id.btnGenerate);
        showTotal = (TextView) findViewById(R.id.showTotal);
        exitCollection = (Button) findViewById(R.id.exitCollections);

        exitCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exitThis = new Intent(Collections.this, AdminActivity.class);
                startActivity(exitThis);
                finish();
            }
        });
        java.text.DateFormat formatterM = new java.text.SimpleDateFormat("MM");
        java.text.DateFormat formatterD = new java.text.SimpleDateFormat("dd");
        java.text.DateFormat formatterY = new java.text.SimpleDateFormat("yyyy");


        Date today = new Date();
        currDay = Integer.parseInt(formatterD.format(today));
        currMonth = Integer.parseInt(formatterM.format(today));
//        currMonth+=;
        currYear = Integer.parseInt(formatterY.format(today));

        String formatterDD = String.format("%02d", currDay);
        String formatterMM = String.format("%02d", currMonth);

        strtView.setText("" + formatterDD + "/" + formatterMM + "/" + currYear + "");
        endView.setText("" + formatterDD + "/" + formatterMM + "/" + currYear + "");

        strtDate = "" + currYear + "-" + formatterMM + "-" + formatterDD;
        endDate = "" + currYear + "-" + formatterMM + "-" + formatterDD;

        collectionDb = new CollectionDatabase(Collections.this);
        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.d(TAG, "onDateSet: Do something with date here");

//                        numTimes = 0;
                        strtDay = dayOfMonth;
                        strtMonth = month + 1;
                        strtYear = year;
//                        map.clear();
//                        sales.clear();

//                        adapterForAccounts.notifyDataSetChanged();

                        String formatterD = String.format("%02d", strtDay);
                        String formatterM = String.format("%02d", strtMonth);
                        strtView.setText("" + formatterD + "/" + formatterM + "/" + year + "");
                        strtDate = "" + strtYear + "-" + formatterM + "-" + formatterD;
                        Log.d(TAG, "onDateSet: " + strtDate);

                    }
                };
                final DatePickerDialog dialog = new DatePickerDialog(v.getContext(), datePickerListener, currYear, currMonth - 1, currDay);
                dialog.show();
            }
        });

        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.d(TAG, "onDateSet: Do something with date here");
//                        endDateBox.setText("" + dayOfMonth + "/" + month + "/" + year + "");
//                        numTimes = 0;
                        endDay = dayOfMonth;
                        endMonth = month + 1;
                        endYear = year;
//                        map.clear();
//                        sales.clear();

//                        adapterForAccounts.notifyDataSetChanged();

                        String eformatterD = String.format("%02d", endDay);
                        String eformatterM = String.format("%02d", endMonth);
                        endView.setText("" + eformatterD + "/" + eformatterM + "/" + year + "");
                        endDate = "" + endYear + "-" + eformatterM + "-" + eformatterD;
                        Log.d(TAG, "onDateSet: " + endDate);

                    }
                };
                final DatePickerDialog dialog = new DatePickerDialog(v.getContext(), datePickerListener, currYear, currMonth - 1, currDay);
                dialog.show();
            }
        });

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionList = collectionDb.getAllHistory(strtDate, endDate);
                for (int i = 0; i < collectionList.size(); i += 1) {
                    TOTAL += collectionList.get(i).getCollection();
                }
                showTotal.setText("Total:- " + String.valueOf(TOTAL));
                View header = getLayoutInflater().inflate(R.layout.collection_template, null);
                collectionsView.addHeaderView(header);

                collectionAdapter listAdapter = new collectionAdapter(Collections.this, R.layout.collection_template, collectionList);
                collectionsView.setAdapter(listAdapter);


            }
        });


    }
}
