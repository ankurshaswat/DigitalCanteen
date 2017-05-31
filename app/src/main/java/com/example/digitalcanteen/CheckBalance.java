package com.example.digitalcanteen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CheckBalance extends AppCompatActivity {
    private UserDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_balance);

        db = new UserDatabase(this);
        final EditText id = (EditText) findViewById(R.id.btnId);
        Button btnCheck = (Button) findViewById(R.id.btnCheckBalance);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id_string = id.getText().toString();
//                Toast.makeText(CheckBalance.this, String.valueOf(db.updateinfo(id_string, 70.0)), Toast.LENGTH_SHORT).show();
//                Toast.makeText(CheckBalance.this, String.valueOf(db.getBal(id_string)), Toast.LENGTH_SHORT).show();


            }
        });
    }
}
