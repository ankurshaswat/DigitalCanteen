package com.example.digitalcanteen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.digitalcanteen.R;

public class OrderComplete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);


        final String Employee_id = getIntent().getExtras().getString("Employee_id");
        double Balance = getIntent().getExtras().getDouble("Balance");

        TextView id = (TextView) findViewById(R.id.txtEmpId);
        id.setText("Employee_id= " + Employee_id);

        TextView bal = (TextView) findViewById(R.id.txtBal);
        bal.setText("Balance = Rs. " + Balance);

        Button btnExit = (Button) findViewById(R.id.btnToMain);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent redirectToMain = new Intent(OrderComplete.this, LoginActivity.class);
                startActivity(redirectToMain);
                finish();
            }
        });

        Button btnAddMoney = (Button) findViewById(R.id.btnAddMoney);
        btnAddMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent redirectToAddMoney = new Intent(OrderComplete.this, AddMoreMoney.class);
                redirectToAddMoney.putExtra("Employee_id", Employee_id);
                startActivity(redirectToAddMoney);
                finish();


            }
        });
    }
}
