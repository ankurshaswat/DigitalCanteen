package com.example.digitalcanteen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangeActivity extends AppCompatActivity {

    private EditText eName = null;
    private EditText ePrice = null;
    private Button submit;
    private Button exit;

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


        eName.setText(name);
        ePrice.setText("" + cost + "");

        View.OnClickListener okN = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                for (int i = 0; i < MainPage.items.size(); i += 1) {
                    if (i == eId) {
                        MainPage.items.get(i).setName(eName.getText().toString());
                        MainPage.items.get(i).setPrice(ePrice.getText().toString());
                        flag = 1;
                        MainPage.renderMenuAdapter.notifyDataSetChanged();
                        break;
                    }
                }
                if (flag == 0) {
//                    int b=MainPage.items.size();
                    MainPage.items.add(new menuItem(eName.getText().toString(), ePrice.getText().toString()));
                    MainPage.items.get(MainPage.items.size() - 1).setId(MainPage.items.size() - 1);
                    MainPage.renderMenuAdapter.notifyDataSetChanged();
                }
            }
        };
        submit.setOnClickListener(okN);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToLogin = new Intent(ChangeActivity.this, LoginActivity.class);
                startActivity(redirectToLogin);
            }
        });


    }
}
