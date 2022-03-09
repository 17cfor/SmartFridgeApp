package com.example.smartfridgeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button fridge;
    private Button groceryList;
    private Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fridge = (Button) findViewById(R.id.fridge);
        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFridgeActivity();
            }
        });

        groceryList = (Button) findViewById(R.id.groceryList);
        groceryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGroceryListActivity();
            }
        });


    }

    private void openGroceryListActivity() {
        Intent intent1 = new Intent(this, GroceryListActivity.class);
        startActivity(intent1);
    }

    public void openFridgeActivity() {
        Intent intent2 = new Intent(this, FridgeActivity.class);
        startActivity(intent2);
    }

}