package com.example.digitalcanteen.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.digitalcanteen.R;
import com.example.digitalcanteen.adapter.BalanceAdapter;
import com.example.digitalcanteen.dataObjects.Employee;
import com.example.digitalcanteen.database.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class Balance extends AppCompatActivity {
    private ListView eBalance=null;
    private List<Employee> employees=new ArrayList<>();
    private UserDatabase db=null;
    private static BalanceAdapter balanceAdapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);


        db=new UserDatabase(Balance.this);
        employees=db.getAll();
        balanceAdapter = new BalanceAdapter(Balance.this,R.layout.template_accounts,employees);
        View header = getLayoutInflater().inflate(R.layout.template_accounts,null);
        eBalance.addHeaderView(header);
        eBalance.setAdapter(balanceAdapter);



    }
}
