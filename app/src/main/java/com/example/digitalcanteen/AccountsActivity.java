package com.example.digitalcanteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AccountsActivity extends AppCompatActivity {
    private static final String TAG = "AccountsActivity";
    public static List<EHistory> EmployeeHistory = new ArrayList<>();
    public static acccountAdapter adapterForAccounts = null;
    private Button btnGenerate = null;
    private Button btnDone = null;
    private EditText strtDateBox = null;
    private EditText endDateBox = null;
    private ListView transactions=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        final TransactionDatabase db = new TransactionDatabase(this);
        btnGenerate = (Button) findViewById(R.id.btnGenerate);

        transactions = (ListView) findViewById(R.id.accntList);

        EmployeeHistory = db.getAll();
        View header = getLayoutInflater().inflate(R.layout.accounttemplate, null);
        transactions.addHeaderView(header);


        adapterForAccounts = new acccountAdapter(AccountsActivity.this, R.layout.accounttemplate, EmployeeHistory);
//        EmployeeHistory = db.getAll();
//        adapterForAccounts.notifyDataSetChanged();
//
//  transactions.setAdapter(adapterForAccounts);
        transactions.setAdapter(adapterForAccounts);
        btnDone = (Button) findViewById(R.id.btnDone);
        strtDateBox = (EditText) findViewById(R.id.editStartDate);
        endDateBox = (EditText) findViewById(R.id.editEndDate);

        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Make sure user insert date into edittext in this format.

                Date strDateObject, endDateObject;
                String strtDate, endDate = null;

                try {
                    String dob_var = (strtDateBox.getText().toString());
                    strDateObject = formatter.parse(dob_var);
                    strtDate = new SimpleDateFormat("yyyy-MM-dd").format(strDateObject);

                    dob_var = (endDateBox.getText().toString());
                    endDateObject = formatter.parse(dob_var);
                    endDate = new SimpleDateFormat("yyyy-MM-dd").format(endDateObject);

//adapterForAccounts.clear();
//                    adapterForAccounts.addAll(db.getAllHistory(strtDate,endDate));

                    EmployeeHistory = db.getAllHistory(strtDate, endDate);
//                    Log.d(TAG, "onClick: "+EmployeeHistory.size());
//                    EmployeeHistory=new ArrayList<EHistory>();

                    adapterForAccounts.notifyDataSetChanged();
                    Log.d(TAG, "onClick: notified");
                    //TODO   Here write rest code for adapter to fill list view with given date objects......i have made a template already for it
                    //TODO please make a list of EHistory containig employee's transactions name it as employeeHistory


                } catch (java.text.ParseException e) {
                    Toast.makeText(getBaseContext(), "Please Enter Date In Correct Format", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + Arrays.toString(e.getStackTrace()));
                }


            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent redirectToAdmin = new Intent(AccountsActivity.this, AdminActivity.class);
                startActivity(redirectToAdmin);
                finish();


            }
        });
    }
}
