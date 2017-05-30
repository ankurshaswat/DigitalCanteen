package com.example.digitalcanteen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainPage extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static List<selectedItems> order = new ArrayList<>();

    public static List<menuItem> items = new ArrayList<>();
    public static SelectAdapter selectedItemsAdapter = null;
    public static MenuAdapter renderMenuAdapter = null;
    public static TextView total;
    //    private ListView selectedThings;
    public static double totalamt = 0;
    private EditText amt2add = null;
    private ListView selectedThings;
    private ListView listItems;
    private ProgressDialog progressDialog;
    private Button btnExit = null;
    private Button btnSubmit = null;
    private EditText employee_id_edit = null;
    private UserDatabase db;
    private TransactionDatabase tranDB;
    private String employee_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        amt2add = (EditText) findViewById(R.id.moneyAmt);
        total = (TextView) findViewById(R.id.txtVuTot);

        setContentView(R.layout.activity_main_page);
        progressDialog = new ProgressDialog(this);
        btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToLogin = new Intent(MainPage.this, LoginActivity.class);
                startActivity(redirectToLogin);
                finish();
            }
        });
        db = new UserDatabase(this);
        tranDB = new TransactionDatabase(this);
        listItems = (ListView) findViewById(R.id.lstMenu);
        selectedThings = (ListView) findViewById(R.id.lstCart);


//       order.add(new selectedItems("pizza", "2", "600"));


        for (int i = 0; i < items.size(); i += 1) {
            items.get(i).setId(i);
        }

        selectedItemsAdapter = new SelectAdapter(MainPage.this, R.layout.activity_selected, order);
        selectedThings.setAdapter(selectedItemsAdapter);
        employee_id_edit = (EditText) findViewById(R.id.employeeCodeInput);
        //Log.d(TAG, "onCreate: i am batman");

        renderMenuAdapter = new MenuAdapter(MainPage.this, R.layout.activity_layout_menu, items);
        listItems.setAdapter(renderMenuAdapter);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                TODO : take list of selected items here and store
//                TODO : check employee code.....if not already present start it with zero balance
                //the code is commented out in login activity code    reuse it..........


                if (employee_id_edit.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Employee code cannot be empty", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    showProgressDialog();
                    employee_id = employee_id_edit.getText().toString();
                    Cursor results = db.checkEmployeeId(employee_id);

                    if (!results.moveToFirst()) {
                        //  create new user with 0 balance instead of old code.

                        Date date_x = new Date();
                        DateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
                        final String date = sdf.format(date_x);

                        Log.d(TAG, "onClick: inserting user");
                        boolean check = db.insertUser(employee_id, 0, date);
                        if (check) {
                            Toast.makeText(getApplicationContext(), "Registration Succesful", Toast.LENGTH_SHORT).show();
//

                            //now take order
                            for (int i = 0; i < order.size(); i++) {
                                //here add each item to transactions table
                                tranDB.insertTransaction(employee_id, order.get(i).getName(), Integer.parseInt(order.get(i).getQuantity()), Double.parseDouble(order.get(i).getPrice()), date);

                            }
                            db.updateinfo(employee_id, -1 * totalamt);
                            if (amt2add.getText().toString() != "") {
                                db.updateinfo(employee_id, Double.parseDouble(amt2add.getText().toString()));
                            }
                            double balance = db.getBal(employee_id);

                            Intent redirectToSuccess = new Intent(MainPage.this, OrderComplete.class);
                            redirectToSuccess.putExtra("Employee_id", employee_id);
                            redirectToSuccess.putExtra("Balance", balance);
                            startActivity(redirectToSuccess);
//TODO take amount to add money
//                            Intent redirectToMain = new Intent(RegisterActivity.this, MainPage.class);
//                            redirectToMain.putExtra("Employee_id", employee_id);
////                            redirectToMain.putExtra("Name", employee_name);
//                            redirectToMain.putExtra("Balance", 0);
//                            redirectToMain.putExtra("Date", date);

//                            startActivity(redirectToMain);


                            //redirect to ordered successfully activity
                            cancelProgressDialog();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Connection Error!! Please Try Again", Toast.LENGTH_SHORT).show();
                        }



//                        cancelProgressDialog();
//
//                        AlertDialog.Builder alert = new AlertDialog.Builder(MainPage.this);
//                        alert.setTitle("Invalid Employee ID!!");
//
//                        final String MessageToShow = "Please enter the details again...";
//                        alert.setMessage(MessageToShow);
//
//                        alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                                employee_id_edit.setText("");
//                            }
//                        });
//                        alert.show();
//                        //employee_id_edit.setText("");
                    } else {
                        //after submitting what else to do??????????????????


                        String employee_id = results.getString(1);//pass index after making db
//                        String name = results.getString(2);//pass index of name
                        Double balance = results.getDouble(2);
//TODO take amount to add money

                        for (int i = 0; i < order.size(); i++) {
                            //here add each item to transactions table


                        }
                        db.updateinfo(employee_id, -1 * totalamt);
                        if (amt2add.getText().toString() != "") {
                            db.updateinfo(employee_id, Double.parseDouble(amt2add.getText().toString()));
                        }
                        balance = db.getBal(employee_id);

                        Intent redirectToSuccess = new Intent(MainPage.this, OrderComplete.class);
                        redirectToSuccess.putExtra("Employee_id", employee_id);
                        redirectToSuccess.putExtra("Balance", balance);
                        startActivity(redirectToSuccess);
                        //redirect to ordered and add transactions




//                        Intent refirectToMain = new Intent(LoginActivity.this, MainPage.class);
//                        refirectToMain.putExtra("employee_id", employee_id);
//                        refirectToMain.putExtra("name", name);
//                        refirectToMain.putExtra("balance", balance);

                        cancelProgressDialog();
//                        startActivity(refirectToMain);
                        finish();

                    }
                }
            }


        });


    }

    private void showProgressDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void cancelProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }
}
