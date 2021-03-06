package com.example.digitalcanteen.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.digitalcanteen.R;
import com.example.digitalcanteen.adapter.EmpTransactionsAdapter;
import com.example.digitalcanteen.dataObjects.EHistory;
import com.example.digitalcanteen.database.TransactionDatabase;
import com.example.digitalcanteen.database.UserDatabase;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    private EditText Eid =null;
    private String EmpId= null;
    private ListView transactions= null;
    private List<EHistory> eTransactions= new ArrayList<>();
    private TransactionDatabase database = null;
    private Button oK = null;
    private Button exit=null;
    private UserDatabase db=null;
    private EmpTransactionsAdapter transactionsAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);


        Eid= (EditText) findViewById(R.id.transactionsEId);
        transactions = (ListView) findViewById(R.id.transactionsTransactions);
        View header = getLayoutInflater().inflate(R.layout.empaccounttemplate, null);
        transactions.addHeaderView(header);

        oK = (Button) findViewById(R.id.transactionsOK);
        exit = (Button) findViewById(R.id.transactionsExit);

        database = new TransactionDatabase(TransactionActivity.this);
        db  =new UserDatabase(TransactionActivity.this);


        oK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmpId = Eid.getText().toString();
                if (EmpId.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Employee code cannot be empty", Toast.LENGTH_SHORT)
                            .show();
                }
                else{


                    Cursor results = db.checkEmployeeId(EmpId);
                    if(results.moveToFirst()){
                        eTransactions = database.getEmpHist(EmpId);

                        transactionsAdapter = new EmpTransactionsAdapter(TransactionActivity.this,R.layout.empaccounttemplate,eTransactions);
                        transactions.setAdapter(transactionsAdapter);
                        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Please enter the Employee Code correctly", Toast.LENGTH_SHORT)
                                .show();
                    }
                }


            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToMP = new Intent(TransactionActivity.this, AdminActivity.class);
                startActivity(redirectToMP);
                finish();
            }
        });


    }
}
