package com.example.digitalcanteen;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    UserDatabase db;
    private EditText enter_name_field;
    private EditText enter_employee_id_field;
    private String employee_name;
    private String employee_id;
    private ProgressDialog progressDialog;
    private Button register_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        enter_name_field = (EditText) findViewById(R.id.registerName);
        enter_employee_id_field = (EditText) findViewById(R.id.registerCode);
        register_button = (Button) findViewById(R.id.registerFormSubmit);
        progressDialog = new ProgressDialog(this);
        db = new UserDatabase(this);
        Date date_x = new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String date = sdf.format(date_x);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                employee_id = enter_employee_id_field.getText().toString();
                employee_name = enter_name_field.getText().toString();

                if (employee_name.trim().isEmpty() || employee_id.trim().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Enter valid credentials", Toast.LENGTH_SHORT).show();
                } else {
                    showProgressDialog();
                    Log.d(TAG, "onClick: going to check employee_id");
                    Cursor results = db.checkEmployeeId(employee_id);
                    Log.d(TAG, "onClick: employee_id checked");
                    if (!results.moveToFirst()) {
                        Log.d(TAG, "onClick: inserting user");
                        boolean check = db.insertUser(employee_id, employee_name, 0, date);
                        if (check) {
                            Toast.makeText(getApplicationContext(), "Registration Succesful", Toast.LENGTH_SHORT).show();

                            Intent redirectToMain = new Intent(RegisterActivity.this, MainPage.class);
                            redirectToMain.putExtra("Employee_id", employee_id);
                            redirectToMain.putExtra("Name", employee_name);
                            redirectToMain.putExtra("Balance", 0);
                            redirectToMain.putExtra("Date", date);

                            startActivity(redirectToMain);
                            cancelProgressDialog();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Connection Error!! Please Try Again", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        employee_name = results.getString(2);

                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegisterActivity.this);
                        alertDialog.setTitle("User already registered.");

                        alertDialog.setMessage("Name :" + employee_name + "\n");

                        alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                cancelProgressDialog();
                            }
                        });
                        alertDialog.show();

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
