package com.example.digitalcanteen.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.example.digitalcanteen.dataObjects.Collection;
import com.example.digitalcanteen.dataObjects.EHistory;
import com.example.digitalcanteen.dataObjects.Employee;
import com.example.digitalcanteen.dataObjects.menuItem;
import com.example.digitalcanteen.dataObjects.selectedItems;
import com.example.digitalcanteen.database.CollectionDatabase;
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
    public static TextView tipAmountView = null;
    private static int flagg = 0;
    //    private EditText amt2add = null;
    private ListView selectedThings;
    private ListView listItems;
    private ProgressDialog progressDialog;
    private Button btnExit = null;
    private Button btnSubmit = null;
    private Button btnAddUser = null;
    private Button btn2Admin = null;
    private EditText employee_id_edit = null;
    private UserDatabase db;
    private CollectionDatabase collDb;
    private TransactionDatabase tranDB;
    private MenuDatabase menuDB;
    private String employee_id;
    private Button logout = null;
    private Button IdEntered = null;
    private Button addMoney = null;
    private Button tip = null;
    private Button addRFID = null;
    private String tempId;

    private void logOut() {
        items.clear();
        order.clear();
        flagg = 0;
        employee_id_edit.setText("");
        IdEntered.setVisibility(View.VISIBLE);
        logout.setVisibility(View.GONE);
        addMoney.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
        btnAddUser.setVisibility(View.VISIBLE);
        btn2Admin.setVisibility(View.VISIBLE);
        tip.setVisibility(View.GONE);
        addRFID.setVisibility(View.GONE);

        recreate();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main_page);
//        AppController.getInstance().getRequestQueue().stop();
//        Log.d(TAG, "addUser: Network availability is "+AppController.getInstance().isInternetAvailable());
        collDb = new CollectionDatabase(this);

        addMoney = (Button) findViewById(R.id.mPAddMoney);
        IdEntered = (Button) findViewById(R.id.mPIdE);
        btn2Admin = (Button) findViewById(R.id.mainPAgeAdmin);
        employee_id_edit = (EditText) findViewById(R.id.employeeCodeInput);
//        amt2add = (EditText) findViewById(R.id.moneyAmt);
        tip = (Button) findViewById(R.id.tip);
        total = (TextView) findViewById(R.id.txtVuTotal);
        btnAddUser = (Button) findViewById(R.id.add_user);
        btnExit = (Button) findViewById(R.id.btnExit);
        listItems = (ListView) findViewById(R.id.lstMenu);
        selectedThings = (ListView) findViewById(R.id.lstCart);
        final TextView nameText = (TextView) findViewById(R.id.txtName);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        addRFID = (Button) findViewById(R.id.mPAR);
//        final TextView bal = (TextView) findViewById(R.id.balance);
        final TextView currBalance = (TextView) findViewById(R.id.balance);
        logout = (Button) findViewById(R.id.mPLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
        tipAmountView = (TextView) findViewById(R.id.tipAmountView);


        employee_id_edit.setText("");
        IdEntered.setVisibility(View.VISIBLE);
        logout.setVisibility(View.GONE);
        addMoney.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
        btnAddUser.setVisibility(View.VISIBLE);
        btn2Admin.setVisibility(View.VISIBLE);
        tip.setVisibility(View.GONE);
        addRFID.setVisibility(View.GONE);

        addRFID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder qBuilder = new AlertDialog.Builder(MainPage.this);
                Log.d(TAG, "onClick: I am here");
                View qView = getLayoutInflater().inflate(R.layout.addrfid, null);
//                final EditText newName = (EditText) qView.findViewById(R.id.addName);
//                final EditText newCode = (EditText) qView.findViewById(R.id.addEC);
//                final EditText newRFID = (EditText) qView.findViewById(R.id.newRFID);
//                Button addSubmit = (Button) qView.findViewById(R.id.addSubmit);
//                Button addCancel = (Button) qView.findViewById(R.id.addCancel);
                final TextView showEmpId = (TextView) qView.findViewById(R.id.showEmployeeId);
                final EditText addingRFID = (EditText) qView.findViewById(R.id.addingRFID);
                Button addingSubmit = (Button) qView.findViewById(R.id.addingRFIDSubmit);
                Button addingCancel = (Button) qView.findViewById(R.id.addRFIDCancel);
                showEmpId.setText("Employee Id:- " + tempId);
//                employee_id = employee_id_edit.getText().toString();
                //NOTE here employee id can also store UID
//                Cursor results = db.checkEmployeeId(employee_id);
                qBuilder.setView(qView);
                final AlertDialog dialog = qBuilder.create();
                dialog.show();
                addingSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tempRFID = addingRFID.getText().toString();
                        if (!tempRFID.isEmpty()) {
                            Cursor results = db.checkEmployeeId(tempRFID);
                            if (!results.moveToFirst()) {
                                if (tempRFID.length() == 10) {
                                    db.updateUID(tempId, tempRFID);
                                    Toast.makeText(getApplicationContext(), "RFID added", Toast.LENGTH_SHORT).show();
                                    checkNet();
//                            dialog.cancel();
                                    dialog.dismiss();

                                } else {
                                    Toast.makeText(getApplicationContext(), "RFID incorrect .Please try again", Toast.LENGTH_SHORT)
                                            .show();
                                    addingRFID.setText("");
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "This RFID is already associated with a user.", Toast.LENGTH_SHORT)
                                        .show();
                                addingRFID.setText("");
                            }
//                        else{
//                            Toast.makeText(getApplicationContext(), "Please try again", Toast.LENGTH_SHORT)
//                                    .show();
//                            addingRFID.setText("");
//                        }
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Scan RFID card .", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
                addingCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });


            }
        });

//        final Window window = getWindow();

//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        btn2Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToAdmin = new Intent(MainPage.this, AdminActivity.class);
                startActivity(redirectToAdmin);
                finish();
            }
        });

//


        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder qBuilder = new AlertDialog.Builder(MainPage.this);
                Log.d(TAG, "onClick: I am here");
                View qView = getLayoutInflater().inflate(R.layout.adduser, null);
                final EditText newName = (EditText) qView.findViewById(R.id.addName);
                final EditText newCode = (EditText) qView.findViewById(R.id.addEC);
                final EditText newRFID = (EditText) qView.findViewById(R.id.newRFID);
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
                        String addRFID = newRFID.getText().toString();

                        if (addId.length() == 0 || addName.length() == 0) {
                            Toast.makeText(getApplicationContext(), "Name and ID cannot be empty", Toast.LENGTH_SHORT)
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
                                if (addRFID.length() == 10) {
                                    Cursor result = db.checkEmployeeId(addRFID);
                                    if (!result.moveToFirst()) {
                                        Boolean check = db.insertUser(addId, addName, 0.0, addRFID);
                                        if (check) {
                                            Toast.makeText(getApplicationContext(), "Registration Succesful", Toast.LENGTH_SHORT).show();
                                            checkNet();
                                            dialog.cancel();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Error occurred in Registration .Please try again", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "User Already Exists", Toast.LENGTH_SHORT).show();
                                    }

                                } else if (addRFID.length() == 0) {
                                    boolean check = db.insertUser(addId, addName, 0.0, "-1");
                                    if (check) {
                                        Toast.makeText(getApplicationContext(), "Registration Succesful", Toast.LENGTH_SHORT).show();
                                        checkNet();
                                        dialog.cancel();
                                    }
//                                boolean check = db.insertUser(addId, addName, 0.0,addRFID);


                                    String UID = "-1";
                                    //TODO here if uid set then put UID

//                                boolean check = db.insertUser(addId, addName, 0.0, UID);
//                                if (check) {
//                                    Toast.makeText(getApplicationContext(), "Registration Succesful", Toast.LENGTH_SHORT).show();
//                                    checkNet();
//                                    dialog.cancel();
//                                }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Incorrect RFID entered .Please try again", Toast.LENGTH_SHORT).show();
                                    newRFID.setText("");
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

//        total.setText("howdy");
//        setContentView(R.layout.activity_main_page);
        progressDialog = new ProgressDialog(this);

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


//       order.add(new selectedItems("pizza", "2", "600"));

        items = menuDB.getAll();
//        for (int i = 0; i < items.size(); i += 1) {
//            items.get(i).setId(i);
//        }

        selectedItemsAdapter = new SelectAdapter(MainPage.this, R.layout.activity_selected, order);
        selectedThings.setAdapter(selectedItemsAdapter);

        //Log.d(TAG, "onCreate: i am batman");

        renderMenuAdapter = new MenuAdapter(MainPage.this, R.layout.activity_layout_menu, items);
        listItems.setAdapter(renderMenuAdapter);

        final View.OnClickListener oKpressed = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (employee_id_edit.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Employee code cannot be empty", Toast.LENGTH_SHORT)
                            .show();
                }
//                TODO checking if id is entered correctly
                else {

                    employee_id = employee_id_edit.getText().toString();
                    //NOTE here employee id can also store UID
                    Cursor results = db.checkEmployeeId(employee_id);
                    if (results.moveToFirst()) {

                        tempId = results.getString(1);

                        nameText.setText("Welcome " + db.getName(tempId));
                        Double roundOff = Math.round(db.getBal(tempId) * 100.0) / 100.0;
                        currBalance.setText("Your Balance is " + String.valueOf(roundOff));
                        flagg = 1;
                        Toast.makeText(MainPage.this, "Login Succesfull", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onClick: " + db.getUID(tempId));
                        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                        if (!results.getString(5).equals("-1")) {
                            addRFID.setVisibility(View.GONE);
                        } else {
                            addRFID.setVisibility(View.VISIBLE);
                        }

                        IdEntered.setVisibility(View.GONE);
                        logout.setVisibility(View.VISIBLE);
                        addMoney.setVisibility(View.VISIBLE);
                        btnSubmit.setVisibility(View.VISIBLE);
                        btnAddUser.setVisibility(View.GONE);
                        btn2Admin.setVisibility(View.GONE);
                        tip.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter the Employee Code correctly", Toast.LENGTH_SHORT)
                                .show();
                    }
                }


            }
        };
        IdEntered.setOnClickListener(oKpressed);
        employee_id_edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    if (employee_id_edit.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Employee code cannot be empty", Toast.LENGTH_SHORT)
                                .show();
                    }
//                TODO checking if id is entered correctly
                    else {

                        employee_id = employee_id_edit.getText().toString();
                        //NOTE here employee id can also store UID
                        Cursor results = db.checkEmployeeId(employee_id);
                        if (results.moveToFirst()) {

                            String tempId = results.getString(1);

                            nameText.setText("Welcome " + db.getName(tempId));
                            Double roundOff = Math.round(db.getBal(tempId) * 100.0) / 100.0;
                            currBalance.setText("Your Balance is " + String.valueOf(roundOff));
                            flagg = 1;
                            Toast.makeText(MainPage.this, "Login Succesfull", Toast.LENGTH_SHORT).show();

                            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow((null == getCurrentFocus()) ? null : getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


                            IdEntered.setVisibility(View.GONE);
                            logout.setVisibility(View.VISIBLE);
                            addMoney.setVisibility(View.VISIBLE);
                            btnSubmit.setVisibility(View.VISIBLE);
                            btnAddUser.setVisibility(View.GONE);
                            btn2Admin.setVisibility(View.GONE);
                            tip.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getApplicationContext(), "Please enter the Employee Code correctly", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }

                return false;
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagg != 1) {
                    Toast.makeText(MainPage.this, "Please Log In First to place order", Toast.LENGTH_SHORT).show();
                } else {
                    Date date_x = new Date();
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    final String date = sdf.format(date_x);
                    int num = tranDB.getNextNum();
                    for (int i = 0; i < order.size(); i++) {
                        //here add each item to transactions table
                        Log.d(TAG, "onClick: inserting " + order.get(i).getName());
                        tranDB.insertTransaction(employee_id, order.get(i).getName(), Integer.parseInt(order.get(i).getQuantity()), Double.parseDouble(order.get(i).getPrice()) / Integer.parseInt(order.get(i).getQuantity()), date, num);


                        if (!order.get(i).getName().equals("tip")) {
                            menuDB.editItemNum(menuDB.getItem(order.get(i).getName()), order.get(i).getName(), Integer.valueOf(order.get(i).getQuantity()));
//     addTransaction(employee_id, order.get(i).getName(), Integer.parseInt(order.get(i).getQuantity()), Double.parseDouble(order.get(i).getPrice()) / Integer.parseInt(order.get(i).getQuantity()), date);
                        }
                    }
                    checkNet();
                    db.updateinfo(employee_id, -1 * totalamt);
                    Toast.makeText(MainPage.this, "Order Succesful. You are being logged out", Toast.LENGTH_LONG).show();


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

        tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainPage.this);
                View mView = getLayoutInflater().inflate(R.layout.tip, null);
                final EditText amount = (EditText) mView.findViewById(R.id.addMoneyAmount);
                Button tipOK = (Button) mView.findViewById(R.id.addMoneyOK);
                Button tipCancel = (Button) mView.findViewById(R.id.addMoneyCancel);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();


                tipOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Double tempTotal = Double.parseDouble(total.getText().toString());
//                        Double tempTip;
                        int check = 1;
//                        Double tipAmount = Double.parseDouble(amount.getText().toString());
                        for (int i = 0; i < order.size(); i += 1) {
                            if (order.get(i).getName() == "tip") {
                                check = 0;
                                break;
                            }
                        }
                        if (check == 1) {
                            selectedItems tipObject = new selectedItems("tip", "1", amount.getText().toString());
                            order.add(tipObject);
                            tipAmountView.setText("Included Tip is:- " + amount.getText());
                            MainPage.selectedItemsAdapter.notifyDataSetChanged();
                        } else {
                            for (int i = 0; i < order.size(); i += 1) {
                                if (order.get(i).getName() == "tip") {
                                    order.get(i).setPrice(amount.getText().toString());
                                    tipAmountView.setText("Included Tip is:- " + amount.getText());
                                    MainPage.selectedItemsAdapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                        }

                        dialog.dismiss();
//                        View view = getcontex.getCurrentFocus();
//                        if (view != null) {
//                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                        }


                    }
                });
                tipCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        dialog.dismiss();
//                        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    }
                });

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
                            Date date_x = new Date();
                            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            final String date = sdf.format(date_x);
                            db.updateinfo(employee_id_edit.getText().toString(), money);
                            if (collDb.checkCollection(date)) {
                                collDb.addCollection(date, money);
                            } else {
                                collDb.insertItem(date, money);
                            }
                            checkNet();

                            balNow = db.getBal(employee_id_edit.getText().toString());
                            Log.d(TAG, "onClick: After Adding " + balNow);


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

    private void syncUser() {
        List<Employee> list = db.get(UserDatabase.Status.NEW);
        for (Employee entry : list) {
            addUpdateUser(entry.getEmployee_id(), entry.getEmployee_name(), entry.getBalance(), entry.getId(), entry.getUID());
        }

    }

    private void syncCollection() {
        List<Collection> list = collDb.get(CollectionDatabase.Status.NEW);

        for (Collection entry : list) {
            addCollection(entry.getDate(), entry.getId(), entry.getCollection());
        }
    }

    private void syncTransaction() {
        List<EHistory> list = tranDB.get(TransactionDatabase.Status.NEW);

        for (EHistory entry : list) {
            addTransaction(entry.getEmployeeCode(), entry.getName(), entry.getQuantity(), entry.getCpi(), entry.getDate(), entry.getId(), entry.getNum());
        }
    }

    private void addTransaction(final String Employee_id, final String Order_name, final Integer Quantity, final Double cpi, final String date, final Integer ID, final Integer num) {
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


                        Log.d(TAG, "onResponse: Succesfully posted to net " + Employee_id + " " + Order_name);
                        tranDB.updateStatus(ID);
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
                Log.e(TAG, "Registration Error: from add transaction " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage() + " Possibly no internet", Toast.LENGTH_LONG).show();
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
                params.put("Cost_perItem", String.valueOf(cpi));
                params.put("Date", date);
                params.put("Total", String.valueOf(Quantity * cpi));
                params.put("num", String.valueOf(num));
//                Log.d(TAG, "insertUser: inseting to db");
                return params;
            }

        };

        // Adding request to request queue
//        strReq.setPriority(Request.Priority.LOW);
//        checkNet();
//        AppController.getInstance().getRequestQueue().stop();
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void addUpdateUser(final String Employee_id, final String name, final Double Balance, final Integer ID, final String UID) {
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
                        db.updateStatus(ID);
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("Employee_code", Employee_id);
                params.put("Name", name);
                params.put("Balance", String.valueOf(Balance));
                params.put("UID", UID);
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


    private void addCollection(final String Date, final Integer id, final Double Collection) {
        // Tag used to cancel the request
        String tag_string_req = "req_register2";

//        pDialog.setMessage("Registering ...");
//        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_UPDATE_COLLECTION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);

//                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {


                        Log.d(TAG, "onResponse: Succesfully posted to net");
                        collDb.updateStatus(id);
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
                params.put("Date", Date);
                params.put("Collection", String.valueOf(Collection));
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


    private void checkNet() {
        // Tag used to cancel the request
        String tag_string_req = "req_register20";

//        pDialog.setMessage("Registering ...");
//        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_TEST_CONNECTION, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);

//                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {


                        Log.d(TAG, "connected");
                        syncUser();
                        syncTransaction();
                        syncCollection();
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
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage() + " Possibly no internet", Toast.LENGTH_LONG).show();
//                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url


//                Log.d(TAG, "insertUser: inseting to db");
                return new HashMap<>();
            }

        };

        // Adding request to request queue
//        strReq.setPriority(Request.Priority.LOW);
//        checkNet();
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
