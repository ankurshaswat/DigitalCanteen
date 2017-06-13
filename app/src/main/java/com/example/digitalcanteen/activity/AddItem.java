package com.example.digitalcanteen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.digitalcanteen.R;
import com.example.digitalcanteen.app.AppConfig;
import com.example.digitalcanteen.app.AppController;
import com.example.digitalcanteen.dataObjects.menuItem;
import com.example.digitalcanteen.database.MenuDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddItem extends AppCompatActivity {

    private static final String TAG="AddItem";
    private EditText name=null;
    private EditText price=null;
    private Button submit=null;
    private Button exit=null;
    private MenuDatabase db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        name=(EditText) findViewById(R.id.addName);
        price=(EditText) findViewById(R.id.addPrice);
        submit=(Button) findViewById(R.id.addSubmit);
        exit=(Button) findViewById(R.id.addExit);
        db= new MenuDatabase(this);

        View.OnClickListener onSubmit = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nName=name.getText().toString();
                double nPrice = Double.parseDouble(price.getText().toString());
                int flag=0;
                for(menuItem m :db.getAll()){

                    if(m.getName()==nName){
                        Toast.makeText(AddItem.this, "Item already exists", Toast.LENGTH_SHORT).show();
                        flag=1;
                        break;

                    }
                }
                if (flag==0){
                    db.insertItem(nName,nPrice);
                    addItem(nName, nPrice);
                    Toast.makeText(AddItem.this, "Item Has Been Added", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    price.setText("");
                }
            }
        };
        submit.setOnClickListener(onSubmit);

        View.OnClickListener onExit = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exitAdd = new Intent(AddItem.this, EditMenu.class);
                startActivity(exitAdd);
                finish();
            }
        };
        exit.setOnClickListener(onExit);


    }


    private void addItem(final String Item_name, final Double Cost) {
        // Tag used to cancel the request
        String tag_string_req = "req_register3";

//        pDialog.setMessage("Registering ...");
//        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_MENU, new Response.Listener<String>() {

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
                params.put("Item_name", Item_name);
                params.put("Cost", String.valueOf(Cost));
//                params.put("Balance", String.valueOf(Balance));
//        new_content.put("Name", employee_name);

//                Log.d(TAG, "insertUser: inseting to db");
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
