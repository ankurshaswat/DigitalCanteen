package com.example.digitalcanteen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CheckBalance extends AppCompatActivity {
    private UserDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_balance);

        db = new UserDatabase(this);
        final EditText id = (EditText) findViewById(R.id.btnId);
        Button btnCheck = (Button) findViewById(R.id.btnCheckBalance);
        Button back = (Button) findViewById(R.id.btnBck);
        final TextView bal = (TextView) findViewById(R.id.txtBala);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_string = id.getText().toString();

                if (id_string.isEmpty()) {
                    Toast.makeText(CheckBalance.this, "Enter Employee Code", Toast.LENGTH_SHORT).show();
                } else {


                    Cursor results = db.checkEmployeeId(id_string);

                    if (!results.moveToFirst()) {

                        Toast.makeText(CheckBalance.this, "This employee has not ordered anything yet", Toast.LENGTH_SHORT).show();
                    } else {

                        bal.setText(String.valueOf(db.getBal(id_string)));

                    }

                }
//                Toast.makeText(CheckBalance.this, String.valueOf(db.updateinfo(id_string, 70.0)), Toast.LENGTH_SHORT).show();
//                Toast.makeText(CheckBalance.this, String.valueOf(db.getBal(id_string)), Toast.LENGTH_SHORT).show();


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent redirectToAdmin = new Intent(CheckBalance.this, AdminActivity.class);
                startActivity(redirectToAdmin);
                finish();
            }
        });
    }
}
