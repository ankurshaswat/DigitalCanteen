package com.example.digitalcanteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddMoreMoney extends AppCompatActivity {
    private UserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_more_money);

        final EditText id = (EditText) findViewById(R.id.empId);
        final EditText amt2add = (EditText) findViewById(R.id.amt2Add);

        db = new UserDatabase(this);

        if (getIntent().hasExtra("Employee_id")) {
            id.setText(getIntent().getExtras().getString("Employee_id"));
        }

        Button add = (Button) findViewById(R.id.addBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double amt = Double.parseDouble(amt2add.getText().toString());
                String employee_id = id.getText().toString();


                if (db.updateinfo(employee_id, amt)) {
                    Toast.makeText(AddMoreMoney.this, "Money Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent redirectToMain = new Intent(AddMoreMoney.this, LoginActivity.class);
                    startActivity(redirectToMain);
                    finish();
                } else {

                    Toast.makeText(AddMoreMoney.this, "Please Check Your Entries", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
