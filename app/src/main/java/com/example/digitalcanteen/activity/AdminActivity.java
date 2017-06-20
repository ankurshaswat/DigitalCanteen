package com.example.digitalcanteen.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.digitalcanteen.R;
import com.example.digitalcanteen.database.TransactionDatabase;

import java.util.Date;

public class AdminActivity extends AppCompatActivity {


    private static final String TAG = "AdminActivity";
    int strtDay, strtMonth, strtYear;
    int currDay, currMonth, currYear;
    private Button btnChangeDate = null;
    private Button btnExit = null;
    private Button btnAccounts = null;
    private EditText edtDate = null;
    private Button btnMM = null;
    private Button checkBal = null;
    private TextView custNum = null;
    private TransactionDatabase db;
    //    private Button exit=null ;
    private Button toAccounts = null;
    private Button toETransactions = null;
    private Button toBalance = null;
    private String strtDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        edtDate = (EditText) findViewById(R.id.edtDate);
        btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
        custNum = (TextView) findViewById(R.id.CustNum);
        db = new TransactionDatabase(this);
        Log.d(TAG, "onCreate: i was here");
        checkBal = (Button) findViewById(R.id.btnCheckBal);

        java.text.DateFormat formatterM = new java.text.SimpleDateFormat("MM");
        java.text.DateFormat formatterD = new java.text.SimpleDateFormat("dd");
        java.text.DateFormat formatterY = new java.text.SimpleDateFormat("yyyy");


        Date today = new Date();
//        today.
        currDay = Integer.parseInt(formatterD.format(today));
        currMonth = Integer.parseInt(formatterM.format(today));
        currYear = Integer.parseInt(formatterY.format(today));

        String formatterDD = String.format("%02d", currDay);
        String formatterMM = String.format("%02d", currMonth);

        edtDate.setText("" + formatterDD + "/" + formatterMM + "/" + currYear + "");
        strtDate = "" + strtYear + "-" + formatterMM + "-" + formatterDD + "";
        custNum.setText(db.numCustomers(strtDate) + " customer came on given date");
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Log.d(TAG, "onDateSet: Do something with date here");


                        strtDay = dayOfMonth;
                        strtMonth = month;
                        strtYear = year;

                        String formatterD = String.format("%02d", strtDay);
                        String formatterM = String.format("%02d", strtMonth);
                        edtDate.setText("" + formatterD + "/" + formatterM + "/" + year + "");
                        strtDate = "" + strtYear + "-" + formatterM + "-" + formatterD;

                    }
                };
                final DatePickerDialog dialog = new DatePickerDialog(v.getContext(), datePickerListener, currYear, currMonth, currDay);
                dialog.show();
            }
        });



        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                custNum.setText(db.numCustomers(strtDate) + " customer came on given date");

//                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Make sure user insert date into edittext in this format.
//
//                Date strDateObject;
//                String strtDate = null;
//
//
//
//                try {
//                    String dob_var = (edtDate.getText().toString());
//                    strDateObject = formatter.parse(dob_var);
//                    strtDate = new SimpleDateFormat("yyyy-MM-dd").format(strDateObject);
//
//
//
//                    //TODO     here check database for number of customers visited on that date
//                    custNum.setText(db.numCustomers(strtDate) + " customer came on given date");
//                } catch (java.text.ParseException e) {
//                    Toast.makeText(getBaseContext(), "Please Enter Date In Correct Format", Toast.LENGTH_LONG).show();
//
//                }




                //fire up a dialog here to change date
                /////link here
                //https://developer.android.com/guide/topics/ui/dialogs.html
// do if free
            }
        });

        btnExit = (Button) findViewById(R.id.btnExit2);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent redirectToLogin = new Intent(AdminActivity.this, MainPage.class);
                startActivity(redirectToLogin);
                finish();
            }
        });


        btnAccounts = (Button) findViewById(R.id.btnAccnts);
        btnAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent redirectToAccounts = new Intent(AdminActivity.this, AccountsActivity.class);
                startActivity(redirectToAccounts);
            }
        });

        toBalance = (Button) findViewById(R.id.EmployeeBalance);
        toBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToBalance = new Intent(AdminActivity.this, Balance.class);
                startActivity(redirectToBalance);
                finish();
            }
        });

        toETransactions = (Button) findViewById(R.id.pWiseTransactions);
        toETransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent redirectToET = new Intent(AdminActivity.this, TransactionActivity.class);
                startActivity(redirectToET);
                finish();
            }
        });
        btnMM = (Button) findViewById(R.id.btnManageMenu);
        btnMM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: I was here");
                Intent redirectTOMM = new Intent(AdminActivity.this, EditMenu.class);
                Log.d(TAG, "onClick: I was here");
                startActivity(redirectTOMM);

            }
        });


        checkBal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent redirectToCheckBal = new Intent(AdminActivity.this, CheckBalance.class);
                startActivity(redirectToCheckBal);


            }
        });
    }


}
