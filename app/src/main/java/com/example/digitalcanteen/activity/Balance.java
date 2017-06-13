package com.example.digitalcanteen.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.example.digitalcanteen.R;
import com.example.digitalcanteen.adapter.BalanceAdapter;
import com.example.digitalcanteen.dataObjects.Employee;
import com.example.digitalcanteen.database.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class Balance extends AppCompatActivity {
    private static BalanceAdapter balanceAdapter = null;
    private ListView eBalance=null;
    private List<Employee> employees=new ArrayList<>();
    private UserDatabase db=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);

        eBalance = (ListView) findViewById(R.id.employee);
        db=new UserDatabase(Balance.this);
        employees=db.getAll();
        balanceAdapter = new BalanceAdapter(Balance.this,R.layout.template_accounts,employees);
        View header = getLayoutInflater().inflate(R.layout.template_balance, null);
        eBalance.addHeaderView(header);
        eBalance.setAdapter(balanceAdapter);



    }
}
