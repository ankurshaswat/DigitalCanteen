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
import java.util.List;
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
                    checkNet();
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

    private void syncItem() {
        List<menuItem> list = db.get(MenuDatabase.Status.NEW);
        for (menuItem entry : list) {
            addUpdateItem(entry.getName(), Double.valueOf(entry.getPrice()), entry.getId());
        }

        list = db.get(MenuDatabase.Status.DELETED);
        for (menuItem entry : list) {
            deleteItem(entry.getName(), entry.getId());
        }
    }


    private void addUpdateItem(final String Item_name, final Double Cost, final Integer ID) {
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
                        db.updateStatus(ID);
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
                        db.finalDelete(ID);
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

    private void checkNet() {
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
//                Toast.makeText(getApplicationContext(),
//                        , Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return new HashMap<>();
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
