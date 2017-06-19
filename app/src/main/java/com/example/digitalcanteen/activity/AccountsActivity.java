package com.example.digitalcanteen.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.digitalcanteen.R;
import com.example.digitalcanteen.adapter.acccountAdapter;
import com.example.digitalcanteen.dataObjects.EHistory;
import com.example.digitalcanteen.dataObjects.Sale;
import com.example.digitalcanteen.database.MenuDatabase;
import com.example.digitalcanteen.database.TransactionDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AccountsActivity extends AppCompatActivity {
    private static final String TAG = "AccountsActivity";
    public List<EHistory> EmployeeHistory = new ArrayList<>();
    public acccountAdapter adapterForAccounts = null;
    private Button btnGenerate = null;
    private Button btnDone = null;
    private EditText strtDateBox = null;
    private EditText endDateBox = null;
    private ListView transactions = null;
    private List<Sale> sales = new ArrayList<>();
    private Cursor cursor = null;
    private HashMap<String, Sale> map = new HashMap<String, Sale>();
    private MenuDatabase menuDb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        final TransactionDatabase db = new TransactionDatabase(this);
        btnGenerate = (Button) findViewById(R.id.btnGenerate);

        transactions = (ListView) findViewById(R.id.accntList);
//        db.insertTransaction("g", "kuchbhi", 2, 3.0, "2017-06-06");
        EmployeeHistory = db.getAll();
        View header = getLayoutInflater().inflate(R.layout.accounttemplate, null);
        transactions.addHeaderView(header);


        btnDone = (Button) findViewById(R.id.btnDone);
        strtDateBox = (EditText) findViewById(R.id.editStartDate);
        endDateBox = (EditText) findViewById(R.id.editEndDate);
        menuDb = new MenuDatabase(AccountsActivity.this);
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Make sure user insert date into edittext in this format.

                Date strDateObject, endDateObject;
                String strtDate, endDate;

                try {
                    String dob_var = (strtDateBox.getText().toString());
                    strDateObject = formatter.parse(dob_var);
                    strtDate = new SimpleDateFormat("yyyy-MM-dd").format(strDateObject);

                    dob_var = (endDateBox.getText().toString());
                    endDateObject = formatter.parse(dob_var);
                    endDate = new SimpleDateFormat("yyyy-MM-dd").format(endDateObject);
                    cursor = db.getAllHistoryCursor(strtDate, endDate);
                    Log.d(TAG, "onClick: Got Cursor");
                    while (cursor.moveToNext()) {
                        Log.d(TAG, "onClick: Noting ofr Item");
                        String name = cursor.getString(2);
                        Integer quantity2 = cursor.getInt(3);
//                        Double cpi = cursor.getDouble(4);
                        Double total = cursor.getDouble(5);
                        Double cpi = menuDb.getItemPrice(name);

                        if (!map.containsKey(name)) {
                            map.put(name, new Sale(name, cpi, quantity2, total));
                        } else {
                            Sale tempSale = map.get(name);
                            Double tempTotal = tempSale.getTotal();
                            Integer tempQuantity = tempSale.getQuan();
                            tempTotal += total;
                            tempQuantity += quantity2;

                            map.put(name, new Sale(name, cpi, tempQuantity, tempTotal));

                        }

                    }

                    for (String KEY : map.keySet()) {
                        sales.add(map.get(KEY));
                    }


                } catch (java.text.ParseException e) {
                    Toast.makeText(getBaseContext(), "Please Enter Date In Correct Format", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Log.d(TAG, "onClick: " + Arrays.toString(e.getStackTrace()));
                }


            }
        });
        adapterForAccounts = new acccountAdapter(AccountsActivity.this, R.layout.accounttemplate, sales);
//        EmployeeHistory = db.getAll();
//        adapterForAccounts.notifyDataSetChanged();
//
//  transactions.setAdapter(adapterForAccounts);
        transactions.setAdapter(adapterForAccounts);

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
