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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

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
    private EditText amt2add = null;
    private ListView selectedThings;
    private ListView listItems;
    private ProgressDialog progressDialog;
    private Button btnExit = null;
    private Button btnSubmit = null;
    private EditText employee_id_edit = null;
    private UserDatabase db;
    private TransactionDatabase tranDB;
    private MenuDatabase menuDB;
    private String employee_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_page);


        amt2add = (EditText) findViewById(R.id.moneyAmt);
        total = (TextView) findViewById(R.id.txtVuTotal);
//        total.setText("howdy");
//        setContentView(R.layout.activity_main_page);
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
        menuDB = new MenuDatabase(this);

        listItems = (ListView) findViewById(R.id.lstMenu);
        selectedThings = (ListView) findViewById(R.id.lstCart);


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
                        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        final String date = sdf.format(date_x);
                        Log.d(TAG, "onClick: inserting user and date is " + date_x);
                        Log.d(TAG, "onClick: inserting user and date is " + date);
                        boolean check = db.insertUser(employee_id, 0, date);
                        if (check) {
                            Toast.makeText(getApplicationContext(), "Registration Succesful", Toast.LENGTH_SHORT).show();
//

                            //now take order
                            for (int i = 0; i < order.size(); i++) {
                                //here add each item to transactions table
                                Log.d(TAG, "onClick: inserting " + order.get(i).getName());
                                tranDB.insertTransaction(employee_id, order.get(i).getName(), Integer.parseInt(order.get(i).getQuantity()), Double.parseDouble(order.get(i).getPrice()) / Integer.parseInt(order.get(i).getQuantity()), date);
                                addTransaction(employee_id, order.get(i).getName(), Integer.parseInt(order.get(i).getQuantity()), Double.parseDouble(order.get(i).getPrice()) / Integer.parseInt(order.get(i).getQuantity()), date);
                            }
                            db.updateinfo(employee_id, -1 * totalamt);

                            if (!amt2add.getText().toString().isEmpty()) {
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
                        Date date_x = new Date();
                        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        final String date = sdf.format(date_x);
                        for (int i = 0; i < order.size(); i++) {
                            //here add each item to transactions table
                            tranDB.insertTransaction(employee_id, order.get(i).getName(), Integer.parseInt(order.get(i).getQuantity()), Double.parseDouble(order.get(i).getPrice()) / Integer.parseInt(order.get(i).getQuantity()), date);
                            addTransaction(employee_id, order.get(i).getName(), Integer.parseInt(order.get(i).getQuantity()), Double.parseDouble(order.get(i).getPrice()) / Integer.parseInt(order.get(i).getQuantity()), date);
                        }
                        db.updateinfo(employee_id, -1 * totalamt);
                        if (!amt2add.getText().toString().matches("")) {
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
//                                LoginActivity.class);
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
