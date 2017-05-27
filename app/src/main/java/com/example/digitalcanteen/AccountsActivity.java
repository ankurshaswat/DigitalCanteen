package com.example.digitalcanteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountsActivity extends AppCompatActivity {

    private Button btnGenerate = null;
    private Button btnDone = null;
    private EditText strtDateBox = null;
    private EditText endDateBox = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        btnGenerate = (Button) findViewById(R.id.btnGenerate);
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
                    strtDate = new SimpleDateFormat("dd/MM/yyyy").format(strDateObject);

                    dob_var = (endDateBox.getText().toString());
                    endDateObject = formatter.parse(dob_var);
                    endDate = new SimpleDateFormat("dd/MM/yyyy").format(endDateObject);

                    //TODO   Here write rest code for adapter to fill list view with given date objects......i have made a template already for it
                } catch (java.text.ParseException e) {
                    Toast.makeText(getBaseContext(), "Please Enter Date In Correct Format", Toast.LENGTH_LONG).show();

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
