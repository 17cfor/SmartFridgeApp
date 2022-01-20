package com.example.smartfridgeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class GroceryListActivity extends AppCompatActivity {
    private Button addItem;
    private Spinner dropDown;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_list);

        addItem = (Button) findViewById(R.id.add);
        listView = findViewById(R.id.listView);
        dropDown = findViewById(R.id.spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(GroceryListActivity.this,android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.groceries));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDown.setAdapter(myAdapter);

        FirebaseDatabase.getInstance().getReference().child("Grocery List").child("Item").setValue("a");
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_name = dropDown.getSelectedItem().toString();
                if(txt_name.isEmpty()){
                    Toast.makeText(GroceryListActivity.this,"No Item Entered", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Grocery List").push().setValue(txt_name);
                }
            }
        });
    }
}