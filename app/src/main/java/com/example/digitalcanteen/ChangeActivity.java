package com.example.digitalcanteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeActivity extends AppCompatActivity {
    private static final String TAG = "ChangeActivity";
    private EditText eName = null;
    private EditText ePrice = null;
    private Button submit;
    private Button exit;
    private MenuDatabase db;
    private int eId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        Intent intent = getIntent();
        String name;
        name = intent.getStringExtra("name");
        String cost;
        cost = intent.getStringExtra("price");
        eId = intent.getIntExtra("id", 0);
        eName = (EditText) findViewById(R.id.editName);
        ePrice = (EditText) findViewById(R.id.editPrice);
        submit = (Button) findViewById(R.id.MMSubmit);
        exit = (Button) findViewById(R.id.MMExit);
        db = new MenuDatabase(this);

        eName.setText(name);
        ePrice.setText("" + cost + "");
        Log.d(TAG, "onCreate: " + db.getAll().get(0).getName());
        Log.d(TAG, "onCreate: " + name);
        final int id = db.getItem(name);
        View.OnClickListener okN = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String item_name = eName.getText().toString();
                double cost = Double.parseDouble(ePrice.getText().toString());
                if (db.editItem(id, item_name, cost)) {
                    Toast.makeText(ChangeActivity.this, "Item updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangeActivity.this, "There was and error, try again", Toast.LENGTH_SHORT).show();
                }


//                int flag = 0;
//                for (int i = 0; i < MainPage.items.size(); i += 1) {
//                    if (i == eId) {
////                        MainPage.items.get(i).setName(eName.getText().toString());
////                        MainPage.items.get(i).setPrice(ePrice.getText().toString());
//                        flag = 1;
////                        MainPage.renderMenuAdapter.notifyDataSetChanged();
//                        break;
//                    }
//                }
//                if (flag == 0) {
////                    int b=MainPage.items.size();
////                    MainPage.items.add(new menuItem(eName.getText().toString(), ePrice.getText().toString()));
////                    MainPage.items.get(MainPage.items.size() - 1).setId(MainPage.items.size() - 1);
////                    MainPage.renderMenuAdapter.notifyDataSetChanged();
//                }
            }
        };
        submit.setOnClickListener(okN);


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToLogin = new Intent(ChangeActivity.this, LoginActivity.class);
                startActivity(redirectToLogin);
                finish();
            }
        });


    }
}
