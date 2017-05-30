package com.example.digitalcanteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminActivity extends AppCompatActivity {


    private static final String TAG = "AdminActivity";
    private Button btnChangeDate = null;
    private Button btnExit = null;
    private Button btnAccounts = null;
    private EditText edtDate = null;
    private Button btnMM = null;
    private Button checkBal = null;
    private TextView custNum = null;
    private TransactionDatabase db;
    
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
        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Make sure user insert date into edittext in this format.

                Date strDateObject;
                String strtDate = null;

                try {
                    String dob_var = (edtDate.getText().toString());
                    strDateObject = formatter.parse(dob_var);
                    strtDate = new SimpleDateFormat("dd/MM/yyyy").format(strDateObject);



                    //TODO     here check database for number of customers visited on that date
                    custNum.setText(db.numCustomers(strtDate) + " customer came on given date");
                } catch (java.text.ParseException e) {
                    Toast.makeText(getBaseContext(), "Please Enter Date In Correct Format", Toast.LENGTH_LONG).show();

                }


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

                Intent redirectToLogin = new Intent(AdminActivity.this, LoginActivity.class);
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
