package com.example.digitalcanteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends AppCompatActivity {


    private Button btnChangeDate = null;
    private Button btnExit = null;
    private Button btnAccounts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
        btnChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //fire up a dialog here to change date
                /////link here
                //https://developer.android.com/guide/topics/ui/dialogs.html

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
    }
}
