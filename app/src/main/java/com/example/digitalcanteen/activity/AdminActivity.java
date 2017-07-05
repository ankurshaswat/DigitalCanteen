package com.example.digitalcanteen.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.digitalcanteen.R;
import com.example.digitalcanteen.app.AppConfig;
import com.example.digitalcanteen.app.AppController;
import com.example.digitalcanteen.dataObjects.Employee;
import com.example.digitalcanteen.dataObjects.menuItem;
import com.example.digitalcanteen.database.CollectionDatabase;
import com.example.digitalcanteen.database.MenuDatabase;
import com.example.digitalcanteen.database.TransactionDatabase;
import com.example.digitalcanteen.database.UserDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {


    private static final String TAG = "AdminActivity";
    int strtDay, strtMonth, strtYear;
    int currDay, currMonth, currYear;
    private Button btnChangeDate = null;
    private Button btnExit = null;
    private Button btnAccounts = null;
    private Button edtDate = null;
    private Button btnMM = null;
    private ProgressDialog progressDialog;
    private Button checkBal = null;
    private TextView custNum = null;
    private TransactionDatabase db;
    private MenuDatabase menudb;
    private UserDatabase userdb;
    //    private Button exit=null ;
    private Button toAccounts = null;
    private Button toETransactions = null;
    private Button toBalance = null;
    private String strtDate;
    private String setDate;
    private TextView theDate;
    private Button btnCollections;
    private TextView showCollection;
    private CollectionDatabase collectionDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        progressDialog = new ProgressDialog(this);
        edtDate = (Button) findViewById(R.id.edtDate);
//        btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
        custNum = (TextView) findViewById(R.id.CustNum);
        db = new TransactionDatabase(this);
        menudb = new MenuDatabase(this);
        userdb = new UserDatabase(this);
//        userdb.del();
        Log.d(TAG, "onCreate: i was here");
        checkBal = (Button) findViewById(R.id.btnCheckBal);

        java.text.DateFormat formatterM = new java.text.SimpleDateFormat("MM");
        java.text.DateFormat formatterD = new java.text.SimpleDateFormat("dd");
        java.text.DateFormat formatterY = new java.text.SimpleDateFormat("yyyy");

        theDate = (TextView) findViewById(R.id.theDate);
        Date today = new Date();
//        today.
        currDay = Integer.parseInt(formatterD.format(today));
        currMonth = Integer.parseInt(formatterM.format(today));
//        currMonth+=1;
        currYear = Integer.parseInt(formatterY.format(today));

        String formatterDD = String.format("%02d", currDay);
        String formatterMM = String.format("%02d", currMonth);
//        Log.d(TAG, "onCreate: "+strtYear);

        theDate.setText("" + formatterDD + "/" + formatterMM + "/" + currYear + "");
        setDate = "" + formatterDD + "/" + formatterMM + "/" + currYear + "";
        strtDate = "" + currYear + "-" + formatterMM + "-" + formatterDD + "";
        Log.d(TAG, "onCreate: " + strtDate);
        Log.d(TAG, "onCreate: outside function" + db.numCustomers(strtDate));
        custNum.setText(db.numCustomers(strtDate) + " customer came on " + setDate);
        Log.d(TAG, "onCreate: " + db.numCustomers(strtDate));
        btnCollections = (Button) findViewById(R.id.btnCollections);
        showCollection = (TextView) findViewById(R.id.collectionView);
        collectionDatabase = new CollectionDatabase(AdminActivity.this);

        if (collectionDatabase.getAllHistory(strtDate, strtDate).size() == 0) {
            showCollection.setText("No History");
        } else {
            showCollection.setText("Total Collection " + String.valueOf(collectionDatabase.getAllHistory(strtDate, strtDate).get(0).getCollection()));

        }

        btnCollections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCollections = new Intent(AdminActivity.this, Collections.class);
                startActivity(toCollections);
                finish();
            }
        });
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Log.d(TAG, "onDateSet: Do something with date here");


                        strtDay = dayOfMonth;
                        strtMonth = month + 1;
                        strtYear = year;

                        String formatterD = String.format("%02d", strtDay);
                        String formatterM = String.format("%02d", strtMonth);
                        theDate.setText("" + formatterD + "/" + formatterM + "/" + year + "");
                        strtDate = "" + strtYear + "-" + formatterM + "-" + formatterD;
                        setDate = "" + formatterD + "/" + formatterM + "/" + year + "";

                        Log.d(TAG, "onCreate: in function " + strtDate);
                        Log.d(TAG, "onCreate:in function " + db.numCustomers(strtDate));
                        if (db.numCustomers(strtDate) != 1) {
                            custNum.setText(db.numCustomers(strtDate) + " customers came on " + "" + formatterD + "/" + formatterM + "/" + year + "");

                            if (collectionDatabase.getAllHistory(strtDate, strtDate).size() != 0) {

                                showCollection.setText("Collection on " + setDate + ":- " + String.valueOf(collectionDatabase.getAllHistory(strtDate, strtDate).get(0).getCollection()));
                            } else {
                                showCollection.setText("Collection on " + setDate + ":- 0");
                            }

                        } else {
                            custNum.setText(db.numCustomers(strtDate) + " customer came on " + "" + formatterD + "/" + formatterM + "/" + year + "");
//                            showCollection.setText("Collection on " + setDate + ":- " + String.valueOf(collectionDatabase.getAllHistory(strtDate, strtDate).get(0).getCollection()));
                            if (collectionDatabase.getAllHistory(strtDate, strtDate).size() != 0) {

                                showCollection.setText("Collection on " + setDate + ":- " + String.valueOf(collectionDatabase.getAllHistory(strtDate, strtDate).get(0).getCollection()));
                            } else {
                                showCollection.setText("Collection on " + setDate + ":- 0");
                            }


                        }

                    }
                };
                final DatePickerDialog dialog = new DatePickerDialog(v.getContext(), datePickerListener, currYear, currMonth - 1, currDay);
                dialog.show();
            }
        });


//        btnChangeDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                custNum.setText(db.numCustomers(strtDate) + " customer came on given date");
//
////                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Make sure user insert date into edittext in this format.
////
////                Date strDateObject;
////                String strtDate = null;
////
////
////
////                try {
////                    String dob_var = (edtDate.getText().toString());
////                    strDateObject = formatter.parse(dob_var);
////                    strtDate = new SimpleDateFormat("yyyy-MM-dd").format(strDateObject);
////
////
////
////                    //TODO     here check database for number of customers visited on that date
////                    custNum.setText(db.numCustomers(strtDate) + " customer came on given date");
////                } catch (java.text.ParseException e) {
////                    Toast.makeText(getBaseContext(), "Please Enter Date In Correct Format", Toast.LENGTH_LONG).show();
////
////                }
//
//
//
//
//                //fire up a dialog here to change date
//                /////link here
//                //https://developer.android.com/guide/topics/ui/dialogs.html
//// do if free
//            }
//        });

        btnExit = (Button) findViewById(R.id.btnExit2);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent redirectToLogin = new Intent(AdminActivity.this, MainPage.class);
                startActivity(redirectToLogin);
                finish();
            }
        });


        btnAccounts = (Button) findViewById(R.id.btnAccnts);
        btnAccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent redirectToAccounts = new Intent(AdminActivity.this, AccountsActivity.class);
                startActivity(redirectToAccounts);
            }
        });

        toBalance = (Button) findViewById(R.id.EmployeeBalance);
        toBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToBalance = new Intent(AdminActivity.this, Balance.class);
                startActivity(redirectToBalance);
                finish();
            }
        });

        toETransactions = (Button) findViewById(R.id.pWiseTransactions);
        toETransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent redirectToET = new Intent(AdminActivity.this, TransactionActivity.class);
                startActivity(redirectToET);
                finish();
            }
        });
        btnMM = (Button) findViewById(R.id.btnManageMenu);
        btnMM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: I was here");
                Intent redirectTOMM = new Intent(AdminActivity.this, EditMenu.class);
                Log.d(TAG, "onClick: I was here");
                startActivity(redirectTOMM);

            }
        });


        checkBal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent redirectToCheckBal = new Intent(AdminActivity.this, CheckBalance.class);
                startActivity(redirectToCheckBal);


            }
        });

        Button syncUserData = (Button) findViewById(R.id.syncUser);
        syncUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showProgressDialog();
                checkNetUser();
                Toast.makeText(AdminActivity.this, "Syncing with Internet. Please Wait", Toast.LENGTH_LONG).show();
            }
        });

        Button syncMenu = (Button) findViewById(R.id.syncMenu);
        syncMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showProgressDialog();
                Toast.makeText(AdminActivity.this, "Syncing with Internet. Please Wait", Toast.LENGTH_LONG).show();
                checkNetMenu();
            }
        });


    }

    private void checkNetUser() {
        String tag_string_req = "req_register42";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_TEST_CONNECTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Log.d(TAG, "onResponse: Succesfully posted to net");
                        syncUser();
                    } else {
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
                        "No Internet Available", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void syncUser() {
        List<Employee> list = userdb.get(UserDatabase.Status.NEW);
        for (Employee entry : list) {
            addUpdateUser(entry.getEmployee_id(), entry.getEmployee_name(), entry.getBalance(), entry.getId(),entry.getUID());
        }

        resetUserList();
    }

    private void addUpdateUser(final String Employee_id, final String name, final Double Balance, final Integer ID,final String UID) {
        // Tag used to cancel the request
        String tag_string_req = "req_register2";

//        pDialog.setMessage("Registering ...");
//        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_UPDATE_USER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);

//                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {


                        Log.d(TAG, "onResponse: Succesfully posted to net");
                        userdb.updateStatus(ID);
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
                Log.e(TAG, "Registration Error: from add user" + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage() + " Possibly no internet", Toast.LENGTH_LONG).show();
//                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<>();
                params.put("Employee_code", Employee_id);
                params.put("Name", name);
                params.put("Balance", String.valueOf(Balance));
                params.put("UID",UID);
//        new_content.put("Name", employee_name);

//                Log.d(TAG, "insertUser: inseting to db");
                return params;
            }

        };

        // Adding request to request queue
//        strReq.setPriority(Request.Priority.LOW);
//        checkNet();
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void resetUserList() {
        String tag_string_req = "req_register42";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_USERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        JSONArray arr = jObj.getJSONArray("data");
                        Log.d(TAG, "onResponse: Succesfully posted to net");
                        if (userdb.isSynced()) {
                            Log.d(TAG, "onResponse: dropping table");
                            userdb.del();

                            Log.d(TAG, "onResponse: User db synced");
                            for (int i = 0; i < arr.length(); i++) {
                                JSONArray temp = arr.getJSONArray(i);
                                JSONArray arr2 = temp;

                                userdb.insertUserUpdated(arr2.getString(0), arr2.getString(1), Double.valueOf(arr2.getString(2)), arr2.getString(3));
                                Log.d(TAG, "onResponse: inserting user " + arr2.getString(0));
                            }


                            Toast.makeText(AdminActivity.this, "Sync Completed Succesfully", Toast.LENGTH_SHORT).show();
                        }
                    } else {
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
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void checkNetMenu() {
        String tag_string_req = "req_register42";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_TEST_CONNECTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Log.d(TAG, "onResponse: Succesfully posted to net");
                        syncItem();
                    } else {
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
                        "No Internet", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void syncItem() {
        List<menuItem> list = menudb.get(MenuDatabase.Status.NEW);
        for (menuItem entry : list) {
            addUpdateItem(entry.getName(), Double.valueOf(entry.getPrice()), entry.getId(), menudb.getItemNum(entry.getName()));
        }

        list = menudb.get(MenuDatabase.Status.DELETED);
        for (menuItem entry : list) {
            deleteItem(entry.getName(), entry.getId());
        }

        resetMenu();
    }

    private void resetMenu() {

        String tag_string_req = "req_register42";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_MENU, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        JSONArray arr = jObj.getJSONArray("data");
                        Log.d(TAG, "onResponse: Succesfully posted to net");
                        if (menudb.isSynced()) {
                            Log.d(TAG, "onResponse: dropping table");
                            menudb.del();

                            Log.d(TAG, "onResponse: Menu db synced");
                            for (int i = 0; i < arr.length(); i++) {
                                JSONArray temp = arr.getJSONArray(i);

                                menudb.insertMenuUpdated(temp.getString(0), Double.valueOf(temp.getString(1)), Integer.valueOf(temp.getString(2)));
                                Log.d(TAG, "onResponse: inserting menu " + temp.getString(0));
                            }

                            Toast.makeText(AdminActivity.this, "Sync Completed Succesfully", Toast.LENGTH_SHORT).show();
                        }
                    } else {
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
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void addUpdateItem(final String Item_name, final Double Cost, final Integer ID, final Integer num) {
        String tag_string_req = "req_register4";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_UPDATE_MENU, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Log.d(TAG, "onResponse: Succesfully posted to net");
                        menudb.updateStatus(ID);
                    } else {
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
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Item_name", Item_name);
                params.put("Cost", String.valueOf(Cost));
                params.put("num", String.valueOf(num));
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void deleteItem(final String Item_name, final Integer ID) {
        String tag_string_req = "req_register6";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DELETE_MENU, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        Log.d(TAG, "onResponse: Succesfully posted to net");
                        menudb.finalDelete(ID);
                    } else {
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
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Item_name", Item_name);
                return params;
            }
        };
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
