package com.example.digitalcanteen.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.digitalcanteen.R;
import com.example.digitalcanteen.adapter.MenuAdapter;
import com.example.digitalcanteen.adapter.SelectAdapter;
import com.example.digitalcanteen.app.AppConfig;
import com.example.digitalcanteen.app.AppController;
import com.example.digitalcanteen.dataObjects.menuItem;
import com.example.digitalcanteen.dataObjects.selectedItems;
import com.example.digitalcanteen.database.MenuDatabase;
import com.example.digitalcanteen.database.TransactionDatabase;
import com.example.digitalcanteen.database.UserDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainPage extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    public static List<selectedItems> order = new ArrayList<>();

    public static List<menuItem> items = new ArrayList<>();

    public static SelectAdapter selectedItemsAdapter = null;
    public static MenuAdapter renderMenuAdapter = null;
    public static TextView total = null;
    //    private ListView selectedThings;
    public static double totalamt = 0;
    private static int flagg = 0;
    private EditText amt2add = null;
    private ListView selectedThings;
    private ListView listItems;
    private ProgressDialog progressDialog;
    private Button btnExit = null;
    private Button btnSubmit = null;
    private Button btnAddUser = null;
    private Button btn2Admin = null;
    private EditText employee_id_edit = null;
    private UserDatabase db;
    private TransactionDatabase tranDB;
    private MenuDatabase menuDB;
    private String employee_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_page);


        btn2Admin = (Button) findViewById(R.id.mainPAgeAdmin);
        btn2Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToAdmin = new Intent(MainPage.this, AdminActivity.class);
                startActivity(redirectToAdmin);
                finish();
            }
        });


        btnAddUser = (Button) findViewById(R.id.add_user);


        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder qBuilder = new AlertDialog.Builder(MainPage.this);
                Log.d(TAG, "onClick: I am here");
                View qView = getLayoutInflater().inflate(R.layout.adduser, null);
                final EditText newName = (EditText) qView.findViewById(R.id.addName);
                final EditText newCode = (EditText) qView.findViewById(R.id.addEC);
                Button addSubmit = (Button) qView.findViewById(R.id.addSubmit);
                Button addCancel = (Button) qView.findViewById(R.id.addCancel);


                qBuilder.setView(qView);
                final AlertDialog dialog = qBuilder.create();
                dialog.show();

                addSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String addId = newCode.getText().toString();
                        String addName = newName.getText().toString();

                        if (addId.length() == 0 || addName.length() == 0) {
                            Toast.makeText(getApplicationContext(), "No field can be Empty", Toast.LENGTH_SHORT)
                                    .show();

                        } else {
//                            Date date_x = new Date();
//                            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                            final String date = sdf.format(date_x);
//                            Log.d(TAG, "onClick: inserting user and date is " + date_x);
//                            Log.d(TAG, "onClick: inserting user and date is " + date);

                            if (db.checkEmployeeId(addId).moveToFirst()) {
                                Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
                            } else {
                                boolean check = db.insertUser(addId, addName, 0.0);
                                if (check) {
                                    Toast.makeText(getApplicationContext(), "Registration Succesful", Toast.LENGTH_SHORT).show();
                                    addUser(addId, addName, 0.0);
                                    dialog.cancel();
                                }
                            }

                        }

                    }
                });


                addCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });


            }
        });
        amt2add = (EditText) findViewById(R.id.moneyAmt);
        total = (TextView) findViewById(R.id.txtVuTotal);
//        total.setText("howdy");
//        setContentView(R.layout.activity_main_page);
        progressDialog = new ProgressDialog(this);
        btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToLogin = new Intent(MainPage.this, MainPage.class);
                startActivity(redirectToLogin);
                order.clear();
                finish();
            }
        });
        db = new UserDatabase(this);
        tranDB = new TransactionDatabase(this);
        menuDB = new MenuDatabase(this);

        listItems = (ListView) findViewById(R.id.lstMenu);
        selectedThings = (ListView) findViewById(R.id.lstCart);
        Button addMoney = (Button) findViewById(R.id.mPAddMoney);


//       order.add(new selectedItems("pizza", "2", "600"));

        items = menuDB.getAll();
//        for (int i = 0; i < items.size(); i += 1) {
//            items.get(i).setId(i);
//        }

        selectedItemsAdapter = new SelectAdapter(MainPage.this, R.layout.activity_selected, order);
        selectedThings.setAdapter(selectedItemsAdapter);
        employee_id_edit = (EditText) findViewById(R.id.employeeCodeInput);
        //Log.d(TAG, "onCreate: i am batman");

        renderMenuAdapter = new MenuAdapter(MainPage.this, R.layout.activity_layout_menu, items);
        listItems.setAdapter(renderMenuAdapter);


        Button IdEntered = (Button) findViewById(R.id.mPIdE);
        IdEntered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (employee_id_edit.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Employee code cannot be empty", Toast.LENGTH_SHORT)
                            .show();
                }
//                TODO checking if id is entered correctly
                else {

                    employee_id = employee_id_edit.getText().toString();
                    Cursor results = db.checkEmployeeId(employee_id);
                    if (results.moveToFirst()) {

                        String tempId = employee_id_edit.getText().toString();
                        final TextView bal = (TextView) findViewById(R.id.balance);
                        bal.setText("Your Balance is " + String.valueOf(db.getBal(tempId)));
                        flagg = 1;
                        Toast.makeText(MainPage.this, "Logged in Succesfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter the Employee Code correctly", Toast.LENGTH_SHORT)
                                .show();
                    }
                }


            }
        });
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagg != 1) {
                    Toast.makeText(MainPage.this, "Please Log In First to place order", Toast.LENGTH_SHORT).show();
                } else {
                    Date date_x = new Date();
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    final String date = sdf.format(date_x);
                    for (int i = 0; i < order.size(); i++) {
                        //here add each item to transactions table
                        Log.d(TAG, "onClick: inserting " + order.get(i).getName());
                        tranDB.insertTransaction(employee_id, order.get(i).getName(), Integer.parseInt(order.get(i).getQuantity()), Double.parseDouble(order.get(i).getPrice()) / Integer.parseInt(order.get(i).getQuantity()), date);
                        addTransaction(employee_id, order.get(i).getName(), Integer.parseInt(order.get(i).getQuantity()), Double.parseDouble(order.get(i).getPrice()) / Integer.parseInt(order.get(i).getQuantity()), date);
                    }
                    db.updateinfo(employee_id, -1 * totalamt);
                    Toast.makeText(MainPage.this, "Order Succesful. You are being logged out", Toast.LENGTH_SHORT).show();


                    items.clear();
                    order.clear();
                    flagg = 0;
                    employee_id_edit.setText("");

                    recreate();


                }

//                TODO : take list of selected items here and store
//                TODO : check employee code.....if not already present start it with zero balance
                //the code is commented out in login activity code    reuse it..........


//                if (employee_id_edit.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Employee code cannot be empty", Toast.LENGTH_SHORT)
//                            .show();
//                } else {
//                    showProgressDialog();
//                    employee_id = employee_id_edit.getText().toString();
//                    Cursor results = db.checkEmployeeId(employee_id);
//
//                    if (!results.moveToFirst()) {
//                        //  create new user with 0 balance instead of old code.
//
////                        Date date_x = new Date();
////                        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
////                        final String date = sdf.format(date_x);
////                        Log.d(TAG, "onClick: inserting user and date is " + date_x);
////                        Log.d(TAG, "onClick: inserting user and date is " + date);
//                        boolean check = db.insertUser(employee_id,name, 0);
//                        if (check) {
//                            Toast.makeText(getApplicationContext(), "Registration Succesful", Toast.LENGTH_SHORT).show();
////
//
//                            //now take order
//                            for (int i = 0; i < order.size(); i++) {
//                                //here add each item to transactions table
//                                Log.d(TAG, "onClick: inserting " + order.get(i).getName());
//                                tranDB.insertTransaction(employee_id, order.get(i).getName(), Integer.parseInt(order.get(i).getQuantity()), Double.parseDouble(order.get(i).getPrice()) / Integer.parseInt(order.get(i).getQuantity()), date);
//                                addTransaction(employee_id, order.get(i).getName(), Integer.parseInt(order.get(i).getQuantity()), Double.parseDouble(order.get(i).getPrice()) / Integer.parseInt(order.get(i).getQuantity()), date);
//                            }
//                            db.updateinfo(employee_id, -1 * totalamt);
//
//                            if (!amt2add.getText().toString().isEmpty()) {
//                                db.updateinfo(employee_id, Double.parseDouble(amt2add.getText().toString()));
//                            }
//                            double balance = db.getBal(employee_id);
//
//                            Intent redirectToSuccess = new Intent(MainPage.this, OrderComplete.class);
//                            redirectToSuccess.putExtra("Employee_id", employee_id);
//                            redirectToSuccess.putExtra("Balance", balance);
//                            startActivity(redirectToSuccess);
//                            order.clear();
////TODO take amount to add money
////                            Intent redirectToMain = new Intent(RegisterActivity.this, MainPage.class);
////                            redirectToMain.putExtra("Employee_id", employee_id);
//////                            redirectToMain.putExtra("Name", employee_name);
////                            redirectToMain.putExtra("Balance", 0);
////                            redirectToMain.putExtra("Date", date);
//
////                            startActivity(redirectToMain);
//
//
//                            //redirect to ordered successfully activity
//                            cancelProgressDialog();
//                            finish();
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Connection Error!! Please Try Again", Toast.LENGTH_SHORT).show();
//                        }
//
//
////                        cancelProgressDialog();
////
////                        AlertDialog.Builder alert = new AlertDialog.Builder(MainPage.this);
////                        alert.setTitle("Invalid Employee ID!!");
////
////                        final String MessageToShow = "Please enter the details again...";
////                        alert.setMessage(MessageToShow);
////
////                        alert.setNegativeButton("OK", new DialogInterface.OnClickListener() {
////                            @Override
////                            public void onClick(DialogInterface dialog, int which) {
////                                dialog.cancel();
////                                employee_id_edit.setText("");
////                            }
////                        });
////                        alert.show();
////                        //employee_id_edit.setText("");
//                    } else {
//                        //after submitting what else to do??????????????????
//
//
//                        String employee_id = results.getString(1);//pass index after making db
////                        String name = results.getString(2);//pass index of name
//                        Double balance = results.getDouble(2);
////TODO take amount to add money
//                        Date date_x = new Date();
//                        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                        final String date = sdf.format(date_x);
//                        for (int i = 0; i < order.size(); i++) {
//                            //here add each item to transactions table
//                            tranDB.insertTransaction(employee_id, order.get(i).getName(), Integer.parseInt(order.get(i).getQuantity()), Double.parseDouble(order.get(i).getPrice()) / Integer.parseInt(order.get(i).getQuantity()), date);
//                            addTransaction(employee_id, order.get(i).getName(), Integer.parseInt(order.get(i).getQuantity()), Double.parseDouble(order.get(i).getPrice()) / Integer.parseInt(order.get(i).getQuantity()), date);
//                        }
//                        db.updateinfo(employee_id, -1 * totalamt);
//                        if (!amt2add.getText().toString().matches("")) {
//                            db.updateinfo(employee_id, Double.parseDouble(amt2add.getText().toString()));
//                        }
//                        balance = db.getBal(employee_id);
//
//                        Intent redirectToSuccess = new Intent(MainPage.this, OrderComplete.class);
//                        redirectToSuccess.putExtra("Employee_id", employee_id);
//                        redirectToSuccess.putExtra("Balance", balance);
//                        startActivity(redirectToSuccess);
//                        order.clear();
//                        //redirect to ordered and add transactions
//
//
////                        Intent refirectToMain = new Intent(LoginActivity.this, MainPage.class);
////                        refirectToMain.putExtra("employee_id", employee_id);
////                        refirectToMain.putExtra("name", name);
////                        refirectToMain.putExtra("balance", balance);
//
//                        cancelProgressDialog();
////                        startActivity(refirectToMain);
//                        finish();

//                    }
//                }
            }


        });

        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flagg == 1) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainPage.this);
                    Log.d(TAG, "onClick: I am here");
                    View mView = getLayoutInflater().inflate(R.layout.addmoney, null);

                    final EditText amount = (EditText) mView.findViewById(R.id.addMoneyAmount);
                    Button aMOK = (Button) mView.findViewById(R.id.addMoneyOK);
                    Button aMCancel = (Button) mView.findViewById(R.id.addMoneyCancel);
                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    aMOK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            double money = Double.parseDouble(amount.getText().toString());
                            double balNow = db.getBal(employee_id_edit.getText().toString());
                            Log.d(TAG, "onClick: Balance Before adding" + balNow);
                            balNow += money;
                            db.updateinfo(employee_id_edit.getText().toString(), money);
                            balNow = db.getBal(employee_id_edit.getText().toString());
                            Log.d(TAG, "onClick: After Adding " + balNow);
                            TextView currBalance = (TextView) findViewById(R.id.balance);

//TODO put check here to see balance added or not and proceed accordingly

                            String tempId = employee_id_edit.getText().toString();
                            currBalance.setText("Your Balance is " + String.valueOf(db.getBal(tempId)));

                            dialog.cancel();

                        }
                    });


                    aMCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                }
            }
        });


    }

    private void addTransaction(final String Employee_id, final String Order_name, final Integer Quantity, final Double cpi, final String date) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

//        pDialog.setMessage("Registering ...");
//        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_TRANSACTION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
//                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {


                        Log.d(TAG, "onResponse: Succesfully posted to net");
//                        // User successfully stored in MySQL
//                        // Now store the user in sqlite
//                        String uid = jObj.getString("uid");
//
//                        JSONObject user = jObj.getJSONObject("user");
//                        String name = user.getString("name");
//                        String email = user.getString("email");
//                        String created_at = user
//                                .getString("created_at");
//
//                        // Inserting row in users table
//                        db.addUser(name, email, uid, created_at);
//
//                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
//
//                        // Launch login activity
//                        Intent intent = new Intent(
//                                RegisterActivity.this,
//                                MainPage.class);
//                        startActivity(intent);
//                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("Employee_code", Employee_id);
//        new_content.put("Name", employee_name);
                params.put("Order_name", Order_name);
                params.put("Quantity", String.valueOf(Quantity));
                params.put("Cost_perItem", String.valueOf(cpi));
                params.put("Date", date);
                params.put("Total", String.valueOf(Quantity * cpi));
//                Log.d(TAG, "insertUser: inseting to db");
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void addUser(final String Employee_id, final String name, final Double Balance) {
        // Tag used to cancel the request
        String tag_string_req = "req_register2";

//        pDialog.setMessage("Registering ...");
//        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_USER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
//                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {


                        Log.d(TAG, "onResponse: Succesfully posted to net");
//                        // User successfully stored in MySQL
//                        // Now store the user in sqlite
//                        String uid = jObj.getString("uid");
//
//                        JSONObject user = jObj.getJSONObject("user");
//                        String name = user.getString("name");
//                        String email = user.getString("email");
//                        String created_at = user
//                                .getString("created_at");
//
//                        // Inserting row in users table
//                        db.addUser(name, email, uid, created_at);
//
//                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
//
//                        // Launch login activity
//                        Intent intent = new Intent(
//                                RegisterActivity.this,
//                                MainPage.class);
//                        startActivity(intent);
//                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
//                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("Employee_code", Employee_id);
                params.put("Name", name);
                params.put("Balance", String.valueOf(Balance));
//        new_content.put("Name", employee_name);

//                Log.d(TAG, "insertUser: inseting to db");
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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
