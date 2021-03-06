package com.example.digitalcanteen.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.digitalcanteen.R;
import com.example.digitalcanteen.dataObjects.menuItem;
import com.example.digitalcanteen.database.UserDatabase;

public class
LoginActivity extends AppCompatActivity {


    private Button login_button = null;
    private Button register_button = null;
    private Button admin_button = null;
    private Button order_button = null;
    private Button btnNoLogin = null;
    private EditText employee_id_edit = null;
    private String employee_id;
    private UserDatabase db;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        login_button = (Button) findViewById(R.id.login_button);
        admin_button = (Button) findViewById(R.id.admin_button);
        order_button = (Button) findViewById(R.id.btnOrder);
//        register_button = (Button) findViewById(R.id.register_button);
//        employee_id_edit = (EditText) findViewById(R.id.employee_id_edit);
//        btnNoLogin = (Button) findViewById(R.id.btnNoLogin);

        db = new UserDatabase(this);
        progressDialog = new ProgressDialog(this);
        MainPage.items.add(new menuItem("pizza", "300"));
        MainPage.items.add(new menuItem("burger", "100"));

//        register_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent redirectToRegister = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(redirectToRegister);
//            }
//        });

        admin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent redirectToAdmin = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(redirectToAdmin);
            }
        });

//        btnNoLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent redirectToNoLogin = new Intent(LoginActivity.this, WithoutLogin.class);
//                startActivity(redirectToNoLogin);
//            }
//        });
//
//        login_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                if (employee_id_edit.getText().toString().trim().isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "Employee code cannot be empty", Toast.LENGTH_SHORT)
//                            .show();
//                } else {
//                    showProgressDialog();
//                    employee_id = employee_id_edit.getText().toString();
//                    Cursor results = db.checkEmployeeId(employee_id);
//
//                    if (!results.moveToFirst()) {
//                        cancelProgressDialog();
//
//                        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
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
//                    } else {
//                        String employee_id = results.getString(1);//pass index after making db
//                        String name = results.getString(2);//pass index of name
//                        Double balance = results.getDouble(3);
//
//                        Intent refirectToMain = new Intent(LoginActivity.this, MainPage.class);
//                        refirectToMain.putExtra("employee_id", employee_id);
//                        refirectToMain.putExtra("name", name);
//                        refirectToMain.putExtra("balance", balance);
//
//                        cancelProgressDialog();
//                        startActivity(refirectToMain);
//                        finish();
//
//                    }
//                }
//            }
//        });

        order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent redirectToMain = new Intent(LoginActivity.this, MainPage.class);
                startActivity(redirectToMain);
                finish();

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
