package com.example.digitalcanteen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
}
